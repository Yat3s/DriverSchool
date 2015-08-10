package com.drivingevaluate.net;

import com.drivingevaluate.config.AppConf;
import com.drivingevaluate.config.ServerConf;
import com.drivingevaluate.config.UrlConfig;
import com.drivingevaluate.model.CoachComment;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by Yat3s on 8/10/15.
 * Email:hawkoyates@gmail.com
 */
public class GetCoachCommentListRequester {
    private Callback<List<CoachComment>> callback;
    private Map<String,Object> param;

    public GetCoachCommentListRequester(Callback<List<CoachComment>> callback, Map<String, Object> param) {
        this.callback = callback;
        this.param = param;
    }

    private interface GetCoachCommentListService{
        @GET(UrlConfig.merchantCommentAPI)
        void getCoachCommentList(@QueryMap Map<String,Object> param,Callback<List<CoachComment>> callback);
    }

    public void request(){
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("token", AppConf.DEFAULT_TOKEN);
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ServerConf.SERVER_IP)
                .setRequestInterceptor(requestInterceptor)
                .build();
        GetCoachCommentListService getCoachCommentListService = restAdapter.create(GetCoachCommentListService.class);
        getCoachCommentListService.getCoachCommentList(param,callback);
    }
}
