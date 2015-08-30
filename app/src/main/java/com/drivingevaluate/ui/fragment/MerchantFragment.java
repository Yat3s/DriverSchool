package com.drivingevaluate.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.drivingevaluate.R;
import com.drivingevaluate.adapter.MerchantAdapter;
import com.drivingevaluate.model.Merchant;
import com.drivingevaluate.net.GetMerchantListRequester;
import com.drivingevaluate.net.component.RequestErrorHandler;
import com.drivingevaluate.ui.LoginActivity;
import com.drivingevaluate.ui.MerchantDetailActivity;
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

public class MerchantFragment extends Yat3sFragment implements OnClickListener {
    private LinearLayout llRating, llDistance, llPrice, llMember;
    private ImageView imgRating, imgDistance, imgPrice, imgMember;

    private View rootView;
    @Bind(R.id.merchant_rv)
    UltimateRecyclerView merchantRv;
    private List<Merchant> merchants = new ArrayList<>();
    private String sort = "";
    private int page = 1;
    private int loadType = 0;
    private MerchantAdapter merchantAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_merchant, viewGroup, false);
        ButterKnife.bind(this, rootView);
        initView();
        initEvent();
        getData();
        return rootView;
    }

    private void initView() {
        llRating = (LinearLayout) rootView.findViewById(R.id.ll_orderByRating);
        llDistance = (LinearLayout) rootView.findViewById(R.id.ll_orderByDistance);
        llPrice = (LinearLayout) rootView.findViewById(R.id.ll_orderByPrice);
        llMember = (LinearLayout) rootView.findViewById(R.id.ll_orderByMember);

        imgRating = (ImageView) rootView.findViewById(R.id.img_rating);
        imgDistance = (ImageView) rootView.findViewById(R.id.img_distance);
        imgPrice = (ImageView) rootView.findViewById(R.id.img_price);
        imgMember = (ImageView) rootView.findViewById(R.id.img_member);

        merchantAdapter = new MerchantAdapter(getActivity(), merchants);
        merchantRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        merchantRv.setAdapter(merchantAdapter);

        merchantRv.enableLoadmore();
    }

    private void initEvent() {
        llRating.setOnClickListener(this);
        llDistance.setOnClickListener(this);
        llPrice.setOnClickListener(this);
        llMember.setOnClickListener(this);

        merchantRv.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                merchants.clear();
                loadType = 0;
                getData();
            }
        });

        merchantRv.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int i, int i1) {
                page++;
                loadType = 1;
                getData();
            }
        });

        merchantAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                if (!SharedPreferencesUtils.contains(getActivity(), "token")) {
                    startActivity(LoginActivity.class);
                } else {
                    Bundle paramBundle = new Bundle();
                    paramBundle.putInt("merchantId", merchants.get(position).getSid());
                    startActivity(MerchantDetailActivity.class, paramBundle);
                }
            }
        });
    }

    private void getData() {
        Callback<List<Merchant>> callback = new Callback<List<Merchant>>() {
            @Override
            public void success(List<Merchant> merchantList, Response response) {
                merchants.addAll(merchantList);
                if (loadType == 0){
                    merchantRv.setRefreshing(false);
                    merchantRv.setAdapter(merchantAdapter);
                }else {
                    merchantAdapter.notifyDataSetChanged();
                }
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
        Map<String,Object> parameter = new HashMap<>();
        parameter.put("pageNo",page);
        parameter.put("orderBy",sort);
        if (mApplication.myLl != null){
            parameter.put("lat",mApplication.myLl.latitude);
            parameter.put("lon", mApplication.myLl.longitude);
        }
        GetMerchantListRequester getMerchantListRequester = new GetMerchantListRequester(callback,parameter);
        getMerchantListRequester.request();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_orderByRating:
                resetIcon();
                imgRating.setImageResource(R.mipmap.ic_order_rating_pressed);
                setSort("5");
                break;
            case R.id.ll_orderByDistance:
                resetIcon();
                imgDistance.setImageResource(R.mipmap.ic_order_distance_pressed);
                setSort("1");
                break;
            case R.id.ll_orderByPrice:
                resetIcon();
                imgPrice.setImageResource(R.mipmap.ic_order_price_pressed);
                setSort("4");
                break;
            case R.id.ll_orderByMember:
                resetIcon();
                imgMember.setImageResource(R.mipmap.ic_order_member_pressed);
                setSort("3");
                break;
            default:
                break;
        }
    }

    private void setSort(String sort) {
        page = 1;
        merchants.clear();
        this.sort = sort;
        loadType = 0;
        getData();
    }

    /**
     * reset img to dark(normal)
     */
    private void resetIcon() {
        imgRating.setImageResource(R.mipmap.ic_order_rating_normal);
        imgDistance.setImageResource(R.mipmap.ic_order_distance_normal);
        imgPrice.setImageResource(R.mipmap.ic_order_price_normal);
        imgMember.setImageResource(R.mipmap.ic_order_member_normal);
    }
}
