package com.drivingevaluate.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.drivingevaluate.R;
import com.drivingevaluate.adapter.LuckyMoneyAdapter;
import com.drivingevaluate.config.AppConf;
import com.drivingevaluate.model.LuckyMoney;
import com.drivingevaluate.net.LuckyMoneyRequester;
import com.drivingevaluate.net.component.RequestErrorHandler;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.ui.fragment.LuckMoneyDialogFragment;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Yat3s on 8/16/15.
 * Email:hawkoyates@gmail.com
 */
public class GetMoneyActivity extends Yat3sActivity implements View.OnClickListener {
    private ImageButton getMoneyBtn;

    private RecyclerView luckyMoneyRv;
    private LuckyMoneyAdapter luckyMoneyAdapter;
    private List<LuckyMoney> mLuckyMoneys = new ArrayList<>();
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_money);

        ButterKnife.bind(this);
        setToolbarWithNavigation(toolbar, "抢红包");

        initView();
        initEvent();
        getData();
    }

    private void initEvent() {
        getMoneyBtn.setOnClickListener(this);
    }

    private void getData() {
        Callback<List<LuckyMoney>> callback = new Callback<List<LuckyMoney>>() {
            @Override
            public void success(List<LuckyMoney> luckyMoneys, Response response) {
                mLuckyMoneys.addAll(luckyMoneys);
                luckyMoneyAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Yat3s","getMoneyList---->"+error.getMessage());
            }
        };
        LuckyMoneyRequester luckyMoneyRequester = new LuckyMoneyRequester(callback);
        luckyMoneyRequester.getLuckyMoneyList();

    }

    private void initView() {
        getMoneyBtn = (ImageButton) findViewById(R.id.get_money_btn);

        luckyMoneyRv = (RecyclerView) findViewById(R.id.luckyMoney_rv);
        luckyMoneyAdapter = new LuckyMoneyAdapter(this,mLuckyMoneys);
        luckyMoneyRv.setLayoutManager(new LinearLayoutManager(this));
        luckyMoneyRv.setAdapter(luckyMoneyAdapter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_money_btn:
                grabMoney();
                break;
        }
    }

    private void grabMoney() {
        Callback<LuckyMoney> callback = new Callback<LuckyMoney>() {
            @Override
            public void success(LuckyMoney luckyMoney, Response response) {
                LuckMoneyDialogFragment luckMoneyDialogFragment = LuckMoneyDialogFragment.newInstance(luckyMoney.getHongbao());
                luckMoneyDialogFragment.show(getFragmentManager(),"luckyMoneyDialog");
            }

            @Override
            public void failure(RetrofitError error) {
                RequestErrorHandler requestErrorHandler = new RequestErrorHandler(GetMoneyActivity.this);
                try {
                    requestErrorHandler.handError(error);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        LuckyMoneyRequester luckyMoneyRequester = new LuckyMoneyRequester(callback, AppConf.USER_ID);
        luckyMoneyRequester.grabLuckyMoney();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_money, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.money_user_menu){
            checkLogin2startActivity(UserMoneyActivity.class,null);
        }
        return super.onOptionsItemSelected(item);
    }
}
