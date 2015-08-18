package com.drivingevaluate.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.drivingevaluate.R;
import com.drivingevaluate.net.RegisterRequester;
import com.drivingevaluate.net.component.RequestErrorHandler;
import com.drivingevaluate.ui.base.Yat3sActivity;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegActivity extends Yat3sActivity implements OnClickListener{
    private EditText etPhone,etPswd,etRePswd,etVerityCode;
    private Button btnCommit,btnGetVerityCode;

    private String account, pwd,verityCode;

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
            showShortToast("请输入正确的手机号码");
            return;
        }
        Callback<String> callback = new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                showShortToast("验证码已发送");
            }

            @Override
            public void failure(RetrofitError error) {

            }
        };
        Map<String, Object> param = new HashMap<>();
        param.put("account", etPhone.getText().toString());
        RegisterRequester registerRequester = new RegisterRequester(callback,param);
        registerRequester.getVerifyCodeRequest();
    }

    private void commitReg() {
        account = etPhone.getText().toString();
        pwd = etRePswd.getText().toString();
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
            Callback<String> callback = new Callback<String>() {
                @Override
                public void success(String s, Response response) {
                    showShortToast("注册成功");
                    finish();
                }

                @Override
                public void failure(RetrofitError error) {
                    RequestErrorHandler requestErrorHandler = new RequestErrorHandler(RegActivity.this);
                    try {
                        requestErrorHandler.handError(error);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            Map<String, Object> param = new HashMap<>();
            param.put("account", account.trim());
            param.put("pwd", pwd.trim());
            param.put("identifyCode", verityCode);
            RegisterRequester registerRequester = new RegisterRequester(callback,param);
            registerRequester.registerRequest();
        }
    }
}
