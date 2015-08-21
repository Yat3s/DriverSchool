package com.drivingevaluate.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.ui.base.Yat3sActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ResultSchoolActivity extends Yat3sActivity{
    private ListView lvSchool;
    private String[] schoolList ;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_school);
        ButterKnife.bind(this);
        setToolbarWithNavigation(toolbar, "选择学校");
        initView();
    }

    private void initView() {
        schoolList = getResources().getStringArray(R.array.school);
        lvSchool = (ListView) findViewById(R.id.lv_school);

        lvSchool.setAdapter(new SchoolAdapter());

        lvSchool.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = getIntent();
                intent.putExtra("schoolName", schoolList[position]);
                setResult(1,intent);
                finish();
            }
        });
    }

    class SchoolAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return schoolList.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_lv_school, null);
            TextView tvSchool = (TextView) convertView.findViewById(R.id.tv_name);
            tvSchool.setText(schoolList[position]);
            return convertView;
        }

    }
}
