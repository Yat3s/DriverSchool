package com.drivingevaluate.net;

import com.drivingevaluate.config.AppConf;
import com.drivingevaluate.config.ServerConf;
import com.drivingevaluate.config.UrlConfig;
import com.drivingevaluate.model.Order;

import org.json.JSONObject;

import java.util.Map;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by Yat3s on 8/11/15.
 * Email:hawkoyates@gmail.com
 */
public class CommitOrderRequester {
    private Callback<Order> callback;
    private Map<String,Object> param;

    public CommitOrderRequester(Callback<Order> callback, Map<String, Object> param) {
        this.callback = callback;
        this.param = param;
    }
    private interface CommitOrderService {
        @GET(UrlConfig.commitBuyOrderAPI)
        void commitOrder(@QueryMap Map<String, Object> param, Callback<Order> callback);
    }

    public void request(){
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("token", AppConf.TOKEN);
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ServerConf.SERVER_IP)
                .setRequestInterceptor(requestInterceptor)
                .build();
        CommitOrderService commitOrderService = restAdapter.create(CommitOrderService.class);
        commitOrderService.commitOrder(param, callback);
    }

}
