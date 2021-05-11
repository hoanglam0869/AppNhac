package com.hoanglam0869.appnhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hoanglam0869.appnhac.Activity.DanhSachTheLoaiTheoChuDeActivity;
import com.hoanglam0869.appnhac.Model.ChuDe;
import com.hoanglam0869.appnhac.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DanhSachTatCaChuDeAdapter extends RecyclerView.Adapter<DanhSachTatCaChuDeAdapter.ViewHolder> {
    Context context;
    ArrayList<ChuDe> mangChuDe;

    public DanhSachTatCaChuDeAdapter(Context context, ArrayList<ChuDe> mangChuDe) {
        this.context = context;
        this.mangChuDe = mangChuDe;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_cac_chu_de, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChuDe chuDe = mangChuDe.get(position);
        Picasso.get().load(chuDe.getHinhChuDe()).into(holder.imgChuDe);
    }

    @Override
    public int getItemCount() {
        return mangChuDe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgChuDe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgChuDe = itemView.findViewById(R.id.imageViewDongCacChuDe);
            imgChuDe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhSachTheLoaiTheoChuDeActivity.class);
                    intent.putExtra("chude", mangChuDe.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
