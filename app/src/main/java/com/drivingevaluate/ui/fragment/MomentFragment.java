package com.drivingevaluate.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.adapter.MomentAdapter;
import com.drivingevaluate.model.Moment;
import com.drivingevaluate.net.GetMomentListRequester;
import com.drivingevaluate.net.component.RequestErrorHandler;
import com.drivingevaluate.ui.AddMomentActivity;
import com.drivingevaluate.ui.MomentDetailActivity;
import com.drivingevaluate.ui.base.Yat3sFragment;
import com.drivingevaluate.view.RefreshLayout;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MomentFragment extends Yat3sFragment implements OnClickListener {
    private View root;
    private RefreshLayout momentRefresh;
    private ListView momentLv;
    private TextView sortTextView;
    private FloatingActionButton addFab;
    private List<Moment> mMoments = new ArrayList<>();
    private ImageButton backButton;
    private MomentAdapter momentAdapter;
    private int sort = 1; // 1按照时间 2按照距离
    private int loadType = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_moment, container, false);
        }
        initView();
        initEvent();
        getData(System.currentTimeMillis());
        return root;
    }

    private void initEvent() {
        sortTextView.setOnClickListener(this);

        momentRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadType = 0;
                getData(System.currentTimeMillis());
            }
        });

        momentRefresh.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                loadType = 1;
                getData(mMoments.get(mMoments.size()-1).getCreateTime());
            }
        });

        momentLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent commentIntent = new Intent(getActivity(), MomentDetailActivity.class);
                commentIntent.putExtra("momentId", mMoments.get(position).getId());
                getActivity().startActivity(commentIntent);
            }
        });
        backButton.setOnClickListener(this);
        addFab.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getData(System.currentTimeMillis());
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getData(Long timestamp) {
        Callback<List<Moment>> callback = new Callback<List<Moment>>() {
            @Override
            public void success(List<Moment> moments, Response response) {
                if (loadType == 0){
                    mMoments.clear();
                }else {
                    momentRefresh.setLoading(false);
                }
                mMoments.addAll(moments);
                momentAdapter.notifyDataSetChanged();
                momentRefresh.setRefreshing(false);
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
//        param.put("pageNum",page);
        GetMomentListRequester getMomentListRequester = new GetMomentListRequester(callback,param);
        getMomentListRequester.request();
    }

    private void initView() {
        momentRefresh = (RefreshLayout) root.findViewById(R.id.moment_fresh);
        momentLv = (ListView) root.findViewById(R.id.moment_lv);
        backButton = (ImageButton) root.findViewById(R.id.btn_back);

        sortTextView = (TextView) root.findViewById(R.id.tv_sort);

        momentAdapter = new MomentAdapter(getActivity(), mMoments,sort);
        momentLv.setAdapter(momentAdapter);

//        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_header,null);
//        momentLv.addHeaderView(headerView, null, false);
        momentRefresh.setColorSchemeResources(R.color.md_blue_300,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                R.color.md_grey_500);
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
                Intent toAddNewsIntent = new Intent(getActivity(), AddMomentActivity.class);
                startActivityForResult(toAddNewsIntent, 0);
                break;
            default:
                break;
        }
    }
}
