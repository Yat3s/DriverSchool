package com.drivingevaluate.ui.base;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.drivingevaluate.R;
import com.drivingevaluate.app.App;
import com.drivingevaluate.config.Config;
import com.drivingevaluate.ui.LoginActivity;
import com.drivingevaluate.util.MyUtil;
import com.drivingevaluate.util.SharedPreferencesUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class Yat3sActivity extends AppCompatActivity {
    protected App mApplication;
    protected Dialog mLoading;

    protected int mScreenWidth;
    protected int mScreenHeight;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mApplication = App.getInstance();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;

        mLoading = MyUtil.createLoadingDialog(this, "努力加载中");
    }

    /**
     * Toast相关
     *
     * @param
     */
    public void showShortToast(String paramString) {
        Toast.makeText(this, paramString, Toast.LENGTH_SHORT).show();
    }
    public void showLongToast(String paramString) {
        Toast.makeText(this, paramString, Toast.LENGTH_LONG).show();
    }

    @SuppressWarnings("unchecked")
    public final <E extends View> E getView (int id) {
        try {
            return (E) findViewById(id);
        } catch (ClassCastException ex) {
            Log.e("Yat3s","Could not cast View to concrete class");
            throw ex;
        }
    }
    /**
     * 打Log ShowLog
     *
     * @return void
     * @throws
     */
    protected void ShowLog(String msg) {
        Log.e("Yat3s", msg);
    }
    protected void loadImg(ImageView img,String url) {
        ImageLoader.getInstance().displayImage( url, img, Config.ImageOptions);
    }
    /**
     * 开始定位
     *
     * @return void
     * @throws
     */
    public void Loc() {
        mApplication.initBaiduLocClient();
    }

    protected void showLoading() {
        mLoading.show();
    }

    protected void dismissLoading() {
        mLoading.dismiss();
    }

    protected void startActivity(Class<?> paramClass) {
        startActivity(paramClass, null);
    }

    protected void startActivity(Class<?> paramClass, Bundle paramBundle) {
        Intent localIntent = new Intent();
        localIntent.setClass(this, paramClass);
        if (paramBundle != null)
            localIntent.putExtras(paramBundle);
        startActivity(localIntent);
    }
    /**
     * 检查是否登录。为什么不用异常401来引导到登陆界面 是有原因的
     * @param paramClass
     * @param paramBundle
     */
    protected void checkLogin2startActivity(Class<?> paramClass, Bundle paramBundle){
        if(SharedPreferencesUtils.contains(this, "token")){
            startActivity(paramClass, paramBundle);
        }
        else {
            startActivity(LoginActivity.class);
            showShortToast("请先登录");
        }
    }

    protected void setToolbarWithNavigation(Toolbar toolbar, String title) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    protected void setBackTitleBar() {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.titlebar_back);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar_back);
        ImageButton titleBackBtn = (ImageButton) findViewById(R.id.btn_back);
        titleBackBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void setTitleBarTitle(String title) {
        TextView titleTextView = (TextView) findViewById(R.id.tv_titleBar);
        titleTextView.setText(title);
    }

    protected void setCustomTitleBar() {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.titlebar_custom);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar_custom);
    }
}
