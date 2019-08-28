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


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SqlUserandNews entity);

    @Delete
    void delete(SqlUserandNews user);
}

