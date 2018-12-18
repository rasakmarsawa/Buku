package com.example.x9090.buku;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName="Buku")
public class Buku  implements Parcelable {

    @PrimaryKey
    private int id;
    @ColumnInfo
    private String judul, penulis, penerbit, kota_terbit;
    @ColumnInfo
    private int jumlah_halaman;
    @ColumnInfo
    private String photo_path;

    @Ignore
    public int fav = 0;

    public Buku() {
    }

    public Buku(int id, String judul, String penulis, String penerbit, String kota_terbit, int jumlah_halaman, String photo_path) {
        this.id = id;
        this.judul = judul;
        this.penulis = penulis;
        this.penerbit = penerbit;
        this.kota_terbit = kota_terbit;
        this.jumlah_halaman = jumlah_halaman;
        this.photo_path = photo_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getKota_terbit() {
        return kota_terbit;
    }

    public void setKota_terbit(String kota_terbit) {
        this.kota_terbit = kota_terbit;
    }

    public int getJumlah_halaman() {
        return jumlah_halaman;
    }

    public void setJumlah_halaman(int jumlah_halaman) {
        this.jumlah_halaman = jumlah_halaman;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.judul);
        dest.writeString(this.penulis);
        dest.writeString(this.penerbit);
        dest.writeString(this.kota_terbit);
        dest.writeInt(this.jumlah_halaman);
        dest.writeString(this.photo_path);
    }

    protected Buku(Parcel in) {
        this.id = in.readInt();
        this.judul = in.readString();
        this.penulis = in.readString();
        this.penerbit = in.readString();
        this.kota_terbit = in.readString();
        this.jumlah_halaman = in.readInt();
        this.photo_path = in.readString();
    }

    public static final Parcelable.Creator<Buku> CREATOR = new Parcelable.Creator<Buku>() {
        @Override
        public Buku createFromParcel(Parcel source) {
            return new Buku(source);
        }

        @Override
        public Buku[] newArray(int size) {
            return new Buku[size];
        }
    };
}
