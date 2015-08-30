package com.drivingevaluate.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.balysv.materialripple.MaterialRippleLayout;
import com.drivingevaluate.R;
import com.drivingevaluate.model.User;
import com.drivingevaluate.net.LoginRequester;
import com.drivingevaluate.ui.FirstSettingInfoActivity;
import com.drivingevaluate.ui.ForgetPasswordActivity;
import com.drivingevaluate.ui.MainActivity;
import com.drivingevaluate.ui.base.Yat3sFragment;
import com.drivingevaluate.util.SharedPreferencesUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

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
public class LoginFragment extends Yat3sFragment {
    @Bind(R.id.account_et)
    MaterialEditText accountEt;
    @Bind(R.id.password_et)
    MaterialEditText passwordEt;
    @Bind(R.id.login_btn)
    Button loginBtn;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);
        MaterialRippleLayout.on(loginBtn)
                .rippleColor(getResources().getColor(R.color.md_blue_700))
                .rippleDuration(400)
                .rippleAlpha(0.6f)
                .rippleOverlay(true)
                .create();
        return rootView;
    }

    /**
     * login
     */
    @OnClick(R.id.login_btn)
    void Login() {
        String account = accountEt.getText().toString();
        String password = passwordEt.getText().toString();
        if (account.isEmpty()) {
            accountEt.setError("账号不能为空");
            return;
        }
        if (password.isEmpty()) {
            passwordEt.setError("密码不能为空");
            return;
        }

        Callback<User> callback = new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                SharedPreferencesUtils.put(getActivity(), "token", user.getAccessToken());
                SharedPreferencesUtils.put(getActivity(), "userId", user.getUserId());
                if (user.getUserName().equals("")) {
                    startActivity(FirstSettingInfoActivity.class);
                    getActivity().finish();
                } else {
                    startActivity(MainActivity.class);
                    getActivity().finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (error.getKind() == RetrofitError.Kind.NETWORK) {
                    showShortToast("网络连接异常");
                    return;
                }
                int code = error.getResponse().getStatus();
                switch (code) {
                    case 401:
                        passwordEt.setError("密码错误");
                        break;
                    case 404:
                        accountEt.setError("账号不存在");
                        break;
                    case 500:
                        showShortToast("服务器内部错误");
                        break;
                    default:
                        showShortToast("未知错误");
                        break;
                }
            }
        };
        Map<String, Object> param = new HashMap<>();
        param.put("account", account);
        param.put("password", password);
        LoginRequester loginRequester = new LoginRequester(callback, param);
        loginRequester.request();
    }

    @OnClick(R.id.forget_password_tv)
    void forgetPassword() {
        startActivity(ForgetPasswordActivity.class);
    }
}
