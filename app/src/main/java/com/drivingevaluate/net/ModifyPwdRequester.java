package com.drivingevaluate.net;

import com.drivingevaluate.app.App;
import com.drivingevaluate.app.ServerConf;

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
public class ModifyPwdRequester {
    private Callback<String> callback;
    private Map<String,Object> param;

    public ModifyPwdRequester(Callback<String> callback, Map<String, Object> param) {
        this.callback = callback;
        this.param = param;
    }

    private interface ModifyPwdService {
        @FormUrlEncoded
        @POST("/api/user/modify_password.htm")
        void modifyPwd(@FieldMap Map<String, Object> param, Callback<String> callback);
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
        ModifyPwdService modifyPwdService = restAdapter.create(ModifyPwdService.class);
        modifyPwdService.modifyPwd(param, callback);
    }
}
