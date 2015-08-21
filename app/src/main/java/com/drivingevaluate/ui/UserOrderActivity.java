package com.drivingevaluate.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.drivingevaluate.R;
import com.drivingevaluate.adapter.OrderAdapter;
import com.drivingevaluate.config.AppConf;
import com.drivingevaluate.model.Order;
import com.drivingevaluate.net.GetOrderListRequester;
import com.drivingevaluate.ui.base.Yat3sActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserOrderActivity extends Yat3sActivity{
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.order_rv) RecyclerView orderRv;
    private List<Order> mOrders = new ArrayList<>();
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_user_order);
        ButterKnife.bind(this);
        setToolbarWithNavigation(toolbar, "我的订单");
        initView();

        getData();
    }

    private void initView() {
        orderRv.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(this,mOrders);
        orderRv.setAdapter(orderAdapter);
    }

    protected void getData() {
        Callback<List<Order>> callback = new Callback<List<Order>>() {
            @Override
            public void success(List<Order> orders, Response response) {
                mOrders.addAll(orders);
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("yat3s","getOrders---->"+error.getMessage());
            }
        };
        GetOrderListRequester getOrderListRequester = new GetOrderListRequester(callback, AppConf.USER_ID);
        getOrderListRequester.request();
    }


}
