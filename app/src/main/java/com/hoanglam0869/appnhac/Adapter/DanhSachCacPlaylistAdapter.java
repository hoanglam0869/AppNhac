package com.hoanglam0869.appnhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hoanglam0869.appnhac.Activity.DanhSachBaiHatActivity;
import com.hoanglam0869.appnhac.Model.PlayList;
import com.hoanglam0869.appnhac.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DanhSachCacPlaylistAdapter extends RecyclerView.Adapter<DanhSachCacPlaylistAdapter.ViewHolder> {
    Context context;
    ArrayList<PlayList> mangPlaylist;

    public DanhSachCacPlaylistAdapter(Context context, ArrayList<PlayList> mangPlaylist) {
        this.context = context;
        this.mangPlaylist = mangPlaylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_danh_sach_cac_playlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlayList playList = mangPlaylist.get(position);
        Picasso.get().load(playList.getHinhPlaylist()).into(holder.imgHinhNen);
        holder.txtTenPlaylist.setText(playList.getTen());
    }

    @Override
    public int getItemCount() {
        return mangPlaylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinhNen;
        TextView txtTenPlaylist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhNen = itemView.findViewById(R.id.imageViewDanhSachCacPlaylist);
            txtTenPlaylist = itemView.findViewById(R.id.textViewDanhSachCacPlaylist);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhSachBaiHatActivity.class);
                    intent.putExtra("itemplaylist", mangPlaylist.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
