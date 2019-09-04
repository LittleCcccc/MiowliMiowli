package io.miowlimiowli.manager;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import androidx.room.*;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.miowlimiowli.R;
import io.miowlimiowli.exceptions.UsernameEmptyError;
import io.miowlimiowli.manager.sql.SqlComment;
import io.reactivex.functions.Function;

import io.miowlimiowli.exceptions.UsernameAlreadExistError;
import io.miowlimiowli.exceptions.UsernameorPasswordError;
import io.miowlimiowli.manager.data.RawNews;
import io.miowlimiowli.manager.sql.AppDatabase;
import io.miowlimiowli.manager.sql.SqlUserandNews;
import io.reactivex.Flowable;
import io.reactivex.Single;
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
        for(int i=0;i<cat.length;i++)
            cat_list.add(cat[i]);
    }

    public String[] cat = {"科技","社会","体育","娱乐","汽车","教育","文化","健康","军事","财经"};
    public String getCatAtPosition(int position)
    {
        return cat[position-1];
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
    public void register(final String username, final String email, final String password) throws UsernameAlreadExistError, UsernameEmptyError {
        if (users.containsKey(username))
            throw new UsernameAlreadExistError();
        if(username.isEmpty())
            throw new UsernameEmptyError();
        User user = new User(username, email, password);
        user.cat_list = new ArrayList<>(cat_list);
        user.avator = context.getResources().getDrawable(R.drawable.logo, null);
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
     * @return 获取当前用户所有收藏的新闻的列表
     */
    public Single<List<DisplayableNews>> fetch_favorite_list(){
        return Flowable.fromCallable(()-> db.SqlUserandNewsDao().getFavoriteListByUsername(user.username))
                .flatMapIterable(item->item)
                .map(item-> new DisplayableNews(newses.get(item.news_id)))
                .map(new WrapDisplayableNews())
                .toList().subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * @return 获取当前用户所有评论的新闻的列表
     */
    public Single<List<DisplayableNews>> fetch_comment_list(){
        return Flowable.fromCallable(()->db.SqlCommentDao().getCommentByUsername(user.username))
                .flatMapIterable(item->item)
                .map(item-> new DisplayableNews(newses.get(item.news_id)))
                .map(new WrapDisplayableNews())
                .toList().subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 通过新闻ID获取新闻
     */

    public Single<DisplayableNews> fetch_news_by_news_id(String news_id)
    {
        return Single.fromCallable(()-> db.SqlUserandNewsDao().query(user.username, news_id))
                .map((item) -> newses.get(item.news_id))
                .map((item)->new DisplayableNews(item))
                .subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());
    }




    /**
     * 以新闻ID获取此新闻所有评论
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

    /**
     * 当前用户发布一条评论
     * @param news_id 新闻ID
     * @param content 评论内容
     * @param date 评论时间
     * @return 返回刚添加的新闻的DisplayableComment
     */
    public DisplayableComment add_comment(String news_id, String content, Date date){
        SqlComment cmt = new SqlComment();
        cmt.content = content;
        cmt.news_id = news_id;
        cmt.publish_date = date;
        cmt.username = user.username;
        db.SqlCommentDao().insert(cmt);
        WrapDisplayableComment f = new WrapDisplayableComment();
        return f.apply(new DisplayableComment(cmt));
    }

    /**
     * @param cmt 被删除的评论，可以不是由当前用户发布
     */
    public void delete_comment(DisplayableComment cmt){
        SqlComment sqlcmt = db.SqlCommentDao().query(cmt.cmt_id);
        db.SqlCommentDao().delete(sqlcmt);
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
            sql.isfavorite = false;
            db.SqlUserandNewsDao().insert(sql);
            sql = db.SqlUserandNewsDao().query(user.username, news.id);
            news.islike = sql.islike;
            news.isread = sql.isread;
            news.isfavorite = sql.isfavorite;
            news.likecount = db.SqlUserandNewsDao().countLike(news.id);
            news.readcount = db.SqlUserandNewsDao().countRead(news.id);
            news.favoritecount = db.SqlUserandNewsDao().countFavorate(news.id);
            news.publisher.subscribe(displayableNews ->  {
                SqlUserandNews t = db.SqlUserandNewsDao().query(user.username, displayableNews.id);
                if(displayableNews.islike && !t.islike)
                    displayableNews.likecount += 1;
                if(!displayableNews.islike && t.islike)
                    displayableNews.likecount -= 1;
                if(displayableNews.isread && !t.isread)
                    displayableNews.readcount += 1;
                if(!displayableNews.isread && t.isread)
                    displayableNews.readcount -= 1;
                if(displayableNews.isfavorite && !t.isfavorite)
                    displayableNews.favoritecount += 1;
                if(!displayableNews.isfavorite && t.isfavorite)
                    displayableNews.favoritecount -= 1;
                t.islike = displayableNews.islike;
                t.isread = displayableNews.isread;
                t.isfavorite = displayableNews.isfavorite;
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

    public void setCat_list(List<String> list){user.setCat_list(list);}

    private Context context;

    private AppDatabase db ;
}
