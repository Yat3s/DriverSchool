package com.drivingevaluate.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.adapter.MomentAdapter;
import com.drivingevaluate.net.GetMomentListRequester;
import com.drivingevaluate.ui.base.Yat3sFragment;
import com.drivingevaluate.ui.MomentDetailActivity;
import com.drivingevaluate.model.Moment;
import com.drivingevaluate.util.MyUtil;
import com.drivingevaluate.view.ArcMenu;
import com.drivingevaluate.view.RefreshLayout;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MomentFragment extends Yat3sFragment implements OnClickListener {
    private View root;
    private RefreshLayout momentRefresh;
    private ListView momentlv;
    private TextView sortTextView;
    private List<Moment> momentList = new ArrayList<>();
    private Button backButton;
    private int page = 1;
    private Dialog loading;
    private ArcMenu mArcMenu;
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
        getData();
        return root;
    }

    private void initEvent() {
        sortTextView.setOnClickListener(this);

        momentRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                loadType = 0;
                getData();
            }
        });

        momentRefresh.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                loadType = 1;
                page++;
                getData();
            }
        });

        momentlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent commentIntent = new
                        Intent(getActivity(),MomentDetailActivity.class);
                commentIntent.putExtra("momentId",
                        momentList.get(position).getId());
                getActivity().startActivity(commentIntent);
            }
        });
//
//        mArcMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
//            @Override
//            public void onClick(View view, int pos) {
//                switch (pos) {
//                    case 4:
//                        Intent toAddNewsIntent = new Intent(getActivity(), AddMomentActivity.class);
//                        startActivityForResult(toAddNewsIntent, 0);
//                        break;
//
//                    default:
//                        break;
//                }
//            }
//        });
        backButton.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getData();
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getData() {

        Callback<List<Moment>> callback = new Callback<List<Moment>>() {
            @Override
            public void success(List<Moment> moments, Response response) {
                if (loadType == 0){
                    momentList.clear();
                }else {
                    momentRefresh.setLoading(false);
                }
                momentList.addAll(moments);
                momentAdapter.notifyDataSetChanged();
                momentRefresh.setRefreshing(false);
            }
            @Override
            public void failure(RetrofitError error) {
            }
        };
        Map<String,Object> param = new HashMap<>();
        param.put("locate","南昌");
        param.put("pageNum",page);
        GetMomentListRequester getMomentListRequester = new GetMomentListRequester(callback,param);
        getMomentListRequester.request();
    }

    private void initView() {
        momentRefresh = (RefreshLayout) root.findViewById(R.id.moment_fresh);
        momentlv = (ListView) root.findViewById(R.id.moment_lv);
        backButton = (Button) root.findViewById(R.id.btn_back);

        sortTextView = (TextView) root.findViewById(R.id.tv_sort);
//        mArcMenu = (ArcMenu) root.findViewById(R.id.id_menu);
        loading = MyUtil.createLoadingDialog(getActivity(), "努力加载中");

        momentAdapter = new MomentAdapter(getActivity(), momentList,sort);
        momentlv.setAdapter(momentAdapter);
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_header,null);
        momentlv.addHeaderView(headerView, null, false);
        momentRefresh.setColorSchemeResources(R.color.md_blue_300,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                R.color.md_grey_500);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sort:
                if (sort == 1) {
                    sort = 2;
                    sortTextView.setText("距离排序");
                    momentList.clear();
                    getData();
                } else {
                    sort = 1;
                    sortTextView.setText("时间排序");
                    momentList.clear();
                    getData();
                }
                break;
            case R.id.btn_back:
                getActivity().finish();
                break;
            default:
                break;
        }
    }
}
