package com.drivingevaluate.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.adapter.MomentAdapter2;
import com.drivingevaluate.model.Moment;
import com.drivingevaluate.net.GetMomentListRequester;
import com.drivingevaluate.net.component.RequestErrorHandler;
import com.drivingevaluate.ui.AddMomentActivity;
import com.drivingevaluate.ui.LoginActivity;
import com.drivingevaluate.ui.MomentDetailActivity;
import com.drivingevaluate.ui.base.Yat3sFragment;
import com.drivingevaluate.util.SharedPreferencesUtils;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MomentFragment extends Yat3sFragment implements OnClickListener {
    private View root;
    private TextView sortTextView;
    private FloatingActionButton addFab;
    private List<Moment> mMoments = new ArrayList<>();
    private ImageButton backButton;
    private MomentAdapter2 momentAdapter;
    private int sort = 1; // 1按照时间 2按照距离
    private int loadType = 0;
    @Bind(R.id.moment_rv)
    UltimateRecyclerView momentRv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_moment, container, false);
        ButterKnife.bind(this, root);
        initView();
        initEvent();
        getData(System.currentTimeMillis());
        return root;
    }

    private void initEvent() {
        sortTextView.setOnClickListener(this);

        momentRv.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMoments.clear();
                getData(System.currentTimeMillis());
            }
        });

        momentRv.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int i, int i1) {
                getData(mMoments.get(mMoments.size()-1).getCreateTime());
            }
        });
        momentAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("momentId",mMoments.get(position).getId());
                checkLogin2startActivity(MomentDetailActivity.class,bundle);
            }
        });
        backButton.setOnClickListener(this);
        addFab.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(500);
                    mMoments.clear();
                    getData(System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getData(Long timestamp) {
        Callback<List<Moment>> callback = new Callback<List<Moment>>() {
            @Override
            public void success(List<Moment> moments, Response response) {
                if (loadType == 0) {
                    momentRv.setRefreshing(false);
                }
                mMoments.addAll(moments);
                momentAdapter.notifyDataSetChanged();
            }
            @Override
            public void failure(RetrofitError error) {
                RequestErrorHandler requestErrorHandler = new RequestErrorHandler(getActivity());
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
        param.put("timestamp",timestamp);
        if (sort == 2){
            param.put("city_code",mApplication.cityCode);
            param.put("latitude",mApplication.myLl.latitude);
            param.put("longitude",mApplication.myLl.longitude);
        }
        GetMomentListRequester getMomentListRequester = new GetMomentListRequester(callback,param);
        getMomentListRequester.request();
    }

    private void initView() {
        backButton = (ImageButton) root.findViewById(R.id.btn_back);
        sortTextView = (TextView) root.findViewById(R.id.tv_sort);

        momentRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        momentAdapter = new MomentAdapter2(getActivity(), mMoments);
        momentRv.setAdapter(momentAdapter);
        momentRv.enableLoadmore();
        addFab = (FloatingActionButton) root.findViewById(R.id.add_moment_fab);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sort:
                if (sort == 1) {
                    sort = 2;
                    sortTextView.setText("距离排序");
                    mMoments.clear();
                    getData(System.currentTimeMillis());
                } else {
                    sort = 1;
                    sortTextView.setText("时间排序");
                    mMoments.clear();
                    getData(System.currentTimeMillis());
                }
                break;
            case R.id.btn_back:
                getActivity().finish();
                break;
            case R.id.add_moment_fab:
                if (SharedPreferencesUtils.contains(getActivity(),"token")) {
                    Intent toAddNewsIntent = new Intent(getActivity(), AddMomentActivity.class);
                    startActivityForResult(toAddNewsIntent, 101);
                }
                else {
                    startActivity(LoginActivity.class);
                }
                break;
            default:
                break;
        }
    }
}
