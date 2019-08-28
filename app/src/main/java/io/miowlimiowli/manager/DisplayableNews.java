package io.miowlimiowli.manager;

import android.util.Pair;

import java.util.List;

import io.miowlimiowli.manager.data.RawNews;
import io.reactivex.Observable;
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

    /**
     * @param read 设置此新闻是否已读
     * @return
     */
    public void setRead(boolean read) {
        this.read = read;
        isread_publisher.onNext(new Pair<>(id, read));
    }
    PublishSubject<Pair<String, Boolean>> isread_publisher;

    /**
     * 新闻是否阅读，修改请使用setRead
     */
    public boolean read;
    public DisplayableNews(final RawNews news){
        this.title = news.title;
        this.content = news.content;
        this.image_urls = news.image_urls;
        this.id = news.id;
        isread_publisher = PublishSubject.create();
        isread_publisher.observeOn(Schedulers.computation());
    }
}
