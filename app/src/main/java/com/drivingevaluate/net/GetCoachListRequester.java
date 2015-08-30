package com.drivingevaluate.net;

import com.drivingevaluate.app.App;
import com.drivingevaluate.app.ServerConf;
import com.drivingevaluate.config.UrlConfig;
import com.drivingevaluate.model.Coach;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by Yat3s on 15/8/7.
 * Email:hawkoyates@gmail.com
 */
public class GetCoachListRequester {
    private Callback<List<Coach>> callback;
    private Map<String,Object> param;

    public GetCoachListRequester(Callback<List<Coach>> callback, Map<String, Object> param) {
        this.callback = callback;
        this.param = param;
    }

    private interface GetCoachListService{
        @GET(UrlConfig.coachsOfMerchantAPI)
        void getCoachList(@QueryMap Map<String,Object> param, Callback<List<Coach>> callback);
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
        GetCoachListService getCoachListService = restAdapter.create(GetCoachListService.class);
        getCoachListService.getCoachList(param,callback);
    }
}
