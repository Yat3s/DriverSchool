package com.drivingevaluate.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.drivingevaluate.R;
import com.drivingevaluate.adapter.LuckyMoneyAdapter;
import com.drivingevaluate.model.LuckyMoney;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.ui.fragment.LuckMoneyDialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yat3s on 8/16/15.
 * Email:hawkoyates@gmail.com
 */
public class GetMoneyActivity extends Yat3sActivity implements View.OnClickListener {
    private ImageButton getMoneyBtn;

    private RecyclerView luckyMoneyRv;
    private LuckyMoneyAdapter luckyMoneyAdapter;
    private List<LuckyMoney> mLuckyMoneys = new ArrayList<>();
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
//        setBackTitleBar();
        setContentView(R.layout.activity_money);

        initView();
        initEvent();
        getData();
    }

    private void initEvent() {
        getMoneyBtn.setOnClickListener(this);
    }

    private void getData() {
        for (int i = 0; i < 10; i++) {
            LuckyMoney luckyMoney = new LuckyMoney("1321***121"+i,"2015-08-29",i*100);
            mLuckyMoneys.add(luckyMoney);
        }
        luckyMoneyAdapter.notifyDataSetChanged();
    }

    private void initView() {
//        setTitleBarTitle("抽取红包");

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
                LuckMoneyDialogFragment luckMoneyDialogFragment = LuckMoneyDialogFragment.newInstance();
                luckMoneyDialogFragment.show(getFragmentManager(),"luckyMoneyDialog");
//                Dialog dialog = new Dialog(this);
//                dialog.setContentView(R.layout.dialog_money);
//                dialog.show();
//                new AlertDialog.Builder(this).setView(R.layout.dialog_money).show();
                break;
        }
    }
}
