package io.miowlimiowli.manager.sql;

import androidx.annotation.NonNull;
import androidx.room.*;

import java.util.Date;


@Entity(tableName = "news")
@TypeConverters(DateConverter.class)
public class SqlNews {
    @PrimaryKey@NonNull
    public String id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "begin")
    public Date begin;

    @ColumnInfo(name = "end")
    public Date end;
}

