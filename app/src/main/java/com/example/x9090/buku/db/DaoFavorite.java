package com.example.x9090.buku.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.x9090.buku.Favorite;

@Dao
public interface DaoFavorite {
    @Insert
    void insert(Favorite favorite);

    @Query("DELETE FROM favorite WHERE fav_id = :id")
    void delete(int id);

    @Query("SELECT * FROM favorite WHERE fav_id = :id")
    Favorite selectOne(int id);
}
