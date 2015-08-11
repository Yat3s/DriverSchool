package com.drivingevaluate.ui;

import com.drivingevaluate.R;
import com.drivingevaluate.config.AppConf;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.util.DataContainer;
import com.drivingevaluate.util.SharedPreferencesHelper;
import com.drivingevaluate.util.SharedPreferencesUtils;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class SplashActivity extends Yat3sActivity {
    private Animation animation;
    private RelativeLayout welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.enlarge);
        animation.setFillAfter(true);
        welcome = (RelativeLayout) findViewById(R.id.welcome);
//        welcome.startAnimation(animation);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    while (mApplication.myLl == null) {
                        Thread.sleep(500);
                    }//如果2秒内还没获取user或者定位 就再继续等。
                    checkIsLogin();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void checkIsLogin() {
        if (SharedPreferencesUtils.contains(SplashActivity.this,"token")){
            AppConf.TOKEN = SharedPreferencesUtils.get(SplashActivity.this,"token","").toString();
            AppConf.USER_ID = (int) SharedPreferencesUtils.get(SplashActivity.this,"userId",-1);
            if (AppConf.TOKEN != null && !AppConf.TOKEN.isEmpty()){
                startActivity(MainActivity.class);
                finish();
            }
        }
        else {
            startActivity(LoginActivity.class);
            finish();
        }
    }
}
