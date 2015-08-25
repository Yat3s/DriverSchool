package com.drivingevaluate.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.ui.base.Yat3sActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ResultMerchantIntroActivity extends Yat3sActivity {
    private TextView introTv;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_dschoolinfo);

        ButterKnife.bind(this);
        setToolbarWithNavigation(toolbar, "驾校简介");
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
