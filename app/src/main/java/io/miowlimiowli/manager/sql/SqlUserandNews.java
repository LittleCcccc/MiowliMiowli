package io.miowlimiowli.manager.sql;

import androidx.annotation.NonNull;
import androidx.room.*;

@Entity(primaryKeys = {"username","news_id"},tableName = "usersnews")
public class SqlUserandNews {
    @ColumnInfo(name = "username")@NonNull
    public String username;

    @ColumnInfo(name = "news_id")@NonNull
    public String news_id;

    @ColumnInfo(name = "islike")
    public boolean islike;

    @ColumnInfo(name = "isread")
    public boolean isread;

    @ColumnInfo(name = "isfavorite")
    public boolean isfavorite;
}
