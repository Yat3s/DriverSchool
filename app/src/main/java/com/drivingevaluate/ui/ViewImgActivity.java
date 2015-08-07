package com.drivingevaluate.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.drivingevaluate.R;
import com.drivingevaluate.ui.base.Yat3sActivity;

public class ViewImgActivity extends Yat3sActivity implements OnClickListener{
    private ImageView img ;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_img_view);
        initView();
        initEvent();

        getData();
    }

    private void getData() {
        String url = getIntent().getStringExtra("imgUrl");
        loadImg(img,url);
    }

    private void initEvent() {
        img.setOnClickListener(this);
    }

    private void initView() {
        img = (ImageView) findViewById(R.id.img_view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_view:
                finish();
                break;

            default:
                break;
        }
    }
}
