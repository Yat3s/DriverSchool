package com.drivingevaluate.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.adapter.EvaluationAdapter;
import com.drivingevaluate.model.Evaluation;
import com.drivingevaluate.model.Merchant;
import com.drivingevaluate.net.GetAllEvaluationListRequester;
import com.drivingevaluate.net.component.RequestErrorHandler;
import com.drivingevaluate.ui.base.Yat3sActivity;

import org.json.JSONException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Yat3s on 8/11/15.
 * Email:hawkoyates@gmail.com
 */
public class EvaluationActivity extends Yat3sActivity {
    private RecyclerView evaluationRv;
    private RecyclerView.LayoutManager layoutManager;
    private EvaluationAdapter evaluationAdapter;
    private List<Evaluation> evaluations = new ArrayList<>();

    private TextView timeGradeTv,placeGradeTv,serviceGradeTv,gradeTv,evaluationNumTv;
    private RatingBar timeGradeRb,placeGradeRb,serviceGradeRb;

    private int merchantId;
    private Merchant merchant;
    private int pageNo = 1;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_merchant_evaluation);

        ButterKnife.bind(this);
        setToolbarWithNavigation(toolbar, "评价列表");

        initView();
        merchantId = getIntent().getExtras().getInt("merchantId");
        merchant = (Merchant) getIntent().getExtras().getSerializable("merchant");
        getData(System.currentTimeMillis());
    }

    private void getData(long timestamp) {
        Callback<List<Evaluation>> callback = new Callback<List<Evaluation>>() {
            @Override
            public void success(List<Evaluation> remoteEvaluations, Response response) {
                if (remoteEvaluations.size()!=0 ){
                    evaluations.addAll(remoteEvaluations);
                    getData(evaluations.get(evaluations.size() - 1).getCreateTime());
                }
                else {
                    evaluationAdapter.notifyDataSetChanged();
                    evaluationNumTv.setText(evaluations.size()+"人评价");
                    //评分
                    gradeTv.setText(new DecimalFormat("#.0").format(merchant.getAvgGrade()));
                    timeGradeRb.setRating(merchant.getItem1());
                    placeGradeRb.setRating(merchant.getItem2());
                    serviceGradeRb.setRating(merchant.getItem3());
                    timeGradeTv.setText(merchant.getItem1() + "分");
                    placeGradeTv.setText(merchant.getItem2() + "分");
                    serviceGradeTv.setText(merchant.getItem3() + "分");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                RequestErrorHandler requestErrorHandler = new RequestErrorHandler(EvaluationActivity.this);
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
        param.put("timestamp",timestamp);
        GetAllEvaluationListRequester getAllEvaluationListRequester = new GetAllEvaluationListRequester(callback,param);
        getAllEvaluationListRequester.request();
    }

    private void initView() {
        evaluationRv = (RecyclerView) findViewById(R.id.evaluation_merchant_rv);
        layoutManager = new LinearLayoutManager(this);
        evaluationAdapter = new EvaluationAdapter(evaluations,this);
        evaluationRv.setLayoutManager(layoutManager);
        evaluationRv.setAdapter(evaluationAdapter);

        evaluationNumTv = (TextView) findViewById(R.id.num_evaluation_tv);
        gradeTv = (TextView) findViewById(R.id.grade_merchant_tv);
        timeGradeRb = (RatingBar) findViewById(R.id.time_grade_rb);
        placeGradeRb = (RatingBar) findViewById(R.id.place_grade_rb);
        serviceGradeRb = (RatingBar) findViewById(R.id.service_grade_rb);

        timeGradeTv = (TextView) findViewById(R.id.time_grade_tv);
        placeGradeTv = (TextView) findViewById(R.id.place_grade_tv);
        serviceGradeTv = (TextView) findViewById(R.id.service_grade_tv);
    }
}
