package com.drivingevaluate.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.app.App;
import com.drivingevaluate.model.LuckyMoney;
import com.drivingevaluate.net.LuckyMoneyRequester;
import com.drivingevaluate.ui.base.Yat3sActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Yat3s on 8/20/15.
 * Email:hawkoyates@gmail.com
 */
public class UserMoneyActivity extends Yat3sActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.money_user_tv)
    TextView moneyTv;
    @Bind(R.id.tip_user_tv)
    TextView tipTv;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_user_money);
        ButterKnife.bind(this);
        setToolbarWithNavigation(toolbar, "我的红包");
        getData();
    }

    private void getData() {
        Callback<LuckyMoney> callback = new Callback<LuckyMoney>() {
            @Override
            public void success(LuckyMoney luckyMoney, Response response) {
                moneyTv.setText(luckyMoney.getHongbao()+"元");
            }

            @Override
            public void failure(RetrofitError error) {
                tipTv.setText("你还未领取红包,赶快到红包专区领取你的红包吧");
            }
        };
        LuckyMoneyRequester luckyMoneyRequester = new LuckyMoneyRequester(callback, App.getUserId());
        luckyMoneyRequester.getUserLuckyMoney();
    }
}
