package com.drivingevaluate.net;

import com.drivingevaluate.config.AppConf;
import com.drivingevaluate.config.ServerConf;
import com.drivingevaluate.config.UrlConfig;
import com.drivingevaluate.model.Evaluation;

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
public class GetEvaluationListRequester {
    private Callback<List<Evaluation>> callback;
    private Map<String,Object> param;

    public GetEvaluationListRequester(Callback<List<Evaluation>> callback, Map<String, Object> param) {
        this.callback = callback;
        this.param = param;
    }

    private interface GetEvaluationService {
        @GET(UrlConfig.merchantCommentAPI)
        void getEvaluationList(@QueryMap Map<String,Object> param,Callback<List<Evaluation>> callback);
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
        GetEvaluationService getEvaluationService = restAdapter.create(GetEvaluationService.class);
        getEvaluationService.getEvaluationList(param,callback);
    }
}
