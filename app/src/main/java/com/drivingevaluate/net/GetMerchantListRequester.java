package com.drivingevaluate.net;

import android.util.Log;

import com.drivingevaluate.app.App;
import com.drivingevaluate.app.ServerConf;
import com.drivingevaluate.config.UrlConfig;
import com.drivingevaluate.model.Merchant;

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
 * Created by Yat3s on 15/7/31.
 */
public class GetMerchantListRequester {
    private Callback<List<Merchant>> cb;
    private Map<String,Object> param;

    public GetMerchantListRequester(Callback<List<Merchant>> cb, Map<String, Object> param) {
        this.cb = cb;
        this.param = param;
    }

    private  interface GetMerchantListService {
     @GET(UrlConfig.merchantListAPI)
     void getMerchantList(@QueryMap Map<String, Object> param, Callback<List<Merchant>> cb);
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

        GetMerchantListService getMerchantListService = restAdapter.create(GetMerchantListService.class);
        getMerchantListService.getMerchantList(this.param, this.cb);
    }

    class MyErrorHandler implements ErrorHandler {
        @Override public Throwable handleError(RetrofitError cause) {
            Log.e("yat3s", "GetMerchantListRequester---->" + cause.getMessage());
            return cause;
        }
    }
}
