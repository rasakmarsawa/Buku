package com.example.x9090.buku.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.x9090.buku.Buku;

import java.util.List;

@Dao
public interface DaoAccess {
    @Insert
    void insertOnlySingleBook(Buku buku);

    @Query("SELECT * FROM Buku WHERE id = :id")
    Buku fetchOneBukuById(int id);

    @Query("SELECT * FROM Buku")
    List<Buku> SelectAll();

    @Query("SELECT Buku.* FROM Buku INNER JOIN favorite ON Buku.id = favorite.fav_id")
    List<Buku> SelectFavorite();
}
