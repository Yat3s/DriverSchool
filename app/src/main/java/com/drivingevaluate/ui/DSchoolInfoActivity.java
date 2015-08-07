package com.drivingevaluate.ui;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.drivingevaluate.R;
import com.drivingevaluate.model.Merchant;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.api.JsonResolve;
import com.drivingevaluate.config.StateConfig;
import com.drivingevaluate.model.Coach;
import com.drivingevaluate.model.DSchool;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class DSchoolInfoActivity extends Yat3sActivity implements OnClickListener {
    private Button btnApply, btnConsult, btnMoreCoach;
    private TextView tvName, tvOurPrice,marketPriceTv, tvDSchoolInfo, tvCoachAmount, studentAmountTextView, gradeTextView;
    private RatingBar gradeBar;
    private LinearLayout coachLayout, serviceLayout;
    private ImageButton btnMap;
    private List<Coach> coachs;
    private String sid, sName;
    private int defaultSize;
    private Merchant merchant;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == StateConfig.CODE_GET_COACH_LIST) {
                coachs = JSON.parseArray(msg.obj.toString(),Coach.class);
                setCoachData();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackTitleBar();
        setContentView(R.layout.activity_dschool_info);

        initView();
        initEvent();

        getData();

    }

    private void setCoachData() {
        tvCoachAmount.setText("精品教练(" + coachs.size() + ")");
        defaultSize = coachs.size() < 2 ? 1 : 2;
        if (coachs.size() == 0) {
            defaultSize = 0;
        }
        if (coachs.size() <= 2) {
            btnMoreCoach.setVisibility(View.GONE);
        }
        btnMoreCoach.setText("查看其他" + (coachs.size() - 2) + "位教练");
        for (int i = 0; i < defaultSize; i++) {
            View convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_lv_coach, null);
            TextView tvName = (TextView) convertView.findViewById(R.id.tv_coachName);
            TextView subjectTv = (TextView) convertView.findViewById(R.id.subject_tv);
            TextView studentAmountTv = (TextView) convertView.findViewById(R.id.studentAmount_tv);
            ImageView coachImg = (ImageView) convertView.findViewById(R.id.coach_img);

            subjectTv.setText(coachs.get(i).getClassType());
            studentAmountTv.setText(coachs.get(i).getStudentAmount()+"名学生");
            loadImg(coachImg, coachs.get(i).getPhotoPath());
            tvName.setText(coachs.get(i).getName());
            convertView.setOnClickListener(new lvButtonListener(i));
            coachLayout.addView(convertView);
        }
        dismissLoading();
    }

    class lvButtonListener implements OnClickListener {
        private int position;
        private Boolean liked = false;
        private TextView tvLikeAmout, tvLikeTag;

        lvButtonListener(int pos) {
            position = pos;
        }

        @Override
        public void onClick(View arg0) {
            Bundle bundle = new Bundle();
            bundle.putString("coachName", coachs.get(position).getName());
            bundle.putString("sName", sName);
            bundle.putString("sid", sid);
            bundle.putString("pic", coachs.get(position).getPhotoPath());
            bundle.putString("tel", coachs.get(position).getTel());
            startActivity(CoachInfoActivity.class, bundle);
        }

    }

    private void initEvent() {
        btnConsult.setOnClickListener(this);
        btnApply.setOnClickListener(this);
        btnMoreCoach.setOnClickListener(this);
        btnMap.setOnClickListener(this);
        tvDSchoolInfo.setOnClickListener(this);
    }

    private void getData() {
        showLoading();
        merchant = (Merchant) getIntent().getExtras().getSerializable("dSchool");
        sid = merchant.getSid();
        sName = merchant.getSname();

        setTitleBarTitle(sName);
        tvName.setText(merchant.getSname());
        tvOurPrice.setText((int) merchant.getOurPrice() + "元");
        marketPriceTv.getPaint().setFlags(TextPaint.STRIKE_THRU_TEXT_FLAG);
        marketPriceTv.setText((int) merchant.getMarketPrice() + "元");
        studentAmountTextView.setText("学生数量:" + merchant.getSellCount());
//        gradeTextView.setText(merchant.getSlevel() + "");
//        gradeBar.setRating(merchant.getSlevel());

        JsonResolve.getCoachByDSchool("1","0","0",handler);

    }

//    public void getService() {
//        LinearLayout layout = new LinearLayout(DSchoolInfoActivity.this);
//        for (int i = 0; i < services.length; i++) {
//            TextView serviceTextView = new TextView(DSchoolInfoActivity.this);
//            serviceTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
//            serviceTextView.setPadding(10, 5, 10,5);
//            serviceTextView.setCompoundDrawables(getResources().getDrawable(R.mipmap.ic_true), null, null, null);
//            serviceTextView.setText(services[i]);
//            layout.addView(serviceTextView);
//            if ((i + 1) % 3 == 0) {
//                serviceLayout.addView(layout);
//                layout = new LinearLayout(DSchoolInfoActivity.this);
//            }
//        }
//    }

    private void initView() {
        coachLayout = (LinearLayout) findViewById(R.id.layoutCoach);
        serviceLayout = (LinearLayout) findViewById(R.id.service_layout);

        btnApply = (Button) findViewById(R.id.btn_apply);
        btnConsult = (Button) findViewById(R.id.btn_consult);
        btnMoreCoach = (Button) findViewById(R.id.btn_moreCoach);

        tvName = (TextView) findViewById(R.id.tv_name);
        tvOurPrice = (TextView) findViewById(R.id.tv_ourPrice);
        tvDSchoolInfo = (TextView) findViewById(R.id.tv_dschoolInfo);
        tvCoachAmount = (TextView) findViewById(R.id.tv_coachAmount);
        studentAmountTextView = (TextView) findViewById(R.id.tvStudentAmount);
        gradeTextView = (TextView) findViewById(R.id.tvScore);
        marketPriceTv = (TextView) findViewById(R.id.tv_marketPrice);

        gradeBar = (RatingBar) findViewById(R.id.rb_score);

        btnMap = (ImageButton) findViewById(R.id.btn_map);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_apply:
                Intent applyIntent = new Intent(DSchoolInfoActivity.this, ApplyDSchoolActivity.class);
                applyIntent.putExtra("sid", sid);
                applyIntent.putExtra("sName", sName);
                startActivity(applyIntent);
                break;
            case R.id.btn_consult:
                startActivity(ConsultActivity.class);
                break;
            case R.id.btn_map:
                Bundle bundle = new Bundle();
                bundle.putString("lat", merchant.getLatitude());
                bundle.putString("lng", merchant.getLongitude());
                startActivity(DSchoolMapActivity.class, bundle);
                break;
            case R.id.tv_dschoolInfo:
                Intent dSchoolInfoIntent = new Intent(DSchoolInfoActivity.this, ResultDSchoolInfoActivity.class);
                startActivity(dSchoolInfoIntent);
                break;
            case R.id.btn_moreCoach:
                btnMoreCoach.setVisibility(View.GONE);
                for (int i = defaultSize; i < coachs.size(); i++) {
                    View convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_lv_coach, null);
                    TextView tvName = (TextView) convertView.findViewById(R.id.tv_coachName);
                    TextView subjectTv = (TextView) convertView.findViewById(R.id.subject_tv);
                    TextView studentAmountTv = (TextView) convertView.findViewById(R.id.studentAmount_tv);
                    ImageView coachImg = (ImageView) convertView.findViewById(R.id.coach_img);

                    subjectTv.setText(coachs.get(i).getClassType());
                    studentAmountTv.setText(coachs.get(i).getStudentAmount()+"名学生");
                    loadImg(coachImg, coachs.get(i).getPhotoPath());
                    tvName.setText(coachs.get(i).getName());
                    convertView.setOnClickListener(new lvButtonListener(i));
                    coachLayout.addView(convertView);
                }
                break;
            default:
                break;
        }
    }
}
