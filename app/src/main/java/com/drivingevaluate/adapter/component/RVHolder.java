package com.drivingevaluate.adapter.component;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Yat3s on 8/13/15.
 * Email:hawkoyates@gmail.com
 */
public class RVHolder  extends RecyclerView.ViewHolder {


    private ViewHolder viewHolder;

    public RVHolder(View itemView) {
        super(itemView);
        viewHolder=ViewHolder.getViewHolder(itemView);

    }


    public ViewHolder getViewHolder() {
        return viewHolder;
    }

}
