package com.example.x9090.buku;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.x9090.buku.db.DaoAccess;
import com.example.x9090.buku.db.DaoFavorite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BukuAdapter.OnBukuClicked{

    List<Buku> bukuList;
    RecyclerView recyclerView;
    BukuAdapter adapter;
    Buku data;

    AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "mydb").allowMainThreadQueries().build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Buku");
        setSupportActionBar(toolbar);

        adapter = new BukuAdapter();
        adapter.setClickHandler(this);
        adapter.setBukuList(bukuList);

        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bukuList = new ArrayList<>();

        loadBuku();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadBuku();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menuCreate:
                openCreateActivity();
                break;
            case R.id.menuRefresh:
                loadBuku();
                break;
            case R.id.menuFav:
                openFavActivity();
                break;
        }
        return true;
    }


    @Override
    public void BukuClicked(Buku buku) {
        Intent detailMovieIntent = new Intent(this, DetailActivity.class);
        detailMovieIntent.putExtra("buku_parcelable", buku);
        startActivity(detailMovieIntent);
    }

    private void loadBuku() {
        if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
            LoadFromServer();
        } else {
            LoadFromLocale();
        }
    }

    private void LoadFromLocale() {
        bukuList = new ArrayList<>();
        DaoAccess daoAccess = database.getDaoAccess();
        bukuList = daoAccess.SelectAll();

        int i = 0;
        while(i < bukuList.size()){
            if(checkFavorite(bukuList.get(i).getId())){
                bukuList.get(i).fav = 1;
            }
            i = i+1;
        }

        Toast.makeText(this, "Load data dari database berhasil", Toast.LENGTH_SHORT).show();
        display();
    }

    private void LoadFromServer(){
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        bukuList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.URL_READ_BOOKS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
//
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject jbuku = array.getJSONObject(i);

                                //adding the product to product list
                                data = new Buku(
                                        jbuku.getInt("id"),
                                        jbuku.getString("judul"),
                                        jbuku.getString("penulis"),
                                        jbuku.getString("penerbit"),
                                        jbuku.getString("kota_terbit"),
                                        jbuku.getInt("jumlah_halaman"),
                                        jbuku.getString("photo_path")
                                );

                                data.setPhoto_path(Api.URL_GET_IMAGE+data.getPhoto_path());

                                bukuList.add(data);

                                if (checkFavorite(data.getId())){
                                    data.fav = 1;
                                }

                                DaoAccess daoAccess = database.getDaoAccess();
                                Buku x = daoAccess.fetchOneBukuById(data.getId());

                                if(x==null){
                                    daoAccess.insertOnlySingleBook(data);
                                }
                            }

                            display();
                            Toast.makeText(MainActivity.this, "Load data dari server berhasil", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error:Server tidak ditemukan", Toast.LENGTH_SHORT).show();
                        display();
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void display(){
        adapter.setmCtx(MainActivity.this);
        adapter.setBukuList(bukuList);
        recyclerView.setAdapter(adapter);
    }

    private void openCreateActivity() {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }

    private void openFavActivity() {
        Intent intent = new Intent(this, FavActivity.class);
        startActivity(intent);
    }

    private boolean checkFavorite(int id) {
        DaoFavorite daoFavorite = database.getDaoFavorite();
        Favorite x = daoFavorite.selectOne(id);

        if(x!=null){
            return true;
        }else{
            return false;
        }
    }
}
