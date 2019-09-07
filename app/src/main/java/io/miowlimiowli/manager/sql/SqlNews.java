package io.miowlimiowli.manager.sql;

import androidx.annotation.NonNull;
import androidx.room.*;

import java.util.Date;

import io.miowlimiowli.manager.data.RawNews;


@Entity(tableName = "news")
@TypeConverters(DateConverter.class)
public class SqlNews {
    @PrimaryKey@NonNull
    public String news_id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "publishtime")
    public Date publishtime;

    public SqlNews(){};

    public SqlNews(RawNews news){
        this.news_id = news.id;
        this.title = news.title;
        this.content = news.content;
        this.category = news.catagory;
        this.publishtime = news.publishtime;
    }

}

