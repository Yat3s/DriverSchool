package com.drivingevaluate.net;

import com.drivingevaluate.config.AppConf;
import com.drivingevaluate.config.ServerConf;
import com.drivingevaluate.model.Consult;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Yat3s on 8/19/15.
 * Email:hawkoyates@gmail.com
 */
public class ConsultRequester {
    private Callback<List<Consult>> listCallback;
    private Callback<String> callback;
    private Map<String,Object> param;
    private int merchantId;
    private ConsultService consultService;

    public ConsultRequester(int merchantId, Callback<List<Consult>> listCallback) {
        this.merchantId = merchantId;
        this.listCallback = listCallback;
        createREST();
    }

    public ConsultRequester(Callback<String> callback, Map<String, Object> param) {
        this.callback = callback;
        this.param = param;
        createREST();
    }

    private interface ConsultService{
        @FormUrlEncoded
        @POST("/api/merchant/feedbacks.htm")
        void cousult(@FieldMap Map<String,Object> param,Callback<String> callback);

        @GET("/api/merchant/feedbacks.htm")
        void getCousultList(@Query("merchantId") int merchantId,Callback<List<Consult>> listCallback);
    }
    public void createREST(){
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
        consultService = restAdapter.create(ConsultService.class);
    }

    public void cousult(){
        consultService.cousult(param,callback);
    }
    public void getCousultList(){
        consultService.getCousultList(merchantId,listCallback);
    }
}
