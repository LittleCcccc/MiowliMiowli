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

    @Query("select news_id from news where category = :category and content like :keyword order by publishtime desc limit :size offset :skipped")
    List<SqlId> query_by_category_and_keyword(String category, String keyword, int size, int skipped);

    @Query("select news_id from news where category = :category order by publishtime desc limit :size offset :skipped")
    List<SqlId> query_by_category(String category, int size, int skipped);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SqlNews news);

    @Delete
    void delete(SqlNews news);
}
