package com.drivingevaluate.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.ui.base.Yat3sActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Yat3s on 8/31/15.
 * Email:hawkoyates@gmail.com
 */
public class OrderCompletedActivity extends Yat3sActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.id_tv)
    TextView idTv;
    @Bind(R.id.address_tv)
    TextView addressTv;
    @Bind(R.id.tel_tv)
    TextView telTv;
    @Bind(R.id.pay_tv)
    TextView payTv;
    @Bind(R.id.coach_tv)
    TextView coachTv;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_order_completed);
        ButterKnife.bind(this);
        setToolbarWithNavigation(toolbar, "预付成功");
        Bundle bundle = getIntent().getExtras();
        nameTv.setText(bundle.getString("name"));
        idTv.setText(bundle.getString("id"));
        addressTv.setText(bundle.getString("address"));
        telTv.setText(bundle.getString("tel"));
        coachTv.setText(bundle.getString("coach"));
        payTv.setText(bundle.getDouble("pay") + "元");
    }

    @OnClick(R.id.complete_btn)
    void complete() {
        finish();
    }
}
