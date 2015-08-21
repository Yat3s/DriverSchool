package com.drivingevaluate.adapter;

import android.content.Context;

import com.drivingevaluate.R;
import com.drivingevaluate.adapter.component.AutoRVAdapter;
import com.drivingevaluate.adapter.component.ViewHolder;
import com.drivingevaluate.model.Order;
import com.drivingevaluate.util.DateUtils;

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
        Order order = (Order) list.get(position);
        holder.setTextView(R.id.name_merchant_order_tv,order.getSname());
        holder.setTextView(R.id.id_order_tv,"订单号:"+order.getOrderNo());
        holder.setTextView(R.id.pre_pay_order_tv,"预付"+order.getPrePay()+"元");
        holder.setTextView(R.id.subject_order_tv,order.getGoodsTitle());
        holder.setTextView(R.id.date_order_tv, DateUtils.getDayDateStr(order.getCreatedTime()));
        if (order.getStatus() == 1){
            holder.setTextView(R.id.status_tv,"交易成功");
            holder.getTextView(R.id.status_tv).setTextColor(context.getResources().getColor(R.color.md_green_500));
        }
        else {
            holder.setTextView(R.id.status_tv,"未支付");
            holder.getTextView(R.id.status_tv).setTextColor(context.getResources().getColor(R.color.md_red_A100));
        }
    }
}
