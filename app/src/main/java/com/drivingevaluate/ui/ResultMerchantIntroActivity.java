package com.drivingevaluate.ui;

import com.drivingevaluate.R;
import com.drivingevaluate.view.BackTitleBar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultMerchantIntroActivity extends Activity{
    private TextView introTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BackTitleBar titleBar = new BackTitleBar(this);
        setContentView(R.layout.activity_result_dschoolinfo);
        titleBar.setTitle("驾校简介");

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
