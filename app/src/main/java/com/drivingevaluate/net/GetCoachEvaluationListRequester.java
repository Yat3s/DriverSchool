package com.drivingevaluate.net;

import com.drivingevaluate.app.App;
import com.drivingevaluate.app.ServerConf;
import com.drivingevaluate.model.Evaluation;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by Yat3s on 8/20/15.
 * Email:hawkoyates@gmail.com
 */
public class GetCoachEvaluationListRequester {
    private Callback<List<Evaluation>> callback;
    private Map<String,Object> param;

    public GetCoachEvaluationListRequester(Callback<List<Evaluation>> callback, Map<String, Object> param) {
        this.callback = callback;
        this.param = param;
    }

    private interface GetCoachEvaluationListService {
        @GET("/api/goods/get_judges.htm")
        void getCoachEvaluationList(@QueryMap Map<String,Object> param,Callback<List<Evaluation>> callback);
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
        GetCoachEvaluationListService getCoachEvaluationListService = restAdapter.create(GetCoachEvaluationListService.class);
        getCoachEvaluationListService.getCoachEvaluationList(param,callback);
    }
}
