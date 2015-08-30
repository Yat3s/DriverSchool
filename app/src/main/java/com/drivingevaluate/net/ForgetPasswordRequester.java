package com.drivingevaluate.net;

import com.drivingevaluate.app.App;
import com.drivingevaluate.app.ServerConf;

import java.util.Map;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.PATCH;
import retrofit.http.QueryMap;

/**
 * Created by Yat3s on 8/29/15.
 * Email:hawkoyates@gmail.com
 */
public class ForgetPasswordRequester {
    private Callback<String> callback;
    private Map<String, Object> param;

    public ForgetPasswordRequester(Callback<String> callback, Map<String, Object> param) {
        this.callback = callback;
        this.param = param;
    }

    private interface ForgetPasswordService {
        @PATCH("/api/user/password")
        void changePassword(@Body String body, @QueryMap Map<String, Object> param, Callback<String> callback);
    }

    public void request() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("token", App.DEFAULT_TOKEN);
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ServerConf.SERVER_IP)
                .setRequestInterceptor(requestInterceptor)
                .build();
        ForgetPasswordService forgetPasswordService = restAdapter.create(ForgetPasswordService.class);
        forgetPasswordService.changePassword("", param, callback);
    }
}
