package com.drivingevaluate.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.drivingevaluate.R;
import com.drivingevaluate.adapter.CoachHorizontalAdapter;
import com.drivingevaluate.model.Coach;
import com.drivingevaluate.net.GetCoachListRequester;
import com.drivingevaluate.net.component.RequestErrorHandler;
import com.drivingevaluate.ui.base.Yat3sActivity;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ResultCoachActivity extends Yat3sActivity{
    private RecyclerView coachRv;
    private RecyclerView.LayoutManager coachRvLayoutManager;
    private CoachHorizontalAdapter coachHorizontalAdapter;
    private List<Coach> coaches = new ArrayList<>();
    private int merchantId;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private int pageNo = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_coachs);

        ButterKnife.bind(this);
        setToolbarWithNavigation(toolbar, "选择教练");
        initView();
        initEvent();

        getCoachListData();
    }


    private void initEvent() {

    }

    private void getCoachListData() {
        merchantId = getIntent().getExtras().getInt("merchantId");
        Callback<List<Coach>> callback = new Callback<List<Coach>>() {
            @Override
            public void success(List<Coach> coachList, Response response) {
                if (coachList.size() !=0 ) {
                    coaches.addAll(coachList);
                    pageNo++;
                    getCoachListData();
                }else{
                    dismissLoading();
                    coachHorizontalAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                RequestErrorHandler requestErrorHandler = new RequestErrorHandler(ResultCoachActivity.this);
                try {
                    requestErrorHandler.handError(error);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Map<String,Object> param = new HashMap<>();
        param.put("merchantId",merchantId);
        param.put("pageNo",pageNo);
        GetCoachListRequester getCoachListRequester = new GetCoachListRequester(callback,param);
        getCoachListRequester.request();
    }

    private void initView() {
        showLoading();
        coachRv = (RecyclerView) findViewById(R.id.coach_rv);
        coachRvLayoutManager = new GridLayoutManager(ResultCoachActivity.this,3);
        coachRv.setLayoutManager(coachRvLayoutManager);
        coachHorizontalAdapter = new CoachHorizontalAdapter(coaches,this);
        coachRv.setAdapter(coachHorizontalAdapter);
    }
}
