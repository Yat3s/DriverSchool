package com.drivingevaluate.net;

import com.drivingevaluate.app.App;
import com.drivingevaluate.app.ServerConf;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Yat3s on 8/22/15.
 * Email:hawkoyates@gmail.com
 */
public class PostPayResultToServerRequester {
    private String payResult;

    public PostPayResultToServerRequester(String payResult) {
        this.payResult = payResult;
    }

    private interface PayService{
        @FormUrlEncoded
        @POST("/api/payment/notify.htm")
        void payResulyToServer(@Field("result") String result);
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
        PayService payService = restAdapter.create(PayService.class);
        payService.payResulyToServer(payResult);
    }
}
