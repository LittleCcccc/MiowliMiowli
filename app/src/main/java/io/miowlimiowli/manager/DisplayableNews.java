package io.miowlimiowli.manager;

import java.util.List;

import io.miowlimiowli.manager.data.RawNews;

/**
 * 修改成员应使用setter！
 */
public class DisplayableNews {
    public String title;
    public String content;
    public List<String> image_urls;
    public String id;

    public void setRead(boolean read) {
        this.read = read;
        if(read)
            Manager.getInstance().user.read_news.add(id);
        else
            Manager.getInstance().user.read_news.remove(id);
    }


    /**
     * 新闻是否阅读
     */
    public boolean read;
    public DisplayableNews(final RawNews news){
        this.title = news.title;
        this.content = news.content;
        this.image_urls = news.image_urls;
        this.id = news.id;
        this.read = Manager.getInstance().user.read_news.contains(news.id);
    }
}
