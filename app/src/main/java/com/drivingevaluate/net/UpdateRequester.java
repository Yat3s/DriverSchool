package com.drivingevaluate.net;

import com.drivingevaluate.app.App;
import com.drivingevaluate.app.ServerConf;
import com.drivingevaluate.model.Version;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;

/**
 * Created by Yat3s on 8/29/15.
 * Email:hawkoyates@gmail.com
 */
public class UpdateRequester {
    private Callback<Version> callback;

    public UpdateRequester(Callback<Version> callback) {
        this.callback = callback;
    }

    private interface UpdateService {
        @GET("/api/version")
        void update(Callback<Version> callback);
    }

    public void request() {
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
        UpdateService updateService = restAdapter.create(UpdateService.class);
        updateService.update(callback);
    }
}
