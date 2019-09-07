package io.miowlimiowli.manager.sql;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SqlUserDao {
    @Query("select * from user")
    List<SqlUser> getall();


    @Update
    void update(SqlUser user);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SqlUser user);

    @Delete
    void delete(SqlUser user);
}
