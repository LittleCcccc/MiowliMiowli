package io.miowlimiowli.manager.sql;

import androidx.room.Database;
import androidx.room.RoomDatabase;

//TODO:exportSchema = false for test
@Database(entities = {SqlNews.class, SqlUserandNews.class, SqlComment.class, SqlUser.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SqlNewsDao SqlNewsDao();
    public abstract  SqlUserandNewsDao SqlUserandNewsDao();
    public abstract  SqlCommentDao SqlCommentDao();
    public abstract  SqlUserDao SqlUserDao();
}