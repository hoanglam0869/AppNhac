package com.hoanglam0869.appnhac.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hoanglam0869.appnhac.Adapter.DanhSachBaiHatAdapter;
import com.hoanglam0869.appnhac.Model.Album;
import com.hoanglam0869.appnhac.Model.BaiHat;
import com.hoanglam0869.appnhac.Model.PlayList;
import com.hoanglam0869.appnhac.Model.QuangCao;
import com.hoanglam0869.appnhac.Model.TheLoai;
import com.hoanglam0869.appnhac.R;
import com.hoanglam0869.appnhac.Service.APIService;
import com.hoanglam0869.appnhac.Service.DataService;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachBaiHatActivity extends AppCompatActivity {

    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    RecyclerView recyclerViewDanhSachBaiHat;
    FloatingActionButton floatingActionButton;
    QuangCao quangCao;
    ImageView imgDanhSachCaKhuc;
    ArrayList<BaiHat> mangBaiHat;
    DanhSachBaiHatAdapter danhSachBaiHatAdapter;
    PlayList playList;
    TheLoai theLoai;
    Album album;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_bai_hat);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        DataIntent();
        anhXa();
        init();
        if (quangCao != null && !quangCao.getTenBaiHat().equals("")) {
            setValueInView(quangCao.getTenBaiHat(), quangCao.getHinhBaiHat());
            new ConvertUrlToBitmap().execute(quangCao.getHinhBaiHat());
            GetDataQuangCao(quangCao.getIdQuangCao());
        }
        if (playList != null && !playList.getTen().equals("")) {
            setValueInView(playList.getTen(), playList.getHinhPlaylist());
            new ConvertUrlToBitmap().execute(playList.getHinhPlaylist());
            GetDataPlaylist(playList.getIdPlaylist());
        }
        if (theLoai != null && !theLoai.getTenTheLoai().equals("")) {
            setValueInView(theLoai.getTenTheLoai(), theLoai.getHinhTheLoai());
            new ConvertUrlToBitmap().execute(theLoai.getHinhTheLoai());
            GetDataTheLoai(theLoai.getIdTheLoai());
        }
        if (album != null && !album.getTenAlbum().equals("")) {
            setValueInView(album.getTenAlbum(), album.getHinhanhAlbum());
            new ConvertUrlToBitmap().execute(album.getHinhanhAlbum());
            GetDataAlbum(album.getIdAlbum());
        }
    }

    private void GetDataAlbum(String idAlbum) {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callBack = dataService.GetDanhSachBaiHatTheoAlbum(idAlbum);
        callBack.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                mangBaiHat = (ArrayList<BaiHat>) response.body();
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhSachBaiHatActivity.this, mangBaiHat);
                recyclerViewDanhSachBaiHat.setLayoutManager(new LinearLayoutManager(DanhSachBaiHatActivity.this));
                recyclerViewDanhSachBaiHat.setAdapter(danhSachBaiHatAdapter);
                eventClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void GetDataTheLoai(String idtheloai) {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callBack = dataService.GetDanhSachBaiHatTheoTheLoai(idtheloai);
        callBack.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                mangBaiHat = (ArrayList<BaiHat>) response.body();
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhSachBaiHatActivity.this, mangBaiHat);
                recyclerViewDanhSachBaiHat.setLayoutManager(new LinearLayoutManager(DanhSachBaiHatActivity.this));
                recyclerViewDanhSachBaiHat.setAdapter(danhSachBaiHatAdapter);
                eventClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void GetDataPlaylist(String idplaylist) {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callBack = dataService.GetDanhSachBaiHatTheoPlaylist(idplaylist);
        callBack.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                mangBaiHat = (ArrayList<BaiHat>) response.body();
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhSachBaiHatActivity.this, mangBaiHat);
                recyclerViewDanhSachBaiHat.setLayoutManager(new LinearLayoutManager(DanhSachBaiHatActivity.this));
                recyclerViewDanhSachBaiHat.setAdapter(danhSachBaiHatAdapter);
                eventClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void GetDataQuangCao(String idquangcao) {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callBack = dataService.GetDanhSachBaiHatTheoQuangCao(idquangcao);
        callBack.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                mangBaiHat = (ArrayList<BaiHat>) response.body();
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhSachBaiHatActivity.this, mangBaiHat);
                recyclerViewDanhSachBaiHat.setLayoutManager(new LinearLayoutManager(DanhSachBaiHatActivity.this));
                recyclerViewDanhSachBaiHat.setAdapter(danhSachBaiHatAdapter);
                eventClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {
                Log.d("BBB", t.getMessage());
            }
        });
    }

    private class ConvertUrlToBitmap extends AsyncTask<String, Long, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                return true;
            } catch (Exception e) {
                Log.e("BBB", e.toString());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                // download was successful
                // if you want to update your UI, make sure u do it on main thread. like this:
                DanhSachBaiHatActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // update UI here
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                        collapsingToolbarLayout.setBackground(bitmapDrawable);
                    }
                });
            } else {
                // download failed
            }
        }
    }

    private void setValueInView(String ten, String hinh) {
        collapsingToolbarLayout.setTitle(ten);

        /*try {
            URL url = new URL(hinh);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
            collapsingToolbarLayout.setBackground(bitmapDrawable);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        Picasso.get().load(hinh).into(imgDanhSachCaKhuc);
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        floatingActionButton.setEnabled(false);
    }

    private void anhXa() {
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        toolbar = findViewById(R.id.toolBarDanhSach);
        recyclerViewDanhSachBaiHat = findViewById(R.id.recyclerViewDanhSachBaiHat);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        imgDanhSachCaKhuc = findViewById(R.id.imageViewDanhSachCaKhuc);
    }

    private void DataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("banner")) {
                quangCao = (QuangCao) intent.getSerializableExtra("banner");
            }
            if (intent.hasExtra("itemplaylist")) {
                playList = (PlayList) intent.getSerializableExtra("itemplaylist");
            }
            if (intent.hasExtra("idtheloai")) {
                theLoai = (TheLoai) intent.getSerializableExtra("idtheloai");
            }
            if (intent.hasExtra("album")) {
                album = (Album) intent.getSerializableExtra("album");
            }
        }
    }

    private void eventClick() {
        floatingActionButton.setEnabled(true);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachBaiHatActivity.this, PlayNhacActivity.class);
                intent.putExtra("cacbaihat", mangBaiHat);
                startActivity(intent);
            }
        });
    }
}