package com.drivingevaluate.ui;

import android.os.Bundle;
import android.view.Window;

import com.drivingevaluate.R;
import com.drivingevaluate.ui.base.Yat3sActivity;

public class MomentActivity extends Yat3sActivity{
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_moment);
    }
}
