package com.drivingevaluate.net;

import com.drivingevaluate.app.App;
import com.drivingevaluate.app.ServerConf;
import com.drivingevaluate.model.CustomerService;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;

/**
 * Created by Yat3s on 8/19/15.
 * Email:hawkoyates@gmail.com
 */
public class GetCustomerServiceRequester {
    private Callback<CustomerService> callback;

    public GetCustomerServiceRequester(Callback<CustomerService> callback) {
        this.callback = callback;
    }

    private interface GetCustomerServiceService{
        @GET("/api/common/contacts")
        void getCustomerService(Callback<CustomerService> callback);
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
        GetCustomerServiceService getCustomerServiceService = restAdapter.create(GetCustomerServiceService.class);
        getCustomerServiceService.getCustomerService(callback);
    }
}
