package io.miowlimiowli.manager;

import java.util.Date;
import java.util.List;

import io.miowlimiowli.manager.data.RawNews;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * 修改成员应使用setter！
 */
public class DisplayableNews {
    public String title;
    public String content;
    public List<String> image_urls;
    public String id;
    public Date publish_time;
    public String publisher_name;
    public Integer likecount;
    public Integer readcount;
    public Integer favoritecount;
    public String video_url;
    public DisplayableNews(final RawNews news){
        this.title = news.title;
        this.content = news.content;
        this.image_urls = news.image_urls;
        this.id = news.id;
        this.publisher_name = news.publisher;
        this.publish_time = news.publishtime;
        this.video_url = news.video_url;
        publisher = PublishSubject.create();
        publisher.observeOn(Schedulers.computation());
    }

    /**
     * @param favorite 设置此新闻是否收藏
     */
    public void setIsfavorite(boolean favorite) {
        this.isfavorite = favorite;
        publisher.onNext(this);
    }

    /**
     * 新闻是否收藏，修改请使用setIsfavorite
     */
    public boolean isfavorite;



    /**
     * @param isread 设置此新闻是否已读
     */
    public void setIsread(boolean isread) {
        this.isread = isread;
        publisher.onNext(this);
    }
    PublishSubject<DisplayableNews> publisher;

    /**
     * 新闻是否阅读，修改请使用setRead
     */
    public boolean isread;


    /**
     * @param islike 设置此新闻是否喜爱
     * @return
     */
    public void setIslike(boolean islike) {
        this.islike = islike;
        publisher.onNext(this);
    }

    /**
     * 新闻是否喜爱，修改请使用setLike
     */
    public boolean islike;
}
