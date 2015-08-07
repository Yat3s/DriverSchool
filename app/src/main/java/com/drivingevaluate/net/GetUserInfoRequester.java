package com.drivingevaluate.net;

import com.drivingevaluate.config.AppConf;
import com.drivingevaluate.config.ServerConf;
import com.drivingevaluate.config.UrlConfig;
import com.drivingevaluate.model.User;

import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Yat3s on 15/8/6.
 * Email:hawkoyates@gmail.com
 */
public class GetUserInfoRequester {
    private Callback<User> callback;
    private int userId;

    public GetUserInfoRequester(Callback<User> callback, int userId) {
        this.callback = callback;
        this.userId = userId;
    }

    private interface GetUserInfoService{
        @GET(UrlConfig.userInfoAPI)
        void getUserInfo(@Query("userId") int userId,Callback<User> callback);
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
                .setRequestInterceptor(requestInterceptor)
                .setErrorHandler(new MyErrorHandler())
                .build();
        GetUserInfoService getUserInfoService = restAdapter.create(GetUserInfoService.class);
        getUserInfoService.getUserInfo(userId,callback);
    }
    class MyErrorHandler implements ErrorHandler {
        @Override public Throwable handleError(RetrofitError cause) {
            return cause;
        }
    }
}
