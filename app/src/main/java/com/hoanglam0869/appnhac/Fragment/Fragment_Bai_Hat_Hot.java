package com.hoanglam0869.appnhac.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hoanglam0869.appnhac.Adapter.BaiHatHotAdapter;
import com.hoanglam0869.appnhac.Model.BaiHat;
import com.hoanglam0869.appnhac.R;
import com.hoanglam0869.appnhac.Service.APIService;
import com.hoanglam0869.appnhac.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Bai_Hat_Hot extends Fragment {

    View view;
    RecyclerView recyclerViewBaiHatHot;
    BaiHatHotAdapter baiHatHotAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bai_hat_thich_nhat, container, false);
        recyclerViewBaiHatHot = view.findViewById(R.id.recyclerViewBaiHatHot);
        GetData();
        return view;
    }

    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callBack = dataService.GetBaiHatHot();
        callBack.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                ArrayList<BaiHat> baiHatArrayList = (ArrayList<BaiHat>) response.body();
                baiHatHotAdapter = new BaiHatHotAdapter(getActivity(), baiHatArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewBaiHatHot.setLayoutManager(linearLayoutManager);
                recyclerViewBaiHatHot.setAdapter(baiHatHotAdapter);
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {
                Log.d("BBB", t.getMessage());
            }
        });
    }
}
