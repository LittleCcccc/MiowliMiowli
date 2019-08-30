package io.miowlimiowli.manager.sql;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "comment")
@TypeConverters(DateConverter.class)
public class SqlComment {
    @PrimaryKey(autoGenerate = true)
    public int cmt_id;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "news_id")
    public String news_id;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "publish_date")
    public Date publish_date;
}
