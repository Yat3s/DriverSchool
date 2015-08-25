package com.drivingevaluate.net;

import com.drivingevaluate.app.App;
import com.drivingevaluate.app.ServerConf;

import java.util.Map;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.QueryMap;

/**
 * Created by Yat3s on 8/16/15.
 * Email:hawkoyates@gmail.com
 */
public class RegisterRequester {
    private Callback<String> callback;
    private Map<String,Object> param;
    private RegisterService registerService;

    public RegisterRequester(Callback<String> callback, Map<String, Object> param) {
        this.callback = callback;
        this.param = param;
        initRest();
    }

    private interface RegisterService{
        @GET("/api/user/get_identify_code.htm")
        void getVerifyCode(@QueryMap Map<String,Object> param,Callback<String> callback);

        @POST("/api/user/user_reg.htm")
        void register(@Body String body,@QueryMap Map<String,Object> param,Callback<String> callback);
    }

    private void initRest(){
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
        registerService = restAdapter.create(RegisterService.class);
    }

    public void getVerifyCodeRequest(){
        registerService.getVerifyCode(param,callback);
    }

    public void registerRequest(){
        registerService.register("",param, callback);
    }
}
