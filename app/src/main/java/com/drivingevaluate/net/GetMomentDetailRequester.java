package com.drivingevaluate.net;

import com.drivingevaluate.app.App;
import com.drivingevaluate.app.ServerConf;
import com.drivingevaluate.model.Moment;

import java.util.Map;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by Yat3s on 8/14/15.
 * Email:hawkoyates@gmail.com
 */
public class GetMomentDetailRequester {
    private Callback<Moment> callback;
    private Map<String,Object> param;

    public GetMomentDetailRequester(Callback<Moment> callback, Map<String, Object> param) {
        this.callback = callback;
        this.param = param;
    }

    private interface GetMomentDetailService {
        @GET("/api/publish/get_details.htm")
        void getMomentDetail(@QueryMap Map<String, Object> param, Callback<Moment> callback);
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
        GetMomentDetailService getMomentDetailService = restAdapter.create(GetMomentDetailService.class);
        getMomentDetailService.getMomentDetail(this.param, this.callback);
    }

}
