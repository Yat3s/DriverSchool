package com.drivingevaluate.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.drivingevaluate.R;
import com.drivingevaluate.config.AppConf;
import com.drivingevaluate.net.ConsultRequester;
import com.drivingevaluate.ui.base.Yat3sActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Yat3s on 15/7/31.
 */
public class ConsultActivity extends Yat3sActivity {

    @Bind(R.id.consult_et) EditText consultEt;
    @Bind(R.id.consult_btn) Button consultBtn;
    @Bind(R.id.toolbar) Toolbar toolbar;
    private int merchantId;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_consult);
        ButterKnife.bind(this);
        setToolbarWithNavigation(toolbar, "在线咨询");

        initEvent();
    }

    private void initEvent() {
        consultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consult();

            }
        });
    }

    private void consult() {
        merchantId = getIntent().getExtras().getInt("merchantId");
        Callback<String> callback = new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                showShortToast("提交成功，在线客户会尽快回复并且联系您");
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Yat3s","consult---->"+error.getMessage());
            }
        };
        Map<String,Object> param = new HashMap<>();
        param.put("merchantId",merchantId);
        param.put("userId", AppConf.USER_ID);
        param.put("desc", consultEt.getText().toString());
        ConsultRequester consultRequester = new ConsultRequester(callback,param);
        consultRequester.cousult();
    }
}
