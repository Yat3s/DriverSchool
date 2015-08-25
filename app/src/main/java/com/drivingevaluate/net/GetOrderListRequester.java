package com.drivingevaluate.net;

import com.drivingevaluate.app.App;
import com.drivingevaluate.app.ServerConf;
import com.drivingevaluate.model.Order;

import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Yat3s on 8/19/15.
 * Email:hawkoyates@gmail.com
 */
public class GetOrderListRequester {
    private Callback<List<Order>> listCallback;
    private int userId;

    public GetOrderListRequester(Callback<List<Order>> listCallback, int userId) {
        this.listCallback = listCallback;
        this.userId = userId;
    }

    private interface GetOrderListService{
        @GET("/api/order/get_user_orders.htm")
        void getOrderList(@Query("userId") int userId,Callback<List<Order>> listCallback);
    }
    public void request(){
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("token", App.getToken());
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ServerConf.SERVER_IP)
                .setRequestInterceptor(requestInterceptor)
                .build();
        GetOrderListService getOrderListService = restAdapter.create(GetOrderListService.class);
        getOrderListService.getOrderList(userId,listCallback);
    }
}
