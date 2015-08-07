package com.drivingevaluate.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.drivingevaluate.R;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.api.JsonResolveUtils;
import com.drivingevaluate.config.Config;
import com.drivingevaluate.config.Constants;
import com.drivingevaluate.model.UserBuyOrder;
import com.drivingevaluate.util.AppMethod;
import com.drivingevaluate.util.DateUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyOrderActivity extends Yat3sActivity{
    private ListView lvOrders;
    private List<UserBuyOrder> userBuyOrders ;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constants.CODE_GET_USER_ORDERS:
                    userBuyOrders = JSON.parseArray(msg.obj.toString(),UserBuyOrder.class);
                    setData();
                    break;

                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setBackTitleBar();
        setContentView(R.layout.activity_user_order);
        initView();
        getData();
    }

    private void initView() {
        setTitleBarTitle("我的订单");
        lvOrders = (ListView) findViewById(R.id.lv_order);
    }

    protected void getData() {
        userBuyOrders = new ArrayList<UserBuyOrder>();

        Map<String, Object> param = new HashMap<String, Object>();
        param.put(Constants.USER_ID, AppMethod.getCurUserId());
        JsonResolveUtils.getUserOrders(param, handler);
    }
    private void setData() {
        lvOrders.setAdapter(new OrderAdapter());
    }
    class OrderAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return userBuyOrders.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_lv_order, null);
            }
            TextView tvDschool = (TextView) convertView.findViewById(R.id.tv_dschoolName);
            TextView tvClass = (TextView) convertView.findViewById(R.id.tv_class);
            TextView tvDate = (TextView) convertView.findViewById(R.id.tv_date);
            TextView tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            ImageView img = (ImageView) convertView.findViewById(R.id.img);

            ImageLoader.getInstance().displayImage(userBuyOrders.get(position).getPhotoPath(), img,Config.ImageOptions);

            tvDschool.setText(userBuyOrders.get(position).getSname());
            tvClass.setText(userBuyOrders.get(position).getGoodsTitle());
            tvPrice.setText("¥"+userBuyOrders.get(position).getTotalFee());
            tvDate.setText(DateUtils.getDateStr(userBuyOrders.get(position).getCreateTime()));

            Button feedbackButton = (Button) convertView.findViewById(R.id.btn_feedback);
            feedbackButton.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    showLongToast("报名未满30天,您暂时不能评价");
                }
            });
            return convertView;
        }
    }
}
