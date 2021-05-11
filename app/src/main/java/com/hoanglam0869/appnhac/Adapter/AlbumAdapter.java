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
import com.hoanglam0869.appnhac.Model.Album;
import com.hoanglam0869.appnhac.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    Context context;
    ArrayList<Album> mangAlbum;

    public AlbumAdapter(Context context, ArrayList<Album> mangAlbum) {
        this.context = context;
        this.mangAlbum = mangAlbum;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_album, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = mangAlbum.get(position);
        holder.txtCaSiAlbum.setText(album.getTencasiAlbum());
        holder.txtTenAlbum.setText(album.getTenAlbum());
        Picasso.get().load(album.getHinhanhAlbum()).into(holder.imgHinhAlbum);
    }

    @Override
    public int getItemCount() {
        return mangAlbum.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinhAlbum;
        TextView txtTenAlbum, txtCaSiAlbum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhAlbum = itemView.findViewById(R.id.imageViewAlbum);
            txtTenAlbum = itemView.findViewById(R.id.textViewTenAlbum);
            txtCaSiAlbum = itemView.findViewById(R.id.textViewTenCaSiAlbum);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhSachBaiHatActivity.class);
                    intent.putExtra("album", mangAlbum.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
