package io.miowlimiowli.manager.sql;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.*;

import java.util.List;

@Dao
public interface SqlUserandNewsDao {
    @Query("Select news_id from usersnews where username = :username and islike = 1")
    List<SqlId> getLikeListByUsername(String username);

    @Query("Select news_id from usersnews where username = :username and isread = 1")
    List<SqlId> getReadListByUsername(String username);

    @Query("Select news_id from usersnews where username = :username and isfavorite")
    List<SqlId> getFavoriteListByUsername(String username);

    @Query("Select * from usersnews where username = :username and news_id = :id")
    SqlUserandNews query(String username, String id);

    @Query("Select count(news_id) from usersnews where islike = 1 and news_id = :id")
    int countLike(String id);

    @Query("Select count(news_id) from usersnews where isread = 1 and news_id = :id")
    int countRead(String id);

    @Query("Select count(news_id) from usersnews where isfavorite = 1 and news_id = :id")
    int countFavorate(String id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SqlUserandNews entity);
    @Update
    void update(SqlUserandNews entity);
    @Delete
    void delete(SqlUserandNews user);
}

