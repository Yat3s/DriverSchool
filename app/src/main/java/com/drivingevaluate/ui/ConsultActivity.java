package com.drivingevaluate.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.drivingevaluate.R;
import com.drivingevaluate.ui.base.Yat3sActivity;

/**
 * Created by Yat3s on 15/7/31.
 */
public class ConsultActivity extends Yat3sActivity {

    private EditText consultEt;
    private Button consultBtn;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setBackTitleBar();
        setContentView(R.layout.activity_consult);

        initView();
        initEvent();
    }

    private void initEvent() {
        consultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                showShortToast("提交成功，在线客户会尽快回复并且联系您");
            }
        });
    }

    private void initView() {
        setTitleBarTitle("在线咨询");

        consultEt = getView(R.id.consult_et);
        consultBtn = getView(R.id.consult_btn);
    }
}
