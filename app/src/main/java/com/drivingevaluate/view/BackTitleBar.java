package com.drivingevaluate.view;


import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.drivingevaluate.R;

public class BackTitleBar {
    private  Activity mActivity;
    private  TextView titleTextView;
    /**
     * @see [自定义标题栏]
     * @param activity
     * @param title
     */
    public BackTitleBar(Activity activity) {
        super();
        mActivity = activity;
        activity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        activity.setContentView(R.layout.titlebar_back);
        activity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.titlebar_back);
        ImageButton titleBackBtn = (ImageButton) activity.findViewById(R.id.btn_back);
        titleBackBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mActivity.finish();
            }
        });
    }
    public  void setTitle(String title){
        titleTextView = (TextView) mActivity.findViewById(R.id.tv_titleBar);
        titleTextView.setText(title);
    }
}
