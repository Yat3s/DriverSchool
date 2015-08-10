package com.drivingevaluate.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.drivingevaluate.R;
import com.drivingevaluate.adapter.CoachHorizontalAdapter;
import com.drivingevaluate.adapter.CourseAdapter;
import com.drivingevaluate.model.Course;
import com.drivingevaluate.model.Merchant;
import com.drivingevaluate.net.GetCoachListRequester;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.model.Coach;
import com.drivingevaluate.view.FullyLinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MerchantInfoActivity extends Yat3sActivity implements OnClickListener {
    private Button btnApply, btnConsult, btnMoreCoach;
    private TextView tvName, tvOurPrice,marketPriceTv, tvDSchoolInfo, tvCoachAmount, studentAmountTextView, gradeTextView,addressTv;
    private RelativeLayout navigateRl;
    private RatingBar gradeBar;
    private RecyclerView coachRv;
    private RecyclerView courseRv;
    private CoachHorizontalAdapter coachHorizontalAdapter;
    private CourseAdapter courseAdapter;
    private ImageButton btnMap;
    private List<Coach> coaches = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private String sid, sName;
    private Merchant merchant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackTitleBar();
        setContentView(R.layout.activity_merchant_info);

        initView();
        initEvent();

        getData();

    }

    private void refreshView() {
        tvName.setText(merchant.getSname());
        tvOurPrice.setText((int) merchant.getOurPrice() + "元");
        marketPriceTv.getPaint().setFlags(TextPaint.STRIKE_THRU_TEXT_FLAG);
        marketPriceTv.setText((int) merchant.getMarketPrice() + "元");
        studentAmountTextView.setText("学生数量:" + merchant.getSellCount());
        addressTv.setText(merchant.getSaddress());
        if (merchant.getSlevel()!=null){
            gradeTextView.setText(merchant.getSlevel().toString());
        }
        tvCoachAmount.setText("精品教练(" + coaches.size() + ")");
        btnMoreCoach.setText("查看全部" + coaches.size() + "位教练");
        coachHorizontalAdapter.notifyDataSetChanged();
        dismissLoading();


        //模拟数据
        Course course1 = new Course();
        course1.setPrice(3200);
        course1.setSubject("周一到周五");
        course1.setType("普通班");
        Course course2 = new Course();
        course2.setPrice(3800);
        course2.setSubject("全天");
        course2.setType("VIP班");

        courses.add(course1);
        courses.add(course2);

        courseAdapter.notifyDataSetChanged();

    }

    private void initEvent() {
        btnConsult.setOnClickListener(this);
        btnApply.setOnClickListener(this);
        btnMoreCoach.setOnClickListener(this);
        btnMap.setOnClickListener(this);
        tvDSchoolInfo.setOnClickListener(this);
        navigateRl.setOnClickListener(this);
    }

    private void getData() {
        showLoading();
        merchant = (Merchant) getIntent().getExtras().getSerializable("dSchool");
        sid = merchant.getSid();
        sName = merchant.getSname();

        setTitleBarTitle(sName);

        Callback<List<Coach>> callback = new Callback<List<Coach>>() {
            @Override
            public void success(List<Coach> coachList, Response response) {
                coaches.addAll(coachList);
                refreshView();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        };
        Map<String,Object> param = new HashMap<>();
        param.put("merchantId",sid);
        GetCoachListRequester getCoachListRequester = new GetCoachListRequester(callback,param);
        getCoachListRequester.request();
    }

    private void initView() {
        coachRv = (RecyclerView) findViewById(R.id.coach_merchant_rv);
        FullyLinearLayoutManager coachLinearLayoutManager = new FullyLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        coachHorizontalAdapter = new CoachHorizontalAdapter(coaches,this);
        coachRv.setLayoutManager(coachLinearLayoutManager);
        coachRv.setAdapter(coachHorizontalAdapter);

        courseRv = (RecyclerView) findViewById(R.id.price_merchant_rv);
        FullyLinearLayoutManager courseLinearLayoutManager = new FullyLinearLayoutManager(this);
        courseAdapter = new CourseAdapter(courses);
        courseRv.setLayoutManager(courseLinearLayoutManager);
        courseRv.setAdapter(courseAdapter);

        navigateRl = (RelativeLayout) findViewById(R.id.navigate_rl);

        btnApply = (Button) findViewById(R.id.btn_apply);
        btnConsult = (Button) findViewById(R.id.btn_consult);
        btnMoreCoach = (Button) findViewById(R.id.btn_moreCoach);

        tvName = (TextView) findViewById(R.id.tv_name);
        tvOurPrice = (TextView) findViewById(R.id.tv_ourPrice);
        tvDSchoolInfo = (TextView) findViewById(R.id.merchantIntro_tv);
        tvCoachAmount = (TextView) findViewById(R.id.tv_coachAmount);
        studentAmountTextView = (TextView) findViewById(R.id.tvStudentAmount);
        gradeTextView = (TextView) findViewById(R.id.grade_merchant_tv);
        marketPriceTv = (TextView) findViewById(R.id.tv_marketPrice);
        addressTv = (TextView) findViewById(R.id.address_tv);

        gradeBar = (RatingBar) findViewById(R.id.rb_score);

        btnMap = (ImageButton) findViewById(R.id.btn_map);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_apply:
                Intent applyIntent = new Intent(MerchantInfoActivity.this, ApplyDSchoolActivity.class);
                applyIntent.putExtra("sid", sid);
                applyIntent.putExtra("sName", sName);
                startActivity(applyIntent);
                break;
            case R.id.btn_consult:
                startActivity(ConsultActivity.class);
                break;
            case R.id.navigate_rl:
                Bundle bundle = new Bundle();
                bundle.putString("lat", merchant.getLatitude());
                bundle.putString("lng", merchant.getLongitude());
                startActivity(MerchantMapActivity.class, bundle);
                break;
            case R.id.merchantIntro_tv:
                Intent dSchoolInfoIntent = new Intent(MerchantInfoActivity.this, ResultDSchoolInfoActivity.class);
                startActivity(dSchoolInfoIntent);
                break;
            case R.id.btn_moreCoach:
                //TODO show all coaches
                break;
            default:
                break;
        }
    }
}
