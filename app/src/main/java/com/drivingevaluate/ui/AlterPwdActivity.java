package com.drivingevaluate.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.drivingevaluate.R;
import com.drivingevaluate.ui.base.Yat3sActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AlterPwdActivity extends Yat3sActivity{
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_alterpwd);

        ButterKnife.bind(this);
        setToolbarWithNavigation(toolbar, "修改密码");

        initView();
    }

    private void initView() {
    }
}
