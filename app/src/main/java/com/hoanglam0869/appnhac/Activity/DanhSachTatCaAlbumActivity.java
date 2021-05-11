package com.hoanglam0869.appnhac.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hoanglam0869.appnhac.Adapter.AllAlbumAdapter;
import com.hoanglam0869.appnhac.Model.Album;
import com.hoanglam0869.appnhac.R;
import com.hoanglam0869.appnhac.Service.APIService;
import com.hoanglam0869.appnhac.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachTatCaAlbumActivity extends AppCompatActivity {

    RecyclerView recyclerViewAllAlbum;
    Toolbar toolbarAllAlbum;
    AllAlbumAdapter allAlbumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_tat_ca_album);

        init();
        GetData();
    }

    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<Album>> callBack = dataService.GetAllAlbum();
        callBack.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                ArrayList<Album> mangAlbum = (ArrayList<Album>) response.body();
                allAlbumAdapter = new AllAlbumAdapter(DanhSachTatCaAlbumActivity.this, mangAlbum);
                recyclerViewAllAlbum.setLayoutManager(new GridLayoutManager(DanhSachTatCaAlbumActivity.this, 2));
                recyclerViewAllAlbum.setAdapter(allAlbumAdapter);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                Log.d("BBB", t.getMessage());
            }
        });
    }

    private void init() {
        recyclerViewAllAlbum = findViewById(R.id.recyclerViewAllAlbum);
        toolbarAllAlbum = findViewById(R.id.toolBarAllAlbum);
        setSupportActionBar(toolbarAllAlbum);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất Cả Album");
        toolbarAllAlbum.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}