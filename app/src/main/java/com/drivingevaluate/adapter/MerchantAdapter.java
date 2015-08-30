package com.drivingevaluate.adapter;

import android.content.Context;

import com.drivingevaluate.R;
import com.drivingevaluate.adapter.component.AutoRVAdapter;
import com.drivingevaluate.adapter.component.ViewHolder;
import com.drivingevaluate.model.Merchant;
import com.drivingevaluate.util.MyUtil;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Yat3s on 15/8/4.
 */
public class MerchantAdapter extends AutoRVAdapter {
    private Context context;

    public MerchantAdapter(Context context, List<?> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_lv_merchant;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Merchant merchant = (Merchant) list.get(position);
        holder.setTextView(R.id.tv_name, merchant.getSname());
        holder.setTextView(R.id.market_price_tv, ("¥" + (int) merchant.getMarketPrice() + "元"));
        holder.setTextView(R.id.tv_score, new DecimalFormat("#.0").format(merchant.getAvgGrade()) + "分");
        holder.setTextView(R.id.tv_studentAmount, merchant.getSellCount() + "名学生");
        holder.setTextView(R.id.costTime_tv, "约" + merchant.getSpendTime() + "天拿证");
        holder.getRatingBar(R.id.rb_score).setRating(merchant.getAvgGrade());
        MyUtil.loadImg(holder.getImageView(R.id.img_pre), merchant.getPhotoPath());

        //距离处理
        if (merchant.getDistance() != null && merchant.getDistance() != 0.0) {
            float distance = merchant.getDistance();
            if (distance > 1000) {
                holder.setTextView(R.id.distance_tv, new DecimalFormat("#.0").format(distance / 1000) + "km");

            } else {
                holder.setTextView(R.id.distance_tv, new DecimalFormat("#").format(distance) + "m");
            }
        } else {
            holder.setTextView(R.id.distance_tv, "");
        }

        if (merchant.getZhekou() == 1) {
            holder.setTextView(R.id.discount_tv, "活动");
            holder.getTextView(R.id.discount_tv).setPadding(2, 2, 2, 2);
        } else {
            holder.setTextView(R.id.discount_tv, "");
            holder.getTextView(R.id.discount_tv).setPadding(0, 0, 0, 0);
        }

    }

}
