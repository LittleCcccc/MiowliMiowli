package io.miowlimiowli.manager.sql;

import android.app.Instrumentation;
import android.test.mock.MockContext;

import androidx.room.Room;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SqlUserandNewsDaoTest {

    @Test
    public void getLikeListByUsername() {
        AppDatabase db = Room.databaseBuilder(new MockContext(),
        AppDatabase.class, "user").build();
        SqlNews news = new SqlNews();
        news.id = "1";
        db.SqlNewsDao().insert(news);

        news = new SqlNews();
        news.id = "2";
        db.SqlNewsDao().insert(news);
        news = new SqlNews();
        news.id = "3";
        db.SqlNewsDao().insert(news);

        SqlUserandNews s = new SqlUserandNews();
        s.news_id = "1";
        s.username = "a";
        db.SqlUserandNewsDao().insert(s);
        List<SqlId> list = db.SqlUserandNewsDao().getLikeListByUsername("a");
        System.out.println(list.size());
    }
}