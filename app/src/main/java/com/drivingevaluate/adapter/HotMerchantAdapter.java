package com.drivingevaluate.adapter;

import android.content.Context;

import com.drivingevaluate.R;
import com.drivingevaluate.adapter.component.AutoRVAdapter;
import com.drivingevaluate.adapter.component.ViewHolder;
import com.drivingevaluate.model.Merchant;
import com.drivingevaluate.util.MyUtil;

import java.util.List;

/**
 * Created by Yat3s on 8/27/15.
 * Email:hawkoyates@gmail.com
 */
public class HotMerchantAdapter extends AutoRVAdapter {

    public HotMerchantAdapter(Context context, List<?> list) {
        super(context, list);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_merchant_hot;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Merchant merchant = (Merchant) list.get(position);
        holder.setTextView(R.id.name_merchant_tv, merchant.getSname());
        holder.setTextView(R.id.member_merchant_tv, merchant.getSellCount() + "名学生");
        MyUtil.loadImg(holder.getImageView(R.id.merchant_img), merchant.getPhotoPath());
    }
}
