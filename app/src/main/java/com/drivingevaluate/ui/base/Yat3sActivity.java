package com.drivingevaluate.ui.base;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import com.drivingevaluate.app.DEApplication;
import com.drivingevaluate.config.Config;
import com.drivingevaluate.util.MyUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class Yat3sActivity extends FragmentActivity {
    protected DEApplication mApplication;
    protected Dialog mLoading;

    protected int mScreenWidth;
    protected int mScreenHeight;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mApplication = DEApplication.getInstance();
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
        ImageLoader.getInstance().displayImage("http://121.43.234.220:8090/upload/"+url, img,Config.ImageOptions);
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
