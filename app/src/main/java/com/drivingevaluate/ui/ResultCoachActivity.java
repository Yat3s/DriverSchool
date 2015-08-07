package com.drivingevaluate.ui;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.drivingevaluate.R;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.api.JsonResolve;
import com.drivingevaluate.config.StateConfig;
import com.drivingevaluate.model.Coach;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ResultCoachActivity extends Yat3sActivity{
    private ListView lvCoachs;
    private List<Coach> coachList;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == StateConfig.CODE_GET_COACH_LIST) {
                coachList = JSON.parseArray(msg.obj.toString(), Coach.class);
                setData();
            }
        }
        private void setData() {
            lvCoachs.setAdapter(new CoachAdapter());
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

    private void initEvent() {
        lvCoachs.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = getIntent();
                intent.putExtra("coachName", coachList.get(position).getName());
                intent.putExtra("class", coachList.get(position).getClassType());
                setResult(0, intent);
                finish();
            }
        });
    }

    private void getDate() {
        JsonResolve.getCoachByDSchool("1", "0", "0", handler);
    }

    private void initView() {
        setTitleBarTitle("选择教练");
        lvCoachs = (ListView) findViewById(R.id.lv_cocach);
    }

    class CoachAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return coachList.size();
        }

        @Override
        public Object getItem(int position) {
            return coachList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(ResultCoachActivity.this).inflate(R.layout.item_lv_result_coach, null);
            }
            TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
            TextView amountTv = (TextView) convertView.findViewById(R.id.tv_selectedAmount);
            TextView classTv = (TextView) convertView.findViewById(R.id.tv_class);
            ImageView coachImg = (ImageView) convertView.findViewById(R.id.coach_img);
            loadImg(coachImg,coachList.get(position).getPhotoPath());
            tvName.setText(coachList.get(position).getName());
            classTv.setText(coachList.get(position).getClassType());
            amountTv.setText(coachList.get(position).getStudentAmount()+"人正在学习");
            return convertView;
        }
    }
}
