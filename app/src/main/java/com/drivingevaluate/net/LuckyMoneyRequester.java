package com.drivingevaluate.net;

import com.drivingevaluate.app.App;
import com.drivingevaluate.app.ServerConf;
import com.drivingevaluate.model.LuckyMoney;

import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Yat3s on 8/19/15.
 * Email:hawkoyates@gmail.com
 */
public class LuckyMoneyRequester {
    private Callback<List<LuckyMoney>> listCallback;
    private Callback<LuckyMoney> callback;
    private int userId;
    private LuckyMoneyService luckyMoneyService;

    public LuckyMoneyRequester(Callback<LuckyMoney> callback, int userId) {
        this.callback = callback;
        this.userId = userId;
        createREST();
    }

    public LuckyMoneyRequester(Callback<List<LuckyMoney>> listCallback) {
        this.listCallback = listCallback;
        createREST();
    }

    private interface LuckyMoneyService{
        @POST("/api/account/hongbaos")
        void grabLuckyMoney(@Body String body,@Query("userId") int userId,Callback<LuckyMoney> callback);

        @GET("/api/account/hongbaos/user")
        void getUserLuckyMoney(@Query("userId") int userId,Callback<LuckyMoney> callback);

        @GET("/api/account/hongbaos")
        void getLuckyMoneyList(Callback<List<LuckyMoney>> callback);
    }

    public void createREST(){
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
        luckyMoneyService = restAdapter.create(LuckyMoneyService.class);
    }

    public void grabLuckyMoney(){
        luckyMoneyService.grabLuckyMoney("",userId,callback);
    }
    public void getUserLuckyMoney(){
        luckyMoneyService.getUserLuckyMoney(userId,callback);
    }
    public void getLuckyMoneyList(){
        luckyMoneyService.getLuckyMoneyList(listCallback);
    }
}

