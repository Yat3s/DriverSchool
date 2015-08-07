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
    private List<Merchant> dSchoolList;
    private Context context;
    private ViewHolder vh;


    public MerchantAdapter(List<Merchant> dSchoolList, Context context) {
        this.dSchoolList = dSchoolList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dSchoolList.size();
    }

    @Override
    public Object getItem(int position) {
        return dSchoolList.get(position);
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
        vh.nameTv.setText(dSchoolList.get(position).getSname());
        vh.ourPriceTv.setText("¥" + (int) dSchoolList.get(position).getOurPrice());
        vh.marketPriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        vh.marketPriceTv.setText(("¥" + (int) dSchoolList.get(position).getMarketPrice()));
        vh.studentAmountTV.setText(dSchoolList.get(position).getSellCount()+ "名学生");
        vh.spendTimeTv.setText("约" + dSchoolList.get(position).getSpendTime() + "天拿证");
        vh.scoreTv.setText((dSchoolList.get(position).getSlevel()) + "分");
        if (dSchoolList.get(position).getSlevel()!=null)
        vh.scoreRb.setRating(Float.parseFloat(dSchoolList.get(position).getSlevel()));

        MyUtil.loadImg(vh.preImg,dSchoolList.get(position).getPhotoPath());
        float distance = dSchoolList.get(position).getDistance();
        if (distance > 1000) {
            vh.distanceTv.setText(new DecimalFormat("#.0").format(distance / 1000) + "km");
        } else {
            vh.distanceTv.setText(new DecimalFormat("#").format(distance) + "m");
        }
        if (dSchoolList.get(position).getDistance() == null || distance == 0) {
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
