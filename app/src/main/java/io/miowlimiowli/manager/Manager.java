package io.miowlimiowli.manager;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import androidx.room.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Publisher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;

import io.miowlimiowli.R;
import io.miowlimiowli.exceptions.UsernameEmptyError;
import io.miowlimiowli.fragment.BaseListFragment;
import io.miowlimiowli.fragment.NewsListFragment;
import io.miowlimiowli.manager.sql.SqlComment;
import io.miowlimiowli.manager.sql.SqlId;
import io.miowlimiowli.manager.sql.SqlNews;
import io.miowlimiowli.manager.sql.SqlUser;
import io.reactivex.Observable;
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
    public static List<String>  cat_list;
    private Manager() {
        cat_list = new ArrayList<>();
        for(int i=0;i<cat.length;i++)
            cat_list.add(cat[i]);
    }

    public int newsposi;
    public BaseListFragment listFragment;
    public DisplayableNews news;
    public DisplayableNews nextNews() throws Exception{
        System.out.println("现在的posi为"+newsposi);
        System.out.println("列表长度为"+listFragment.getmNews().size());
        news = listFragment.getmNews().get(newsposi);
        newsposi += 1;
        return news;
    }
    public boolean isLastNews(){
        if(newsposi >= listFragment.getmNews().size())
        {
            if(listFragment instanceof NewsListFragment)
            {
                listFragment.requireMoreNews(()->{});
            }
            if(newsposi>=listFragment.getmNews().size())
            {
                System.out.println("没有新闻了！");
                return true;
            }
        }
        return false;
    }

    public String[] cat = {"科技","社会","体育","娱乐","汽车","教育","文化","健康","军事","财经"};
    public String getCatAtPosition(int position)
    {
        return cat[position-1];
    }

    public boolean nopic = false;
    public void setNopic(boolean b){
        nopic = b;
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
        if (!user.getPassword().equals(password))
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
        try {
            init_thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (users.containsKey(username))
            throw new UsernameAlreadExistError();
        if(username.isEmpty())
            throw new UsernameEmptyError();
        User user = new User(username, email, password);
        user.cat_list = new ArrayList<>(cat_list);
        user.setAvator(context, context.getResources().getDrawable(R.drawable.logo, null));
        SqlUser sqlUser = new SqlUser(user);
        Observable.just("suck")
                .subscribeOn(Schedulers.io())
                .subscribe((item)->{db.SqlUserDao().insert(sqlUser);});
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
                List<RawNews> list =  RawNews.fetch_news_from_server(size, page, null, null, keyword, category);
                for(RawNews news : list){
                    db.SqlNewsDao().insert(new SqlNews(news));
                }
                return list;
            }catch (Exception e){
                //离线时，从本地获取新闻
                System.out.println("offline mode");
                System.out.println(category + keyword + size + (page - 1) * size);
                List<SqlId> list = db.SqlNewsDao().query_by_category(category, size, (page - 1) * size);
                System.out.println(db.SqlNewsDao().getAll().size());
                System.out.println(list.size());
                List<RawNews> ret = new ArrayList<>();
                for(SqlId id: list){
                    if(newses.get(id.news_id) == null) {
                        System.out.println("null Get");
                        System.out.println(id.news_id);
                    }
                    ret.add(newses.get(id.news_id));
                    //System.out.println(newses.get(id.news_id).title);
                }
                return ret;
            }
        }).flatMapIterable((item)->item)
                .map(item ->{
                    newses.put(item.id, item);
                    return item;
                })
                .map(DisplayableNews::new)
                .map(new WrapDisplayableNews())
                .toList().subscribeOn(Schedulers.io()).observeOn((AndroidSchedulers.mainThread()));
    }

    /**
     * @return 推荐新闻列表
     */
    public Single<List<DisplayableNews>> fetch_recommend_news(){
        return Flowable.fromCallable(()-> {
            try {
                List<RawNews> list =  RawNews.fetch_news_from_server(100, 1, null, null, "", "");
                List<SqlId> idread = db.SqlUserandNewsDao().getReadListByUsername(user.username);
                List<RawNews> readlist = new ArrayList<>();
                for(SqlId id : idread){
                    readlist.add(newses.get(id.news_id));
                }
                List<SqlId> idfavorite = db.SqlUserandNewsDao().getFavoriteListByUsername(user.username);
                List<RawNews> favoritelist = new ArrayList<>();
                for(SqlId id : idfavorite){
                    favoritelist.add(newses.get(id.news_id));
                }

                if(idfavorite.size() == 0 && idread.size() == 0)
                    return list;

                return Recommender.fetch_recommand_list(list, readlist, favoritelist);
            } catch (JSONException | ParseException | IOException e) {
                return new ArrayList<RawNews>();
            }
        }).flatMapIterable((item)->item)
                .map(item ->{
                    newses.put(item.id, item);
                    return item;
                })
                .map(DisplayableNews::new)
                .map(new WrapDisplayableNews())
                .toList()
                .subscribeOn(Schedulers.io()).observeOn((AndroidSchedulers.mainThread()));
    }

    /**
     * 获取时间范围内的新闻
     * @param size 一页数量
     * @param page 第几页的新闻
     * @param begin 开始时间
     * @param end 截至时间
     * @param category 种类
     * @param keyword 关键词
     * @return
     */
    public Single<List<DisplayableNews>> fetch_news_by_all(final int size, final int page,  final Date begin,final Date end,final String category, final String keyword) {
        return Flowable.fromCallable(()->{
            try {
                return RawNews.fetch_news_from_server(size, page, begin, end, keyword, category);
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
        return Single.fromCallable(()-> newses.get(news_id))
                .map(DisplayableNews::new)
                .map(new WrapDisplayableNews())
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
    public Single<DisplayableComment> add_comment(String news_id, String content, Date date){
        return Single.fromCallable(()->{
            SqlComment cmt = new SqlComment();
            cmt.content = content;
            cmt.news_id = news_id;
            cmt.publish_date = date;
            cmt.username = user.username;
            db.SqlCommentDao().insert(cmt);
            WrapDisplayableComment f = new WrapDisplayableComment();
            return f.apply(new DisplayableComment(cmt));
        }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());

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
            news.publisher.observeOn(Schedulers.computation()).subscribe(displayableNews ->  {
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


    public AppDatabase db ;
    private Context context;
    Thread init_thread;
    Runnable init_userdata = ()->{
        for(SqlUser user : db.SqlUserDao().getall()){
            users.put(user.username, new User(user));
        }
        String[] files = context.fileList();
        for(String filename: files){
            if(filename.endsWith(".png")){
                String username = filename.substring(0, filename.length() - 4);
                System.out.println(filename);
                System.out.println(context.getFilesDir().getAbsolutePath() + filename);
                users.get(username).avator = new BitmapDrawable(context.getResources(),BitmapFactory.decodeFile(context.getFilesDir().getAbsolutePath() + "/" + filename));
            }
        }
    };
    /**
     * 此函数只允许在启动时调用一次
     * @param context Application Context
     */
    public void setContext(Context context) {
        this.context = context;
        RawNews.context = context;
        try {
            InputStream in = context.openFileInput("news.json");
            Scanner scanner = new Scanner(in);
            while(scanner.hasNext()){
                String str = scanner.nextLine();
                if(str.equals(""))
                    continue;
                JSONObject obj = new JSONObject(str);
                RawNews news = new RawNews(obj);
                newses.put(news.id, news);
            }
        } catch (FileNotFoundException | JSONException | ParseException e) {
            System.out.println("error in read from local file");
            e.printStackTrace();
        }

        db  = Room.databaseBuilder(context,
                AppDatabase.class,"mydatabase").build();
        init_thread = new Thread(init_userdata);
        init_thread.start();
    }

    /**
     * @return 获取当前用户的类别列表
     */
    public List<String> getCat_list(){
        return user.cat_list;
    }

    public void setCat_list(List<String> list){user.setCat_list(list);}




}
