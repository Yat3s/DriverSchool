package com.drivingevaluate.net;

import com.drivingevaluate.app.App;
import com.drivingevaluate.app.ServerConf;
import com.drivingevaluate.model.Comment;

import java.util.List;
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
public class GetCommentListRequester {
    private Callback<List<Comment>> callback ;
    private Map<String,Object> param;

    public GetCommentListRequester(Callback<List<Comment>> callback, Map<String, Object> param) {
        this.callback = callback;
        this.param = param;
    }

    private interface GetCommentListService{
        @GET("/api/comment/get_comments.htm")
        void getCommentList(@QueryMap Map<String,Object> param,Callback<List<Comment>> callback);
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
        GetCommentListService getCommentListService = restAdapter.create(GetCommentListService.class);
        getCommentListService.getCommentList(param, callback);
    }
}
