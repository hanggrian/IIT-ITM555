package com.example.marks.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.marks.db.schema.Mark;
import java.util.List;

/**
 * A Data Access Object for {@link Mark} entity.
 */
@Dao
public interface Marks {
    @Query("SELECT COUNT(*) FROM mark")
    int getCount();

    @Query("SELECT EXISTS(SELECT 1 FROM mark WHERE id = :id)")
    boolean isExist(int id);

    @Query("SELECT * FROM mark")
    List<Mark> getAll();

    @Query("SELECT * FROM mark WHERE id = :id LIMIT 1")
    Mark findById(String id);

    @Insert
    void insertAll(Mark... marks);

    @Update
    void update(Mark mark);

    @Delete
    void delete(Mark mark);

    @Query("DELETE FROM mark")
    void deleteAll();
}
