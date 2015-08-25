package com.drivingevaluate.net;

import com.drivingevaluate.app.App;
import com.drivingevaluate.app.ServerConf;

import java.util.Map;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Yat3s on 8/15/15.
 * Email:hawkoyates@gmail.com
 */
public class PostMomentRequester {
    private Callback<String> callback;
    private Map<String,Object> param;

    public PostMomentRequester(Callback<String> callback, Map<String, Object> param) {
        this.callback = callback;
        this.param = param;
    }

    private interface PostMomentService{
        @FormUrlEncoded
        @POST("/api/publish/commit_publish.htm")
        void postMoment(@FieldMap Map<String,Object> param,Callback<String> callback);
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
        PostMomentService postMomentService = restAdapter.create(PostMomentService.class);
        postMomentService.postMoment(param,callback);
    }
}
