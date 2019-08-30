package io.miowlimiowli.manager;

import androidx.room.ColumnInfo;

import java.util.Date;

import io.miowlimiowli.manager.sql.SqlComment;

public class DisplayableComment {
    private int cmt_id;

    public String username;

    public String news_id;

    public User user;

    public DisplayableNews news;

    public String content;

    public Date publish_date;

    public DisplayableComment(SqlComment comment){
        cmt_id = comment.cmt_id;
        username = comment.username;
        news_id = comment.news_id;
        content = comment.content;
        publish_date = comment.publish_date;
    }
}
