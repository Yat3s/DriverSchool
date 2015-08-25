package com.drivingevaluate.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.app.App;
import com.drivingevaluate.model.Order;
import com.drivingevaluate.net.EvaluateRequester;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.util.MyUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Yat3s on 8/22/15.
 * Email:hawkoyates@gmail.com
 */
public class EvaluateActivity extends Yat3sActivity{
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.avatar_img)
    ImageView avatarImg;
    @Bind(R.id.name_coach_tv) TextView nameCoachTv;
    @Bind(R.id.name_merchant_tv) TextView nameMerchantTv;
    @Bind(R.id.subject_coach_tv) TextView subjectCoachTv;

    @Bind(R.id.study_rb) RatingBar studyRb;
    @Bind(R.id.place_rb) RatingBar placeRb;
    @Bind(R.id.quality_rb) RatingBar qualityRb;
    @Bind(R.id.car_rb) RatingBar carRb;

    @Bind(R.id.content_et) EditText contentEt;
    private Order order;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_evaluate);
        ButterKnife.bind(this);
        setToolbarWithNavigation(toolbar,"评价教练");
        initView();
    }

    private void initView() {
        order = (Order) getIntent().getExtras().getSerializable("order");
        nameMerchantTv.setText(order.getSname());
        subjectCoachTv.setText(order.getGoodsTitle());
        MyUtil.loadImg(avatarImg,order.getPhotoPath());
    }


    @OnClick(R.id.commit_btn)
    void commit(){
        if (contentEt.getText().toString().equals("")){
            showShortToast("请给教练一点评价内容吧");
            return;
        }
        Callback<String> callback = new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                showShortToast("评价成功");
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                showShortToast(error.getMessage());
            }
        };
        Map<String,Object> param = new HashMap<>();
        param.put("goodsId",order.getGoodsId());
        param.put("userId", App.getUserId());
        param.put("fengqi",studyRb.getRating());
        param.put("changdi",placeRb.getRating());
        param.put("quality",qualityRb.getRating());
        param.put("chekuang",carRb.getRating());
        param.put("orderId",order.getOrderNo());
        param.put("judegWord",contentEt.getText().toString());
        EvaluateRequester evaluateRequester = new EvaluateRequester(callback,param);
        evaluateRequester.request();
    }
}
