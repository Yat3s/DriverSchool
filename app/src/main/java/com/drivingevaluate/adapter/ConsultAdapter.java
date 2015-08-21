package com.drivingevaluate.adapter;

import android.content.Context;

import com.drivingevaluate.R;
import com.drivingevaluate.adapter.component.AutoRVAdapter;
import com.drivingevaluate.adapter.component.ViewHolder;
import com.drivingevaluate.model.Consult;

import java.util.List;

/**
 * Created by Yat3s on 8/20/15.
 * Email:hawkoyates@gmail.com
 */
public class ConsultAdapter extends AutoRVAdapter {
    public ConsultAdapter(Context context, List<?> list) {
        super(context, list);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_consult;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Consult consult = (Consult) list.get(position);
        holder.setTextView(R.id.name_tv,consult.getUser().getUserName()+":");
        holder.setTextView(R.id.content_consult_tv,consult.getDesc());
    }
}
