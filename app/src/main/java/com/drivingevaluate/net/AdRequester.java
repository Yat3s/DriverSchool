package com.drivingevaluate.net;

import com.drivingevaluate.app.App;
import com.drivingevaluate.app.ServerConf;
import com.drivingevaluate.model.Advertisement;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;

/**
 * Created by Yat3s on 8/27/15.
 * Email:hawkoyates@gmail.com
 */
public class AdRequester {
    private Callback<Advertisement> callback ;

    public AdRequester(Callback<Advertisement> callback) {
        this.callback = callback;
    }

    private interface AdService {
        @GET("/api/advs")
        void getAd(Callback<Advertisement> callback);
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
                .setRequestInterceptor(requestInterceptor)
                .build();
        AdService adService = restAdapter.create(AdService.class);
        adService.getAd(callback);
    }
}
