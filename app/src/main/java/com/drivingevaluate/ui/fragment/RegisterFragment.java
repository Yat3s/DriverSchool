package com.drivingevaluate.ui.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.balysv.materialripple.MaterialRippleLayout;
import com.drivingevaluate.R;
import com.drivingevaluate.net.RegisterRequester;
import com.drivingevaluate.net.component.RequestErrorHandler;
import com.drivingevaluate.ui.LoginActivity;
import com.drivingevaluate.ui.base.Yat3sFragment;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Yat3s on 8/28/15.
 * Email:hawkoyates@gmail.com
 */
public class RegisterFragment extends Yat3sFragment {
    @Bind(R.id.account_et)
    MaterialEditText accountEt;
    @Bind(R.id.password_et)
    MaterialEditText passwordEt;
    @Bind(R.id.verify_code_et)
    MaterialEditText verifyCodeEt;

    @Bind(R.id.verify_code_btn)
    Button verifyCodeBtn;
    @Bind(R.id.register_btn)
    Button registerBtn;
    private View rootView;
    private String account, password, verifyCode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, rootView);

        MaterialRippleLayout.on(registerBtn)
                .rippleColor(getResources().getColor(R.color.md_blue_700))
                .rippleDuration(400)
                .rippleAlpha(0.6f)
                .rippleOverlay(true)
                .create();

        return rootView;
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
                RequestErrorHandler requestErrorHandler = new RequestErrorHandler(getActivity());
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

    @OnClick(R.id.register_btn)
    void register() {
        account = accountEt.getText().toString();
        password = passwordEt.getText().toString();
        verifyCode = verifyCodeEt.getText().toString();
        if (account.length() != 11) {
            accountEt.setError("人类的手机号吗?");
        } else if (password.isEmpty()) {
            passwordEt.setError("设一个密码吧");
        } else if (verifyCode.isEmpty()) {
            verifyCodeEt.setError("请输入验证码");
        } else {
            Callback<String> callback = new Callback<String>() {
                @Override
                public void success(String s, Response response) {
                    showShortToast("注册成功");
                    accountEt.setText("");
                    passwordEt.setText("");
                    verifyCodeEt.setText("");
                    ((LoginActivity) getActivity()).viewPager.setCurrentItem(0);
                }

                @Override
                public void failure(RetrofitError error) {
                    RequestErrorHandler requestErrorHandler = new RequestErrorHandler(getActivity());
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
            param.put("pwd", password.trim());
            param.put("identifyCode", verifyCode.trim());
            RegisterRequester registerRequester = new RegisterRequester(callback, param);
            registerRequester.registerRequest();
        }
    }
}
