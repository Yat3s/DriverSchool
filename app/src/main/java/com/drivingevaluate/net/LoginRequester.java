package com.drivingevaluate.net;


import com.drivingevaluate.config.AppConf;
import com.drivingevaluate.config.ServerConf;
import com.drivingevaluate.config.UrlConfig;
import com.drivingevaluate.model.User;

import java.util.Map;

import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.QueryMap;

/**
 * Created by Yat3s on 15/8/6.
 * Email:hawkoyates@gmail.com
 */
public class LoginRequester {
    private Callback<User> callback ;
    private Map<String,Object> param;

    public LoginRequester(Callback<User> callback, Map<String, Object> param) {
        this.callback = callback;
        this.param = param;
    }

    private interface LoginService{
        @POST(UrlConfig.userLoginAPI)
        void login(@Body String body,@QueryMap Map<String, Object> param,Callback<User> callback);
    }

    public void request(){
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("token", AppConf.DEFAULT_TOKEN);
            }
        };
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ServerConf.SERVER_IP)
                .setErrorHandler(new MyErrorHandler())
                .setRequestInterceptor(requestInterceptor)
                .build();

        LoginService loginService = restAdapter.create(LoginService.class);
        loginService.login("",param,callback);
    }

    class MyErrorHandler implements ErrorHandler {
        @Override public Throwable handleError(RetrofitError cause) {
            Response r = cause.getResponse();
            if (r != null && r.getStatus() == 404) {

            }
            if (r != null && r.getStatus() == 422) {

            }
            return cause;
        }
    }
}
