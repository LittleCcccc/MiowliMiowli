package io.miowlimiowli.manager.sql;

import android.app.Instrumentation;
import android.test.mock.MockContext;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SqlUserandNewsDaoTest {

    @Test
    public void getLikeListByUsername() {
        AppDatabase db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
        AppDatabase.class).build();
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
        s.islike = true;
        db.SqlUserandNewsDao().insert(s);
        s = new SqlUserandNews();
        s.news_id = "2";
        s.username = "a";
        s.islike = true;
        db.SqlUserandNewsDao().insert(s);
        s = new SqlUserandNews();
        s.news_id = "2";
        s.username = "b";
        s.islike = true;
        db.SqlUserandNewsDao().insert(s);
        List<SqlId> list = db.SqlUserandNewsDao().getLikeListByUsername("a");
        System.out.println("hahaahh");

        System.out.println(list.size());
        System.out.println(list.get(0).news_id);
        assertEquals(list.size(), 2);
    }
}