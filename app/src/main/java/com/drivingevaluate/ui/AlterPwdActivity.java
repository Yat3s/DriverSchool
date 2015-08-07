package com.drivingevaluate.ui;

import android.os.Bundle;

import com.drivingevaluate.R;
import com.drivingevaluate.ui.base.Yat3sActivity;

public class AlterPwdActivity extends Yat3sActivity{
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setBackTitleBar();
        setContentView(R.layout.activity_alterpwd);

        initView();
    }

    private void initView() {
        setTitleBarTitle("修改密码");
    }
}
