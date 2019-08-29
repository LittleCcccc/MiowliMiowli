package io.miowlimiowli.manager.sql;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SqlCommentDao {
    @Query("Select * from comment where news_id = :news_id order by publish_date")
    List<SqlComment> getCommentByNewsid(String news_id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SqlNews news);

    @Delete
    void delete(SqlNews news);
}
