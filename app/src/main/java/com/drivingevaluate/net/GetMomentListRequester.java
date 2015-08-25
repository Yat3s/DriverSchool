package com.drivingevaluate.net;

import android.util.Log;

import com.drivingevaluate.app.App;
import com.drivingevaluate.app.ServerConf;
import com.drivingevaluate.config.UrlConfig;
import com.drivingevaluate.model.Moment;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by Yat3s on 15/8/4.
 */
public class GetMomentListRequester {
    private Callback<List<Moment>> cb;
    private Map<String,Object> param;

    public GetMomentListRequester(Callback<List<Moment>> callback, Map<String, Object> param) {
        this.cb = callback;
        this.param = param;
    }

    private interface GetMomentListService{
        @GET(UrlConfig.publishListAPI)
        void getMomentList(@QueryMap Map<String,Object> param,Callback<List<Moment>> callback);
    }

    public void request(){
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("token", App.DEFAULT_TOKEN);
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ServerConf.SERVER_IP)
                .setErrorHandler(new MyErrorHandler())
                .setRequestInterceptor(requestInterceptor)
                .build();
        GetMomentListService getMomentListService = restAdapter.create(GetMomentListService.class);
        getMomentListService.getMomentList(this.param,this.cb);
    }
    class MyErrorHandler implements ErrorHandler {
        @Override public Throwable handleError(RetrofitError cause) {
            Log.e("yat3s", "GetMomentListRequester---->" + cause.getMessage());
            return cause;
        }
    }
}
