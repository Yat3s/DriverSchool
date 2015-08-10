package com.drivingevaluate.ui;

import com.drivingevaluate.R;
import com.drivingevaluate.ui.base.Yat3sActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class CoachInfoActivity extends Yat3sActivity implements OnClickListener{

    private ListView lvComment;
    private TextView tvCoachName;
    private Button btnSelectMe,consultButton;
    private LinearLayout commentLayout;
    private ImageView avatorImageView;

    private String[] commenter = new String[]{"寂寞**雨","洗剪吹**特","你好**吗"};
    private String[] comments = new String[]{"这个教练挺不错的","还行哦特","特别棒"};
    private String coachName,sName,sid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackTitleBar();
        setContentView(R.layout.activity_coach_info);

        initView();
        initEvent();

        getData();
    }

    private void initEvent() {
        btnSelectMe.setOnClickListener(this);
        consultButton.setOnClickListener(this);
    }

    private void getData() {
        lvComment.setFocusable(false);
        coachName = getIntent().getExtras().getString("coachName");
//        sName = getIntent().getExtras().getString("sName");
//        sid = getIntent().getExtras().getString("sid");
        tvCoachName.setText(coachName);
//        loadImg(avatorImageView,getIntent().getExtras().getString("pic"));
        for (int i = 0; i < commenter.length; i++) {
            View convertView = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.item_lv_coach_comment, null);
            TextView tvName = (TextView) convertView
                    .findViewById(R.id.tv_commenter);
            tvName.setText(commenter[i]);
            commentLayout.addView(convertView);
        }
    }

    private void initView() {
        setTitleBarTitle("教练详情");

        btnSelectMe = (Button) findViewById(R.id.btn_selectMe);
        tvCoachName = (TextView) findViewById(R.id.tv_coachName);
        lvComment = (ListView) findViewById(R.id.lv_comment);
        avatorImageView = (ImageView) findViewById(R.id.img_avator);
        consultButton = (Button) findViewById(R.id.consult_btn);

        commentLayout = (LinearLayout) findViewById(R.id.layoutComment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_selectMe:
                Bundle bundle = new Bundle();
                bundle.putString("coachName",coachName);
                bundle.putString("sName",sName);
                bundle.putString("sid",sid);
                startActivity(ApplyDSchoolActivity.class,bundle);
                break;
            case R.id.consult_btn:
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+getIntent().getExtras().getString("tel"))));
                break;
            default:
                break;
        }
    }
}
