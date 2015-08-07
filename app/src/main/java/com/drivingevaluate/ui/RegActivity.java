package com.drivingevaluate.ui;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.drivingevaluate.R;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.api.JsonResolveUtils;
import com.drivingevaluate.config.Constants;

public class RegActivity extends Yat3sActivity implements OnClickListener{
    private EditText etPhone,etPswd,etRePswd,etVerityCode;
    private Button btnCommit,btnGetVerityCode;

    private String account,pswd,verityCode;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Constants.CODE_USER_ADD_RESPONSE:
                    if ((Integer)msg.obj == 1) {
                        showShortToast("注册成功");
                        finish();
                    }
                    else if ((Integer)msg.obj == 2) {
                        showShortToast("该号码已经注册");
                    }
                    else {
                        showShortToast("注册失败");
                    }
                    break;
                case Constants.CODE_IDENTIFY_CODE_REQUEST:
                    if ((Integer)msg.obj == 1) {
                        showShortToast("验证码已经发送");
                    }
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setBackTitleBar();
        setContentView(R.layout.activity_reg);

        initView();
        initEvent();

    }

    private void initEvent() {
        btnCommit.setOnClickListener(this);
        btnGetVerityCode.setOnClickListener(this);
    }

    private void initView() {
        setTitleBarTitle("填写注册信息");

        etPhone = (EditText) findViewById(R.id.et_phone);
        etPswd = (EditText) findViewById(R.id.et_password);
        etRePswd = (EditText) findViewById(R.id.et_rePassword);
        etVerityCode = (EditText) findViewById(R.id.et_verifyCode);

        btnCommit = (Button) findViewById(R.id.btn_commit);
        btnGetVerityCode = (Button) findViewById(R.id.btn_getVerifyCode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_getVerifyCode:
                getVerityCode();
                setCountDown();
                break;
            case R.id.btn_commit:
                commitReg();
                break;

            default:
                break;
        }
    }

    private void setCountDown() {
        CountDownTimer countDownTimer = new CountDownTimer(60000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                btnGetVerityCode.setText(millisUntilFinished/1000+"秒后重新获取");
                btnGetVerityCode.setBackgroundColor(getResources().getColor(R.color.line));
                btnGetVerityCode.setClickable(false);
            }

            @Override
            public void onFinish() {
                btnGetVerityCode.setText("重新获取");
                btnGetVerityCode.setClickable(true);
            }
        }.start();
    }

    private void getVerityCode() {
        if (etPhone.getText().toString().length()!=11) {
            return;
        }
        Map<String, String> param = new HashMap<String, String>();
        param.put("account", etPhone.getText().toString());
        JsonResolveUtils.getIdentifyCode(param, this.handler);
    }

    private void commitReg() {
        account = etPhone.getText().toString();
        pswd = etRePswd.getText().toString();
        verityCode = etVerityCode.getText().toString();
        if (account.length() != 11 ) {
            showShortToast("请输入正确的手机号码");
        }
        else if (etPswd.getText().toString().isEmpty()||etRePswd.getText().toString().isEmpty()) {
            showShortToast("请输入密码");
        }
        else if (!etPswd.getText().toString().equals(etRePswd.getText().toString())) {
            showShortToast("两次输入的密码不一样");
        }
        else if (etVerityCode.getText().toString().isEmpty()) {
            showShortToast("请输入验证码");
        }
        else {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("account", account.trim());
            param.put("pwd", pswd.trim());
            param.put("identifyCode", verityCode);
            JsonResolveUtils.userReg(param, handler);
        }
    }
}
