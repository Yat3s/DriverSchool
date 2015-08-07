package com.drivingevaluate.ui;


import com.drivingevaluate.R;
import com.drivingevaluate.view.BackTitleBar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ResultSchoolActivity extends Activity{
    private ListView lvSchool;
    private String[] schoolList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BackTitleBar titleBar = new BackTitleBar(this);
        setContentView(R.layout.activity_result_school);
        titleBar.setTitle("选择学校");
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
                intent.putExtra("name", schoolList[position]);
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
