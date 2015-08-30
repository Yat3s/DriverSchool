package com.drivingevaluate.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.drivingevaluate.R;
import com.drivingevaluate.net.ForgetPasswordRequester;
import com.drivingevaluate.net.RegisterRequester;
import com.drivingevaluate.net.component.RequestErrorHandler;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by Yat3s on 8/29/15.
 * Email:hawkoyates@gmail.com
 */
public class ForgetPasswordActivity extends Yat3sActivity {
    @Bind(R.id.account_et)
    MaterialEditText accountEt;
    @Bind(R.id.password_et)
    MaterialEditText passwordEt;
    @Bind(R.id.verify_code_et)
    MaterialEditText verifyCodeEt;
    @Bind(R.id.verify_code_btn)
    Button verifyCodeBtn;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        setToolbarWithNavigation(toolbar, "重设密码");
    }

    @OnClick(R.id.verify_code_btn)
    void getVerifyCode() {
        if (accountEt.getText().toString().length() != 11) {
            accountEt.setError("请输入正确的手机号码");
            return;
        }
        Callback<String> callback = new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                showShortToast("验证码已发送");
            }

            @Override
            public void failure(RetrofitError error) {
                RequestErrorHandler requestErrorHandler = new RequestErrorHandler(ForgetPasswordActivity.this);
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
        param.put("account", accountEt.getText().toString());
        RegisterRequester registerRequester = new RegisterRequester(callback, param);
        registerRequester.getVerifyCodeRequest();

        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                verifyCodeBtn.setText(millisUntilFinished / 1000 + "秒后重新获取");
                verifyCodeBtn.setEnabled(false);
            }

            @Override
            public void onFinish() {
                verifyCodeBtn.setText("重新获取");
                verifyCodeBtn.setEnabled(true);
            }
        }.start();
    }

    @OnClick(R.id.commit_btn)
    void commit() {
        Callback<String> callback = new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                showShortToast("设置成功");
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                if (error.getKind() == RetrofitError.Kind.NETWORK) {
                    showShortToast("网络连接异常");
                    return;
                }
                int code = error.getResponse().getStatus();
                switch (code) {
                    case 404:
                        accountEt.setError("账号不存在");
                        break;
                    case 500:
                        showShortToast("服务器内部错误");
                        break;
                    case 422:
                        String responseString = new String(((TypedByteArray) error.getResponse().getBody()).getBytes());
                        JSONObject responseObj = null;
                        try {
                            responseObj = new JSONObject(responseString);
                            String msg = responseObj.getString("message");
                            passwordEt.setError(msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        showShortToast("未知错误");
                        break;
                }
            }
        };
        Map<String, Object> param = new HashMap<>();
        param.put("account", accountEt.getText().toString().trim());
        param.put("pwd", passwordEt.getText().toString().trim());
        param.put("identifyCode", verifyCodeEt.getText().toString().trim());

        ForgetPasswordRequester forgetPasswordRequester = new ForgetPasswordRequester(callback, param);
        forgetPasswordRequester.request();
    }
}
