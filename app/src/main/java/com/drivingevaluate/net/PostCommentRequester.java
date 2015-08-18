package com.drivingevaluate.net;

import com.drivingevaluate.config.AppConf;
import com.drivingevaluate.config.ServerConf;

import java.util.Map;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by Yat3s on 8/15/15.
 * Email:hawkoyates@gmail.com
 */
public class PostCommentRequester {
    private Callback<String> callback;
    private Map<String,Object> param;

    public PostCommentRequester(Callback<String> callback, Map<String, Object> param) {
        this.callback = callback;
        this.param = param;
    }

    private interface PostCommentService {
        @GET("/api/comment/publish_comment.htm")
        void postComment(@QueryMap Map<String, Object> param, Callback<String> callback);
    }

    public void request(){
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("token", AppConf.TOKEN);
            }
        };
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ServerConf.SERVER_IP)
                .setRequestInterceptor(requestInterceptor)
                .build();
        PostCommentService postCommentService = restAdapter.create(PostCommentService.class);
        postCommentService.postComment(param, callback);
    }
}
