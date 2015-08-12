package com.drivingevaluate.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.model.Coach;
import com.drivingevaluate.net.GetCoachDetailRequester;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.util.MyUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CoachInfoActivity extends Yat3sActivity implements OnClickListener{

    private ListView lvComment;
    private TextView tvCoachName;
    private Button btnSelectMe,consultButton;
    private LinearLayout commentLayout;
    private ImageView avatorImageView;

    private String[] commenter = new String[]{"寂寞**雨","洗剪吹**特","你好**吗"};
    private String[] comments = new String[]{"这个教练挺不错的","还行哦特","特别棒"};
    private int coachId;
    private Coach coach;
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
        coachId = getIntent().getExtras().getInt("coachId");

        Callback<Coach> callback = new Callback<Coach>() {
            @Override
            public void success(Coach remoteCoach, Response response) {
                coach = remoteCoach;
                refreshView();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        };
        Map<String,Object> param = new HashMap<>();
        param.put("goodsId",coachId);
        GetCoachDetailRequester getCoachDetailRequester = new GetCoachDetailRequester(callback,param);
        getCoachDetailRequester.request();
    }

    private void refreshView() {
        tvCoachName.setText(coach.getSellerName());
        MyUtil.loadImg(avatorImageView, coach.getPhotoPath());

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
        avatorImageView = (ImageView) findViewById(R.id.img_avatar);
        consultButton = (Button) findViewById(R.id.consult_btn);

        commentLayout = (LinearLayout) findViewById(R.id.layoutComment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_selectMe:
                Bundle bundle = new Bundle();
                bundle.putString("coachName",coach.getSellerName());
                bundle.putString("coachSubject",coach.getGoodsTitle());
                bundle.putInt("coachId",coachId);
                bundle.putDouble("prePay",coach.getPrepayPrice());
                startActivity(ApplyDSchoolActivity.class,bundle);
                break;
            case R.id.consult_btn:
//                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+getIntent().getExtras().getString("tel"))));
                showShortToast("告诉我这个该咨询谁？");
                break;
            default:
                break;
        }
    }
}
