package com.hoanglam0869.appnhac.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hoanglam0869.appnhac.Adapter.DanhSachCacPlaylistAdapter;
import com.hoanglam0869.appnhac.Model.PlayList;
import com.hoanglam0869.appnhac.R;
import com.hoanglam0869.appnhac.Service.APIService;
import com.hoanglam0869.appnhac.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachCacPlaylistActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerViewDanhSachCacPlayList;
    DanhSachCacPlaylistAdapter danhSachCacPlaylistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_cac_playlist);

        anhXa();
        init();
        GetData();
    }

    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<PlayList>> callBack = dataService.GetDanhSachCacPlayList();
        callBack.enqueue(new Callback<List<PlayList>>() {
            @Override
            public void onResponse(Call<List<PlayList>> call, Response<List<PlayList>> response) {
                ArrayList<PlayList> mangPlaylist = (ArrayList<PlayList>) response.body();
                danhSachCacPlaylistAdapter = new DanhSachCacPlaylistAdapter(DanhSachCacPlaylistActivity.this, mangPlaylist);
                recyclerViewDanhSachCacPlayList.setLayoutManager(new GridLayoutManager(DanhSachCacPlaylistActivity.this, 2));
                recyclerViewDanhSachCacPlayList.setAdapter(danhSachCacPlaylistAdapter);
            }

            @Override
            public void onFailure(Call<List<PlayList>> call, Throwable t) {
                Log.d("BBB", t.getMessage());
            }
        });
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Playlists");
        toolbar.setTitleTextColor(getResources().getColor(R.color.mautim));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhXa() {
        toolbar = findViewById(R.id.toolBarDanhSachCacPlaylist);
        recyclerViewDanhSachCacPlayList = findViewById(R.id.recyclerViewDanhSachCacPlaylist);
    }
}