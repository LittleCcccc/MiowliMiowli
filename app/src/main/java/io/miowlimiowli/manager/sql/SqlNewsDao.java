package io.miowlimiowli.manager.sql;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SqlNewsDao {
    @Query("SELECT * FROM news")
    List<SqlNews> getAll();


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SqlNews news);

    @Delete
    void delete(SqlNews news);
}
