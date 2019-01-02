package com.example.x9090.buku;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.x9090.buku.db.DaoAccess;
import com.example.x9090.buku.db.DaoFavorite;

@Database(entities = {Buku.class,Favorite.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoAccess getDaoAccess();

    public abstract DaoFavorite getDaoFavorite();


}
