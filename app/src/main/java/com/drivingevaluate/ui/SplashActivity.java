package com.drivingevaluate.ui;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.baidu.mapapi.model.LatLng;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.drivingevaluate.R;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.view.TypeTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends Yat3sActivity {
    private Animation animation;
    private RelativeLayout welcome;
    @Bind(R.id.subtitle)
    TypeTextView subTitleTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        ButterKnife.bind(this);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.enlarge);
        animation.setFillAfter(true);
        welcome = (RelativeLayout) findViewById(R.id.welcome);
        YoYo.with(Techniques.BounceInUp).duration(2000)
                .playOn(findViewById(R.id.logo));
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
        subTitleTv.start("让驾照考试更简单", 100);
        subTitleTv.setOnTypeViewListener(new TypeTextView.OnTypeViewListener() {
            @Override
            public void onTypeStart() {

            }

            @Override
            public void onTypeOver() {
                YoYo.with(Techniques.FadeOutRight).duration(1000)
                        .playOn(findViewById(R.id.logo));
                YoYo.with(Techniques.FadeOutLeft).duration(1000)
                        .playOn(subTitleTv);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(500);
                            startActivity(MainActivity.class);
                            finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }
}
