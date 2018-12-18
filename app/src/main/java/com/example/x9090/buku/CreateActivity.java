package com.example.x9090.buku;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;

import java.io.IOException;
import java.util.UUID;

public class CreateActivity extends AppCompatActivity {

    EditText ejudul, epenulis, epenerbit, ekota_terbit, ejumlah_halaman, ephoto_path;
    Button buttonSelect;
    ImageView imageView;

    private int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Bitmap bitmap;
    private Uri filePath;
    private String realpath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        requestStoragePermission();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tambah Buku");
        setSupportActionBar(toolbar);

        ejudul = (EditText) findViewById(R.id.editTextJudul);
        epenulis = (EditText) findViewById(R.id.editTextPenulis);
        epenerbit = (EditText) findViewById(R.id.editTextPenerbit);
        ekota_terbit = (EditText) findViewById(R.id.editTextKotaTerbit);
        ejumlah_halaman = (EditText) findViewById(R.id.editTextJumlahHalaman);
        buttonSelect = (Button) findViewById(R.id.buttonSelect);
        imageView = (ImageView) findViewById(R.id.imageView);

        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        if(savedInstanceState!=null){
            realpath = savedInstanceState.getString("path");
            imageView.setImageBitmap(BitmapFactory.decodeFile(realpath));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.create_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menuSave:
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    uploadMultipart();
                } else {
                    Toast.makeText(this, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
                }

                break;
        }
        return true;
    }

    public void uploadMultipart() {
        String judul = ejudul.getText().toString().trim();
        String penulis = epenulis.getText().toString().trim();
        String penerbit = epenerbit.getText().toString().trim();
        String kota_terbit = ekota_terbit.getText().toString().trim();
        String jumlah_halaman = ejumlah_halaman.getText().toString().trim();
        if(judul==null){
            Toast.makeText(this,"Judul Tidak Ada",Toast.LENGTH_SHORT).show();
        }

        try {
            //getting the actual path of the image
            String path = realpath;

            //Uploading code
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, Api.URL_CREATE_BOOK)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("judul", judul)
                    .addParameter("penulis", penulis)
                    .addParameter("penerbit", penerbit)
                    .addParameter("kota_terbit", kota_terbit)
                    .addParameter("jumlah_halaman", jumlah_halaman)
//                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload
            Toast.makeText(this, "Buku berhasil ditambahkan", Toast.LENGTH_SHORT).show();

        } catch (Exception exc) {
            if(exc.getMessage()=="uri"){
                Toast.makeText(this, "Gambar belum dipilih", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                realpath = getPath(filePath);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("path",realpath);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
}
