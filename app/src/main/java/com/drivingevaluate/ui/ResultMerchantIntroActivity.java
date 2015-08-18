package com.drivingevaluate.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.ui.base.Yat3sActivity;

public class ResultMerchantIntroActivity extends Yat3sActivity{
    private TextView introTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackTitleBar();
        setContentView(R.layout.activity_result_dschoolinfo);
        setTitleBarTitle("驾校简介");

        initView();
        getData();
    }

    private void getData() {
        introTv.setText(getIntent().getExtras().getString("intro"));
    }

    private void initView() {
        introTv = (TextView) findViewById(R.id.intro_merchant_tv);
    }


}
