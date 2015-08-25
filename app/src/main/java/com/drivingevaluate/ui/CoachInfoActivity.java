package com.drivingevaluate.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.adapter.EvaluationAdapter;
import com.drivingevaluate.model.Coach;
import com.drivingevaluate.model.Evaluation;
import com.drivingevaluate.net.GetCoachDetailRequester;
import com.drivingevaluate.net.GetCoachEvaluationListRequester;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.util.MyUtil;
import com.drivingevaluate.view.FullyLinearLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CoachInfoActivity extends Yat3sActivity implements OnClickListener{
    private TextView tvCoachName;
    private Button btnSelectMe,consultButton;
    private ImageView avatarImg;

    private int coachId;
    private Coach coach;
    private List<Evaluation> mEvaluations = new ArrayList<>();
    private EvaluationAdapter evaluationAdapter;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.evaluation_coach_rv) RecyclerView evaluationRv;
    @Bind(R.id.intro_coach_tv) TextView introTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_info);

        ButterKnife.bind(this);
        setToolbarWithNavigation(toolbar, "教练详情");

        initView();
        initEvent();

        getData();
    }

    private void initEvent() {
        btnSelectMe.setOnClickListener(this);
        consultButton.setOnClickListener(this);
    }

    private void getData() {
        coachId = getIntent().getIntExtra("coachId",-1);
        getCoachInfo();
        getCoachEvaluation();
    }

    /**
     * get Coach Evaluations
     */
    private void getCoachEvaluation() {
        Callback<List<Evaluation>> callback = new Callback<List<Evaluation>>() {
            @Override
            public void success(List<Evaluation> evaluations, Response response) {
                mEvaluations.addAll(evaluations);
                evaluationAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Yat3s", "getCoachEvaluation---->"+error.getMessage());
            }
        };
        Map<String,Object> param = new HashMap<>();
        param.put("goodsId",coachId);
        param.put("timestamp", System.currentTimeMillis());
        GetCoachEvaluationListRequester getCoachEvaluationListRequester = new GetCoachEvaluationListRequester(callback,param);
        getCoachEvaluationListRequester.request();

    }

    /**
     * get Coach Information
     */
    private void getCoachInfo() {
        Callback<Coach> callback = new Callback<Coach>() {
            @Override
            public void success(Coach remoteCoach, Response response) {
                coach = remoteCoach;
                tvCoachName.setText(coach.getSellerName());
                if (coach.getDesc()!=null)
                    introTV.setText(coach.getDesc());
                MyUtil.loadImg(avatarImg, coach.getPhotoPath());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Yat3s","getCoachInfo---->"+error.getMessage());
            }
        };
        Map<String,Object> param = new HashMap<>();
        param.put("goodsId", coachId);
        GetCoachDetailRequester getCoachDetailRequester = new GetCoachDetailRequester(callback,param);
        getCoachDetailRequester.request();
    }


    private void initView() {
        btnSelectMe = (Button) findViewById(R.id.btn_selectMe);
        tvCoachName = (TextView) findViewById(R.id.tv_coachName);
        avatarImg = (ImageView) findViewById(R.id.img_avatar);
        consultButton = (Button) findViewById(R.id.consult_btn);

        evaluationAdapter = new EvaluationAdapter(mEvaluations,this);
        evaluationRv.setLayoutManager(new FullyLinearLayoutManager(this));
        evaluationRv.setAdapter(evaluationAdapter);
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
