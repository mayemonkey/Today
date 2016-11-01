package com.maye.today.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.maye.today.domain.ImageItem;
import com.maye.today.global.TodayApplication;
import com.maye.today.today.R;
import com.maye.today.ui.activity.AlbumActivity;

import java.io.File;
import java.util.List;


/**
 * Album中GridView适配器
 */
public class AlbumAdapter extends BaseAdapter {

    private AlbumActivity activity;
    private List<ImageItem> list;

    public AlbumAdapter(AlbumActivity activity, List<ImageItem> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        if (convertView == null) {
            convertView = View.inflate(TodayApplication.getContext(), R.layout.layout_gv_album, null);
        }

        ImageItem imageItem = list.get(position);
        AlbumViewHolder holder = AlbumViewHolder.getInstance(convertView);

        final String path = imageItem.getImagePaht();

        holder.iv_gv_album_sign.setVisibility(View.GONE);
        Glide.with(TodayApplication.getContext()).
                load(new File(path)).
                centerCrop().crossFade().into(holder.iv_gv_album_bitmap);

        holder.iv_gv_album_bitmap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                activity.backResult(path);
            }
        });

        return convertView;
    }

    private static class AlbumViewHolder {

        ImageView iv_gv_album_bitmap;
        ImageView iv_gv_album_sign;

        AlbumViewHolder(View convertView) {
            iv_gv_album_bitmap = (ImageView) convertView.findViewById(R.id.iv_gv_album_bitmap);
            iv_gv_album_sign = (ImageView) convertView.findViewById(R.id.iv_gv_album_sign);
        }

        static AlbumViewHolder getInstance(View convertView) {
            AlbumViewHolder holder = (AlbumViewHolder) convertView.getTag();
            if (holder == null) {
                holder = new AlbumViewHolder(convertView);
                convertView.setTag(holder);
            }
            return holder;
        }
    }

}
