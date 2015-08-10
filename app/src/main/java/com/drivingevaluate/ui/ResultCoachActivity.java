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
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == StateConfig.CODE_GET_COACH_LIST) {
                coaches = JSON.parseArray(msg.obj.toString(), Coach.class);
                setCoachData();
            }
        }

    };
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
        param.put("merchantId",1);
        GetCoachListRequester getCoachListRequester = new GetCoachListRequester(callback,param);
        getCoachListRequester.request();
    }

    private void initView() {
        setTitleBarTitle("选择教练");
        coachRv = (RecyclerView) findViewById(R.id.coach_rv);
        coachRvLayoutManager = new LinearLayoutManager(ResultCoachActivity.this);
        coachRv.setLayoutManager(coachRvLayoutManager);
        coachHorizontalAdapter = new CoachHorizontalAdapter(coaches,this);
        coachRv.setAdapter(coachHorizontalAdapter);
    }

//    class CoachAdapter extends BaseAdapter{
//
//        @Override
//        public int getCount() {
//            return coachList.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return coachList.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            if (convertView == null) {
//                convertView = LayoutInflater.from(ResultCoachActivity.this).inflate(R.layout.item_lv_result_coach, null);
//            }
//            TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
//            TextView amountTv = (TextView) convertView.findViewById(R.id.tv_selectedAmount);
//            TextView classTv = (TextView) convertView.findViewById(R.id.tv_class);
//            ImageView coachImg = (ImageView) convertView.findViewById(R.id.coach_img);
//            loadImg(coachImg,coachList.get(position).getPhotoPath());
//            tvName.setText(coachList.get(position).getSellerName());
//            classTv.setText(coachList.get(position).getGoodsTitle());
//            amountTv.setText(coachList.get(position).getSellCount()+"人正在学习");
//            return convertView;
//        }
//    }
}
