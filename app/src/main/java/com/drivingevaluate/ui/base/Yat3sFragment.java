package com.drivingevaluate.ui.base;

import com.drivingevaluate.app.DEApplication;
import com.drivingevaluate.config.Config;
import com.drivingevaluate.util.MyUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Yat3sFragment extends Fragment{
    protected DEApplication mApplication;
    protected Dialog mLoading;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = DEApplication.getInstance();
        mLoading = MyUtil.createLoadingDialog(getActivity(), "努力加载中");
    }

    /**
     * Toast相关
     *
     * @param paramInt
     */
    protected void showLongToast(int paramInt) {
        Toast.makeText(getActivity(), getString(paramInt), Toast.LENGTH_LONG).show();
    }

    protected void showLongToast(String paramString) {
        Toast.makeText(getActivity(), paramString, Toast.LENGTH_LONG).show();
    }

    public void showShortToast(int paramInt) {
        Toast.makeText(getActivity(), getString(paramInt), Toast.LENGTH_SHORT).show();
    }

    public void showShortToast(String paramString) {
        Toast.makeText(getActivity(), paramString, Toast.LENGTH_SHORT).show();
    }

    /**
     * 打Log ShowLog
     *
     * @return void
     * @throws
     */
    protected void ShowLog(String msg) {
        Log.d("Yat3s", msg);
    }

    public final <E extends View> E getView (int id) {
        try {
            return (E) getActivity().findViewById(id);
        } catch (ClassCastException ex) {
            Log.e("Yat3s","Could not cast View to concrete class");
            throw ex;
        }
    }
    public void loadImg(ImageView img,String url) {
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

    public String getLat() {
        return mApplication.myLl.latitude+"";
    }
    public String getLng() {
        return mApplication.myLl.longitude+"";
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
        localIntent.setClass(getActivity(), paramClass);
        if (paramBundle != null)
            localIntent.putExtras(paramBundle);
        startActivity(localIntent);
    }
}
