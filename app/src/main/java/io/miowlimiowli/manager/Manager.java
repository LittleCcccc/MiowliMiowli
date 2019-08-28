package io.miowlimiowli.manager;

import android.content.Context;

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
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;
/**
 * 使用 getManager 获取Manager单例
 * 前端只可调用函数，不得直接访问成员
 */
public class Manager {

    private static Manager manager = new Manager();

    private Manager() {

    }

    public static Manager getInstance() {
        return manager;
    }

    public Map<String, User> users = new HashMap<>();
    public Map<String, RawNews> newses = new HashMap<>();
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
        users.put(username, new User(username, password));
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
     * @return 返回的新闻是从第((pageNo - 1) * pageSize + 1)个的pageSize个最新新闻。若不到pageSize个，说明没有更多可用新闻
     */
    public Single<List<DisplayableNews>> FetchDisplayableNewsbyCategory(final int size, final int page,  final String category) {
        return Flowable.fromCallable(()->{
            try {
                return RawNews.fetch_news_from_server(size, page, null, null, "", category);
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
     * @return 获取所有喜欢的新闻的列表
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
     * @return 获取所有阅读过的新闻的列表
     */
    public Single<List<DisplayableNews>> fetch_read_list(){
        return Flowable.fromCallable(()->{
            return db.SqlUserandNewsDao().getReadListByUsername(user.username);
        })
                .flatMapIterable(item->item)
                .map(item-> new DisplayableNews(newses.get(item.news_id)))
                .map(new WrapDisplayableNews())
                .toList().subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());
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
            news.publisher.subscribe(displayableNews ->  {
                SqlUserandNews t = db.SqlUserandNewsDao().query(user.username, displayableNews.id).get(0);
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

    private Context context;

    AppDatabase db ;
}
