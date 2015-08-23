package com.drivingevaluate.adapter;

import android.content.Context;

import com.drivingevaluate.R;
import com.drivingevaluate.adapter.component.AutoRVAdapter;
import com.drivingevaluate.adapter.component.ViewHolder;
import com.drivingevaluate.model.Image;
import com.drivingevaluate.util.BitmapUtil;

import java.util.List;

/**
 * Created by Yat3s on 8/23/15.
 * Email:hawkoyates@gmail.com
 */
public class ImageAdapter extends AutoRVAdapter {
    private Context context;
    public ImageAdapter(Context context, List<?> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_image;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Image image = (Image) list.get(position);
        holder.getImageView(R.id.image_iv).setImageBitmap(BitmapUtil.getSmallBitmap(image.getImgPath(),240,400));
    }
}
