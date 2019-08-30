package io.miowlimiowli.manager;

import android.content.Context;
import android.util.Pair;

import androidx.room.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.miowlimiowli.exceptions.UsernameEmpty;
import io.reactivex.functions.Function;

import io.miowlimiowli.exceptions.UsernameAlreadExistError;
import io.miowlimiowli.exceptions.UsernameorPasswordError;
import io.miowlimiowli.manager.data.RawNews;
import io.miowlimiowli.manager.sql.AppDatabase;
import io.miowlimiowli.manager.sql.SqlUserandNews;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.internal.operators.flowable.FlowableWindow;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;
/**
 * 使用 getManager 获取Manager单例
 * 前端只可调用函数，不得直接访问成员
 */
public class Manager {

    private static Manager manager = new Manager();
    private static List<String>  cat_list;
    private Manager() {
        cat_list = new ArrayList<>();
        cat_list.add("科技");
        cat_list.add("社会");
        cat_list.add("体育");
        cat_list.add("娱乐");
        cat_list.add("汽车");
        cat_list.add("教育");
        cat_list.add("文化");
        cat_list.add("健康");
        cat_list.add("军事");
        cat_list.add("财经");
    }

    public static Manager getInstance() {
        return manager;
    }

    public Map<String, User> users = new HashMap<>();
    public Map<String, RawNews> newses = new HashMap<>();

    public User getUser() {
        return user;
    }

    public User user = null;
    /**
     * @param username 用户名
     * @param password 密码
     * @throws UsernameorPasswordError 登陆失败抛出异常
     */
    public void login(final String username, final String password) throws UsernameorPasswordError {
        if (!users.containsKey(username))
            throw new UsernameorPasswordError();
        User user = users.get(username);
        if (user.getPassword() != password)
            throw new UsernameorPasswordError();
        this.user = user;
    }

    /**
     * 注册用户，注册完不会自动登录！
     *
     * @param username 用户名
     * @param password 密码
     * @throws UsernameAlreadExistError 注册失败（用户重名）抛出异常
     */
    public void register(final String username, final String password) throws UsernameAlreadExistError, UsernameEmpty {
        if (users.containsKey(username))
            throw new UsernameAlreadExistError();
        if(username.isEmpty())
            throw new UsernameEmpty();
        User user = new User(username, password);
        user.cat_list = new ArrayList<>(cat_list);
        users.put(username, user);
    }

    /**
     * 退出登录
     */
    public void logout(){
        user = null;
    }
    /**
     * @param size 一次获取的新闻数量，推荐100个，平均每个新闻3kb
     * @param page 获取第几页的新闻。
     * @param category 新闻的类别，共10个
     * @param keyword 搜索关键词，留空则不指定关键词
     * @return 返回的新闻是从第((pageNo - 1) * pageSize + 1)个的pageSize个最新新闻。若不到pageSize个，说明没有更多可用新闻
     */
    public Single<List<DisplayableNews>> FetchDisplayableNewsbyCategoryandKeyword(final int size, final int page,  final String category, final String keyword) {
        return Flowable.fromCallable(()->{
            try {
                return RawNews.fetch_news_from_server(size, page, null, null, keyword, category);
            }catch (Exception e){
                return new ArrayList<RawNews>();
            }
        }).flatMapIterable(item -> item)
                .map(item ->{
                    newses.put(item.id, item);
                    return item;
                })
                .map(DisplayableNews::new)
                .map(new WrapDisplayableNews())
                .toList().subscribeOn(Schedulers.io()).observeOn((AndroidSchedulers.mainThread()));
    }

    /**
     * @return 获取当前用户所有喜欢的新闻的列表
     */
    public Single<List<DisplayableNews>> fetch_like_list(){
        return Flowable.fromCallable(()->{
            return db.SqlUserandNewsDao().getLikeListByUsername(user.username);
        })
                .flatMapIterable(item->item)
                .map(item-> new DisplayableNews(newses.get(item.news_id)))
                .map(new WrapDisplayableNews())
                .toList().subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * @return 获取当前用户所有阅读过的新闻的列表
     */
    public Single<List<DisplayableNews>> fetch_read_list(){
        return Flowable.fromCallable(()-> db.SqlUserandNewsDao().getReadListByUsername(user.username))
                .flatMapIterable(item->item)
                .map(item-> new DisplayableNews(newses.get(item.news_id)))
                .map(new WrapDisplayableNews())
                .toList().subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * @param news_id 新闻的ID
     * @return 此新闻所有的评论
     */
    public Single<List<DisplayableComment>> fetch_comment_by_news_id(String news_id){
        return Flowable.fromCallable(()->db.SqlCommentDao().getCommentByNewsid(news_id))
                .flatMapIterable(item->item)
                .map(DisplayableComment::new)
                .map(new WrapDisplayableComment())
                .toList()
                .subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());
    }


    private class WrapDisplayableComment implements Function<DisplayableComment, DisplayableComment>{
        @Override
        public DisplayableComment apply(DisplayableComment displayableComment) {
            displayableComment.user  = users.get(displayableComment.username);
            WrapDisplayableNews f = new WrapDisplayableNews();
            displayableComment.news = f.apply(new DisplayableNews(newses.get(displayableComment.news_id)));
            return displayableComment;
        }
    }


    private class WrapDisplayableNews implements Function<DisplayableNews, DisplayableNews> {
        @Override
        public DisplayableNews apply(DisplayableNews news) {
            SqlUserandNews sql = new SqlUserandNews();
            sql.username = user.username;
            sql.news_id = news.id;
            sql.isread = false;
            sql.islike = false;
            db.SqlUserandNewsDao().insert(sql);
            sql = db.SqlUserandNewsDao().query(user.username, news.id).get(0);
            news.islike = sql.islike;
            news.isread = sql.isread;
            news.likecount = db.SqlUserandNewsDao().countLike(news.id);
            news.readcount = db.SqlUserandNewsDao().countRead(news.id);
            news.publisher.subscribe(displayableNews ->  {
                SqlUserandNews t = db.SqlUserandNewsDao().query(user.username, displayableNews.id).get(0);
                if(displayableNews.islike && !t.islike)
                    displayableNews.likecount += 1;
                if(!displayableNews.islike && t.islike)
                    displayableNews.likecount -= 1;
                if(displayableNews.isread && !t.isread)
                    displayableNews.readcount += 1;
                if(!displayableNews.isread && t.isread)
                    displayableNews.readcount -= 1;
                t.islike = displayableNews.islike;
                t.isread = displayableNews.isread;
                db.SqlUserandNewsDao().update(t);
            });
            return news;
        }
    }
    /**
     * 此函数只允许在启动时调用一次
     * @param context Application Context
     */
    public void setContext(Context context) {
        this.context = context;
        //TODO:inmemory_for_test
        db  = Room.inMemoryDatabaseBuilder(context,
                AppDatabase.class).build();
    }

    /**
     * @return 获取当前用户的类别列表
     */
    public List<String> getCat_list(){
        return user.cat_list;
    }


    private Context context;

    AppDatabase db ;
}
