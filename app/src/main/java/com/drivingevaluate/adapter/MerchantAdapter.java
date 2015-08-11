package com.drivingevaluate.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.model.Merchant;
import com.drivingevaluate.util.MyUtil;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Yat3s on 15/8/4.
 */
public class MerchantAdapter extends BaseAdapter{
    private List<Merchant> merchants;
    private Context context;
    private ViewHolder vh;


    public MerchantAdapter(List<Merchant> merchants, Context context) {
        this.merchants = merchants;
        this.context = context;
    }

    @Override
    public int getCount() {
        return merchants.size();
    }

    @Override
    public Object getItem(int position) {
        return merchants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_merchant,null);
            vh = new ViewHolder();
            vh.nameTv = (TextView) convertView.findViewById(R.id.tv_name);
            vh.ourPriceTv = (TextView) convertView.findViewById(R.id.tv_ourPrice);
            vh.marketPriceTv = (TextView) convertView.findViewById(R.id.tv_marketPrice);
            vh.distanceTv = (TextView) convertView.findViewById(R.id.distance_tv);
            vh.scoreTv = (TextView) convertView.findViewById(R.id.tv_score);
            vh.studentAmountTV = (TextView) convertView.findViewById(R.id.tv_studentAmount);
            vh.spendTimeTv = (TextView) convertView.findViewById(R.id.costTime_tv);
            vh.scoreRb = (RatingBar) convertView.findViewById(R.id.rb_score);
            vh.preImg = (ImageView) convertView.findViewById(R.id.img_pre);
            convertView.setTag(vh);
        }
        else {
            vh = (ViewHolder)convertView.getTag();
        }
        vh.nameTv.setText(merchants.get(position).getSname());
        vh.ourPriceTv.setText("¥" + merchants.get(position).getMarketPrice());
        vh.marketPriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        vh.marketPriceTv.setText(("¥" + merchants.get(position).getMarketPrice()));
        vh.studentAmountTV.setText(merchants.get(position).getSellCount()+ "名学生");
        vh.spendTimeTv.setText("约" + merchants.get(position).getSpendTime() + "天拿证");

        //取3个item的平均分
        float avgGrade = (merchants.get(position).getItem1()+ merchants.get(position).getItem2()+ merchants.get(position).getItem3())/3.0f;
        vh.scoreRb.setRating(avgGrade);
        vh.scoreTv.setText(avgGrade + "分");

        MyUtil.loadImg(vh.preImg, merchants.get(position).getPhotoPath());

        //距离处理
        float distance = merchants.get(position).getDistance();
        if (distance > 1000) {
            vh.distanceTv.setText(new DecimalFormat("#.0").format(distance / 1000) + "km");
        } else {
            vh.distanceTv.setText(new DecimalFormat("#").format(distance) + "m");
        }
        if (merchants.get(position).getDistance() == null || distance == 0) {
            vh.distanceTv.setText("");
        }
        return convertView;
    }


    private class ViewHolder{
        TextView nameTv ;
        TextView ourPriceTv ;
        TextView marketPriceTv ;
        TextView distanceTv ;
        TextView scoreTv;
        TextView studentAmountTV;
        TextView spendTimeTv;

        RatingBar scoreRb;
        ImageView preImg;
    }
}
