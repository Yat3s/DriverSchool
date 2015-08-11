package com.drivingevaluate.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.drivingevaluate.R;
import com.drivingevaluate.adapter.CoachHorizontalAdapter;
import com.drivingevaluate.net.GetCoachListRequester;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.api.JsonResolve;
import com.drivingevaluate.config.StateConfig;
import com.drivingevaluate.model.Coach;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ResultCoachActivity extends Yat3sActivity{
    private RecyclerView coachRv;
    private RecyclerView.LayoutManager coachRvLayoutManager;
    private CoachHorizontalAdapter coachHorizontalAdapter;
    private List<Coach> coaches = new ArrayList<>();
    private int merchantId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackTitleBar();
        setContentView(R.layout.activity_result_coachs);

        initView();
        initEvent();

        getDate();
    }
    private void setCoachData() {
        Log.e("Yat3s", coaches.size() + "");
        coachHorizontalAdapter.notifyDataSetChanged();
    }

    private void initEvent() {
//        lvCoachs.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = getIntent();
//                intent.putExtra("coachName", coachList.get(position).getSellerName());
//                intent.putExtra("class", coachList.get(position).getGoodsTitle());
//                setResult(0, intent);
//                finish();
//            }
//        });
    }

    private void getDate() {
//        JsonResolve.getCoachByDSchool("1", "0", "0", handler);
        merchantId = getIntent().getExtras().getInt("merchantId");
        Callback<List<Coach>> callback = new Callback<List<Coach>>() {
            @Override
            public void success(List<Coach> coachList, Response response) {
                coaches.addAll(coachList);
                setCoachData();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        };
        Map<String,Object> param = new HashMap<>();
        param.put("merchantId",merchantId);
        GetCoachListRequester getCoachListRequester = new GetCoachListRequester(callback,param);
        getCoachListRequester.request();
    }

    private void initView() {
        setTitleBarTitle("选择教练");
        coachRv = (RecyclerView) findViewById(R.id.coach_rv);
        coachRvLayoutManager = new GridLayoutManager(ResultCoachActivity.this,3);
        coachRv.setLayoutManager(coachRvLayoutManager);
        coachHorizontalAdapter = new CoachHorizontalAdapter(coaches,this);
        coachRv.setAdapter(coachHorizontalAdapter);
    }
}
