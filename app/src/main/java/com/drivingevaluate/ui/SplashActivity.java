package com.drivingevaluate.ui;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.baidu.mapapi.model.LatLng;
import com.drivingevaluate.R;
import com.drivingevaluate.config.AppConf;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.util.SharedPreferencesUtils;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    if (mApplication.myLl == null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showShortToast("请检查网络连接");
                                mApplication.myLl = new LatLng(0.0,0.0);
                            }
                        });
                    }
                    runApp();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void runApp() {
        if (SharedPreferencesUtils.contains(SplashActivity.this,"token")){
            AppConf.TOKEN = SharedPreferencesUtils.get(SplashActivity.this,"token","").toString();
            AppConf.USER_ID = (int) SharedPreferencesUtils.get(SplashActivity.this,"userId",-1);

        }
        startActivity(MainActivity.class);
        finish();
    }
}
