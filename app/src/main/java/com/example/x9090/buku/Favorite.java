package com.example.x9090.buku;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "favorite",
        foreignKeys = @ForeignKey(entity = Buku.class,
            parentColumns = "id",
            childColumns = "fav_id",
            onDelete = CASCADE))
public class Favorite {
    @PrimaryKey
    private int fav_id;

    public Favorite(int fav_id) {
        this.fav_id = fav_id;
    }

    public Favorite() {
    }

    public int getFav_id() {
        return fav_id;
    }

    public void setFav_id(int fav_id) {
        this.fav_id = fav_id;
    }
}