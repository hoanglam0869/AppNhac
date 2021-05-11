package com.hoanglam0869.appnhac.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hoanglam0869.appnhac.Adapter.DanhSachTatCaChuDeAdapter;
import com.hoanglam0869.appnhac.Model.ChuDe;
import com.hoanglam0869.appnhac.R;
import com.hoanglam0869.appnhac.Service.APIService;
import com.hoanglam0869.appnhac.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachTatCaChuDeActivity extends AppCompatActivity {

    RecyclerView recyclerViewTatCaChuDe;
    Toolbar toolbarTatCaChuDe;
    DanhSachTatCaChuDeAdapter danhSachTatCaChuDeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_tat_ca_chu_de);

        init();
        GetData();
    }

    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<ChuDe>> callBack = dataService.GetAllChuDe();
        callBack.enqueue(new Callback<List<ChuDe>>() {
            @Override
            public void onResponse(Call<List<ChuDe>> call, Response<List<ChuDe>> response) {
                ArrayList<ChuDe> mangChuDe = (ArrayList<ChuDe>) response.body();
                danhSachTatCaChuDeAdapter = new DanhSachTatCaChuDeAdapter(DanhSachTatCaChuDeActivity.this, mangChuDe);
                recyclerViewTatCaChuDe.setLayoutManager(new GridLayoutManager(DanhSachTatCaChuDeActivity.this, 1));
                recyclerViewTatCaChuDe.setAdapter(danhSachTatCaChuDeAdapter);
            }

            @Override
            public void onFailure(Call<List<ChuDe>> call, Throwable t) {
                Log.d("BBB", t.getMessage());
            }
        });
    }

    private void init() {
        recyclerViewTatCaChuDe = findViewById(R.id.recyclerViewAllChuDe);
        toolbarTatCaChuDe = findViewById(R.id.toolBarAllChuDe);
        setSupportActionBar(toolbarTatCaChuDe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất Cả Chủ Đề");
        toolbarTatCaChuDe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}