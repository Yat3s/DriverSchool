package com.drivingevaluate.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.drivingevaluate.R;
import com.drivingevaluate.model.User;
import com.drivingevaluate.net.LoginRequester;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.util.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends Yat3sActivity implements OnClickListener{
    private Button btnLogin,btnReg;
    private ImageButton backBtn;

    private EditText etAccount,etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReg = (Button) findViewById(R.id.btn_reg);
        backBtn = (ImageButton) findViewById(R.id.back_btn);

        etAccount = (EditText) findViewById(R.id.et_account);
        etPassword = (EditText) findViewById(R.id.et_password);

        btnLogin.setOnClickListener(this);
        btnReg.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                Login();
                break;
            case R.id.btn_reg:
                Register();
                break;
            case R.id.back_btn:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * register
     */
    private void Register() {
        Intent intent = new Intent(LoginActivity.this,RegActivity.class);
        startActivity(intent);
    }


    /**
     * login
     */
    private void Login() {
        String account = etAccount.getText().toString();
        String password = etPassword.getText().toString();
        if (account.isEmpty() || password.isEmpty()){
            showShortToast("账号或密码不能为空");
            return;
        }

        Callback<User> callback = new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                SharedPreferencesUtils.put(LoginActivity.this, "token", user.getAccessToken());
                SharedPreferencesUtils.put(LoginActivity.this, "userId", user.getUserId());
                if (user.getUserName().equals("")) {
                    startActivity(FirstSettingInfoActivity.class);
                    finish();
                }
                else{
                    startActivity(MainActivity.class);
                    finish();
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
                        showShortToast("账号或密码错误");
                        break;
                    case 404:
                        showShortToast("该账号不存在");
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
        Map<String,Object> param = new HashMap<>();
        param.put("account",account);
        param.put("password",password);
        LoginRequester loginRequester = new LoginRequester(callback,param);
        loginRequester.request();
    }
}
