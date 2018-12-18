package com.example.x9090.buku;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.x9090.buku.db.DaoFavorite;

public class DetailActivity extends AppCompatActivity {

    Buku buku;
    TextView TVJudul, TVPenulis, TVPenerbit, TVKotaTerbit, TVJumlahHalaman;
    ImageView IVIMG;

    MenuItem favbutton;

    AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "mydb").allowMainThreadQueries().build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Detail Buku");
        setSupportActionBar(toolbar);

        Intent detailIntent = getIntent();
        if(null != detailIntent) {
            buku = detailIntent.getParcelableExtra("buku_parcelable");
        }

        TVJudul = (TextView) findViewById(R.id.text_judul);
        TVPenulis = (TextView) findViewById(R.id.text_penulis);
        TVPenerbit = (TextView) findViewById(R.id.text_penerbit);
        TVKotaTerbit = (TextView) findViewById(R.id.text_kota_terbit);
        TVJumlahHalaman = (TextView) findViewById(R.id.text_jumlah_halaman);

        IVIMG = (ImageView) findViewById(R.id.image_poster);
        //loading the image
        Glide.with(this)
                .load(buku.getPhoto_path())
                .into(IVIMG);

        TVJudul.setText(buku.getJudul());
        TVPenulis.setText(buku.getPenulis());
        TVPenerbit.setText(buku.getPenerbit());
        TVKotaTerbit.setText(buku.getKota_terbit());
        TVJumlahHalaman.setText(String.valueOf(buku.getJumlah_halaman())+" Halaman");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        favbutton = menu.getItem(0);

        if(checkFavorite()){
            favbutton.setIcon(getResources().getDrawable(R.drawable.ic_action_fav));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_share:

                String x = "Buku berjudul '"+buku.getJudul()+"' ditulis oleh "+buku.getPenulis()
                        +". Buku ini diterbitkan di kota "+buku.getKota_terbit()+" oleh penerbit "+buku.getPenerbit()
                        +". Jumlah halaman buku ini ialah "+buku.getJumlah_halaman()+" halaman.";

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = x;
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
                return true;

            case R.id.menu_fav:
                if(checkFavorite()==false){
                    addFavorite();
                }else{
                    deleteFavorite();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteFavorite() {
        DaoFavorite daoFavorite = database.getDaoFavorite();
        daoFavorite.delete(buku.getId());

        favbutton.setIcon(getResources().getDrawable(R.drawable.ic_action_non_fav));
        Toast.makeText(this, "Favorit dibatalkan", Toast.LENGTH_SHORT).show();
    }

    private void addFavorite() {

        DaoFavorite daoFavorite = database.getDaoFavorite();
        Favorite favorite = new Favorite(buku.getId());
        daoFavorite.insert(favorite);

        favbutton.setIcon(getResources().getDrawable(R.drawable.ic_action_fav));
        Toast.makeText(this, "Favorit ditambah", Toast.LENGTH_SHORT).show();
    }

    private boolean checkFavorite() {
        DaoFavorite daoFavorite = database.getDaoFavorite();
        Favorite x = daoFavorite.selectOne(buku.getId());

        if(x!=null){
            return true;
        }else{
            return false;
        }
    }


}
