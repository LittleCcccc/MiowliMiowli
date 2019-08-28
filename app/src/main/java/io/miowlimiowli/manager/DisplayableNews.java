package io.miowlimiowli.manager;

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

    public DisplayableNews(final RawNews news){
        this.title = news.title;
        this.content = news.content;
        this.image_urls = news.image_urls;
        this.id = news.id;
        publisher = PublishSubject.create();
        publisher.observeOn(Schedulers.computation());
    }

    /**
     * @param isread 设置此新闻是否已读
     * @return
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
