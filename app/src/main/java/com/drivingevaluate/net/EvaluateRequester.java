package com.drivingevaluate.net;

import com.drivingevaluate.config.AppConf;
import com.drivingevaluate.config.ServerConf;

import java.util.Map;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Yat3s on 8/22/15.
 * Email:hawkoyates@gmail.com
 */
public class EvaluateRequester {
    private Callback<String> callback;
    private Map<String,Object> param;

    public EvaluateRequester(Callback<String> callback, Map<String, Object> param) {
        this.callback = callback;
        this.param = param;
    }

    private interface EvaluateService{
        @FormUrlEncoded
        @POST("/api/goods/judge.htm")
        void evaluate(@FieldMap Map<String,Object> param,Callback<String> callback);
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
        EvaluateService evaluateService = restAdapter.create(EvaluateService.class);
        evaluateService.evaluate(param,callback);
    }
}
