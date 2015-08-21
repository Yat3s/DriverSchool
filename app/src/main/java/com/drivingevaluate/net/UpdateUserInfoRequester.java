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
 * Created by Yat3s on 8/17/15.
 * Email:hawkoyates@gmail.com
 */
public class UpdateUserInfoRequester {
    private Callback<String> callback;
    private Map<String,Object> param;

    public UpdateUserInfoRequester(Callback<String> callback, Map<String, Object> param) {
        this.callback = callback;
        this.param = param;
    }

    private interface UpdateUserInfoService{
        @FormUrlEncoded
        @POST("/api/user/save_userinfo.htm")
        void updateUserInfo(@FieldMap Map<String,Object> param,Callback<String> callback);
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
        UpdateUserInfoService UpdateUserInfoService = restAdapter.create(UpdateUserInfoService.class);
        UpdateUserInfoService.updateUserInfo(param, callback);
    }
}
