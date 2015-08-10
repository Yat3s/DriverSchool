package com.drivingevaluate.ui;


import com.drivingevaluate.R;
import com.drivingevaluate.config.AppConf;
import com.drivingevaluate.net.LoginRequester;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.model.User;
import com.drivingevaluate.util.SharedPreferencesUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends Yat3sActivity implements OnClickListener{
    private Button btnLogin,btnReg;

    private EditText etAccount,etPassword;

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitleBar();
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        setTitleBarTitle("登录");

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReg = (Button) findViewById(R.id.btn_reg);

        etAccount = (EditText) findViewById(R.id.et_account);
        etPassword = (EditText) findViewById(R.id.et_password);

        btnLogin.setOnClickListener(this);
        btnReg.setOnClickListener(this);
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
                AppConf.TOKEN = SharedPreferencesUtils.get(LoginActivity.this,"token","").toString();
                AppConf.USER_ID = (int) SharedPreferencesUtils.get(LoginActivity.this,"userId",-1);
                startActivity(MainActivity.class);
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                showShortToast("账号或密码错误");
            }
        };
        Map<String,Object> param = new HashMap<>();
        param.put("account",account);
        param.put("password",password);
        LoginRequester loginRequester = new LoginRequester(callback,param);
        loginRequester.request();
    }

    /**
     * 点击两次退出
     */
    private long firstTime = 0;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {                                         //如果两次按键时间间隔大于2秒，则不退出
                    showShortToast("再按一次退出程序");
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {                                                    //两次按键小于2秒时，退出应用
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
}
