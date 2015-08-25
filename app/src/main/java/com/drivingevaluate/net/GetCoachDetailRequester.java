package com.drivingevaluate.net;

import android.util.Log;

import com.drivingevaluate.app.App;
import com.drivingevaluate.app.ServerConf;
import com.drivingevaluate.config.UrlConfig;
import com.drivingevaluate.model.Coach;

import java.util.Map;

import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by Yat3s on 8/11/15.
 * Email:hawkoyates@gmail.com
 */
public class GetCoachDetailRequester {
    private Callback<Coach> callback;
    private Map<String,Object> param;

    public GetCoachDetailRequester(Callback<Coach> callback, Map<String, Object> param) {
        this.callback = callback;
        this.param = param;
    }

    private interface GetCoachDetailService{
        @GET(UrlConfig.getGoodsDetailsAPI)
        void getCoachDetail(@QueryMap Map<String,Object> param, Callback<Coach> callback);
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
                .setErrorHandler(new MyErrorHandler())
                .build();
        GetCoachDetailService getCoachDetailService = restAdapter.create(GetCoachDetailService.class);
        getCoachDetailService.getCoachDetail(param, callback);
    }
    class MyErrorHandler implements ErrorHandler {
        @Override public Throwable handleError(RetrofitError cause) {
            Log.e("yat3s", "GetCoachDetailRequester---->" + cause.getMessage());
            return cause;
        }
    }
}
