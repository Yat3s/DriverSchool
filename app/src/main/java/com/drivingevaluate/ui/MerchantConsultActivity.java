package com.drivingevaluate.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.drivingevaluate.R;
import com.drivingevaluate.adapter.ConsultAdapter;
import com.drivingevaluate.model.Consult;
import com.drivingevaluate.net.ConsultRequester;
import com.drivingevaluate.net.component.RequestErrorHandler;
import com.drivingevaluate.ui.base.Yat3sActivity;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Yat3s on 8/20/15.
 * Email:hawkoyates@gmail.com
 */
public class MerchantConsultActivity extends Yat3sActivity {
    @Bind(R.id.consult_rv) RecyclerView consultRv;
    @Bind(R.id.toolbar) Toolbar toolbar;
    private List<Consult> mConsults = new ArrayList<>();
    private ConsultAdapter consultAdapter;
    private int merchantId;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_merchant_consult);
        ButterKnife.bind(this);
        setToolbarWithNavigation(toolbar,"在线咨询");
        initView();
        getData();
    }

    private void getData() {
        merchantId = getIntent().getExtras().getInt("merchantId");
        Callback<List<Consult>> callback = new Callback<List<Consult>>() {
            @Override
            public void success(List<Consult> consults, Response response) {
                mConsults.addAll(consults);
                consultAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                RequestErrorHandler requestErrorHandler = new RequestErrorHandler(MerchantConsultActivity.this);
                try {
                    requestErrorHandler.handError(error);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ConsultRequester consultRequester = new ConsultRequester(merchantId,callback);
        consultRequester.getCousultList();

    }

    private void initView() {
        consultRv.setLayoutManager(new LinearLayoutManager(this));
        consultAdapter = new ConsultAdapter(this,mConsults);
        consultRv.setAdapter(consultAdapter);
    }
}
