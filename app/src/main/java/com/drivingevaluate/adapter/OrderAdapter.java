package com.drivingevaluate.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.drivingevaluate.R;
import com.drivingevaluate.adapter.component.AutoRVAdapter;
import com.drivingevaluate.adapter.component.ViewHolder;
import com.drivingevaluate.model.Order;
import com.drivingevaluate.ui.EvaluateActivity;
import com.drivingevaluate.util.DateUtils;
import com.drivingevaluate.util.MyUtil;

import java.util.List;

/**
 * Created by Yat3s on 8/19/15.
 * Email:hawkoyates@gmail.com
 */
public class OrderAdapter extends AutoRVAdapter{
    private Context context;
    public OrderAdapter(Context context, List<?> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_user_order;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Order order = (Order) list.get(position);
        holder.setTextView(R.id.name_merchant_order_tv,order.getSname());
        holder.setTextView(R.id.id_order_tv,"订单号:"+order.getOrderNo());
        holder.setTextView(R.id.pre_pay_order_tv,"预付"+order.getPrePay()+"元");
        holder.setTextView(R.id.name_coach_order_tv, order.getSellName());
        holder.setTextView(R.id.subject_order_tv, "(" + order.getGoodsTitle() + ")");
        holder.setTextView(R.id.date_order_tv, DateUtils.getDayDateStr(order.getCreatedTime()));
        if (order.getStatus() == 0){
            holder.setTextView(R.id.status_tv,"待支付");
            holder.getTextView(R.id.status_tv).setTextColor(context.getResources().getColor(R.color.md_red_A100));
        }
        else if (order.getStatus() == 1){
            holder.setTextView(R.id.status_tv,"预付成功");
            holder.getTextView(R.id.status_tv).setTextColor(context.getResources().getColor(R.color.md_green_500));
        }
        else if (order.getStatus() == 2){
            holder.setTextView(R.id.status_tv, "驾校缴费成功");
            TextView tv = holder.getTextView(R.id.status_tv);
            TextPaint tp = tv.getPaint();
            tp.setFakeBoldText(true);
            tv.setTextColor(context.getResources().getColor(R.color.md_green_500));
        }

        MyUtil.loadImg(holder.getImageView(R.id.image_merchant_order_img),order.getPhotoPath());

        holder.getButton(R.id.evaluate_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order.getStatus() == 0 || order.getStatus() == 1){
                    Toast.makeText(context,"在驾校报名成功后才可以评价教练哦",Toast.LENGTH_LONG).show();
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("order",order);
                    Intent intent = new Intent(context, EvaluateActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            }
        });
    }
}
