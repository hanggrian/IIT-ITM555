package com.example.pokemon.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.pokemon.db.schema.Member;
import java.util.List;

@Dao
public interface Members {
    @Query("SELECT COUNT(*) FROM member")
    int getCount();

    @Query("SELECT EXISTS(SELECT 1 FROM member WHERE pokemon = :pokemon)")
    boolean isExist(String pokemon);

    @Query("SELECT * FROM member")
    List<Member> getAll();

    @Query("SELECT * FROM member WHERE pokemon = :pokemon LIMIT 1")
    Member findById(String pokemon);

    @Insert
    void insertAll(Member... members);

    @Update
    void update(Member member);

    @Delete
    void delete(Member member);

    @Query("DELETE FROM member")
    void deleteAll();
}
