package com.hoanglam0869.appnhac.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hoanglam0869.appnhac.Model.PlayList;
import com.hoanglam0869.appnhac.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlayListAdapter extends ArrayAdapter<PlayList> {
    public PlayListAdapter(@NonNull Context context, int resource, @NonNull List<PlayList> objects) {
        super(context, resource, objects);
    }

    class ViewHolder {
        TextView txtTenPlayList;
        ImageView imgBackground, imgPlayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.dong_playlist, null);
            viewHolder = new ViewHolder();
            viewHolder.txtTenPlayList = convertView.findViewById(R.id.textViewTenPlayList);
            viewHolder.imgPlayList = convertView.findViewById(R.id.imageViewPlayList);
            viewHolder.imgBackground = convertView.findViewById(R.id.imageViewBackgroundPlayList);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PlayList playList = getItem(position);
        Picasso.get().load(playList.getHinhPlaylist()).into(viewHolder.imgBackground);
        Picasso.get().load(playList.getIcon()).into(viewHolder.imgPlayList);
        viewHolder.txtTenPlayList.setText(playList.getTen());
        return convertView;
    }
}
