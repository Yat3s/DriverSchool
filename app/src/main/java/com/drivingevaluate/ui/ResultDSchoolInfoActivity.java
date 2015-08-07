package com.drivingevaluate.ui;

import com.drivingevaluate.R;
import com.drivingevaluate.view.BackTitleBar;

import android.app.Activity;
import android.os.Bundle;

public class ResultDSchoolInfoActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BackTitleBar titleBar = new BackTitleBar(this);
        setContentView(R.layout.activity_result_dschoolinfo);
        titleBar.setTitle("驾校简介");
    }
}
