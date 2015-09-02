package com.drivingevaluate.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.balysv.materialripple.MaterialRippleLayout;
import com.cocosw.bottomsheet.BottomSheet;
import com.drivingevaluate.R;
import com.drivingevaluate.adapter.HotMerchantAdapter;
import com.drivingevaluate.adapter.MomentAdapter2;
import com.drivingevaluate.model.Advertisement;
import com.drivingevaluate.model.CustomerService;
import com.drivingevaluate.model.Merchant;
import com.drivingevaluate.model.Moment;
import com.drivingevaluate.net.AdRequester;
import com.drivingevaluate.net.GetCustomerServiceRequester;
import com.drivingevaluate.net.GetMerchantListRequester;
import com.drivingevaluate.net.GetMomentListRequester;
import com.drivingevaluate.ui.GetMoneyActivity;
import com.drivingevaluate.ui.LoginActivity;
import com.drivingevaluate.ui.MainActivity;
import com.drivingevaluate.ui.MerchantDetailActivity;
import com.drivingevaluate.ui.MomentActivity;
import com.drivingevaluate.ui.base.Yat3sFragment;
import com.drivingevaluate.util.MyUtil;
import com.drivingevaluate.util.SharedPreferencesUtils;
import com.drivingevaluate.view.FullyLinearLayoutManager;
import com.drivingevaluate.view.SlideShowView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Yat3s on 8/27/15.
 * Email:hawkoyates@gmail.com
 */
public class HomeFragment extends Yat3sFragment {
    private View rootView;
    @Bind(R.id.ad_banner)
    SlideShowView adBanner;
    @Bind(R.id.merchant_home_rv)
    RecyclerView merchantRv;
    @Bind(R.id.moment_home_rv)
    RecyclerView momentRv;
    @Bind(R.id.merchant_layout)
    LinearLayout merchantLayout;
    @Bind(R.id.moment_layout)
    LinearLayout momentLayout;
    @Bind(R.id.money_layout)
    LinearLayout moneyLayout;
    @Bind(R.id.service_layout)
    LinearLayout serviceLayout;
    private List<Advertisement> ads;
    private List<Merchant> merchants = new ArrayList<>();
    private List<Moment> moments = new ArrayList<>();
    private HotMerchantAdapter hotMerchantAdapter;
    private MomentAdapter2 momentAdapter;
    private CustomerService mCustomerService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);

        initData();
        initEvent();
        getAdData();
        getMerchantData();

        getCustomerService();
        return rootView;
    }

    private void initEvent() {
        hotMerchantAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!SharedPreferencesUtils.contains(getActivity(), "token")) {
                    startActivity(LoginActivity.class);
                } else {
                    Bundle paramBundle = new Bundle();
                    paramBundle.putInt("merchantId", merchants.get(position).getSid());
                    startActivity(MerchantDetailActivity.class, paramBundle);
                }
            }
        });

        momentAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(MomentActivity.class);
            }
        });
    }

    private void getMomentData() {
        Callback<List<Moment>> callback = new Callback<List<Moment>>() {
            @Override
            public void success(List<Moment> momentList, Response response) {
                moments.addAll(momentList);
                momentAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
//                RequestErrorHandler requestErrorHandler = new RequestErrorHandler(getActivity());
//                try {
//                    requestErrorHandler.handError(error);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        };
        Map<String, Object> param = new HashMap<>();
        param.put("timestamp", System.currentTimeMillis());
        param.put("pageSize", 2);
        GetMomentListRequester getMomentListRequester = new GetMomentListRequester(callback, param);
        getMomentListRequester.request();
    }

    private void initData() {
        merchantRv.setFocusable(false);

        MaterialRippleLayout.on(merchantLayout)
                .rippleColor(getResources().getColor(R.color.md_blue_200))
                .rippleDuration(400)
                .rippleAlpha(0.6f)
                .create();
        MaterialRippleLayout.on(momentLayout)
                .rippleColor(getResources().getColor(R.color.md_blue_200))
                .rippleDuration(400)
                .rippleAlpha(0.6f)
                .create();
        MaterialRippleLayout.on(moneyLayout)
                .rippleColor(getResources().getColor(R.color.md_red_300))
                .rippleDuration(400)
                .rippleAlpha(0.6f)
                .create();
        MaterialRippleLayout.on(serviceLayout)
                .rippleColor(getResources().getColor(R.color.md_blue_300))
                .rippleDuration(400)
                .rippleAlpha(0.6f)
                .rippleOverlay(true)
                .create();

        merchantRv.setLayoutManager(new FullyLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        hotMerchantAdapter = new HotMerchantAdapter(getActivity(), merchants);
        merchantRv.setAdapter(hotMerchantAdapter);


        momentRv.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
        momentAdapter = new MomentAdapter2(getActivity(), moments);
        momentRv.setAdapter(momentAdapter);

        adBanner.setFocusable(false);
    }

    private void getMerchantData() {
        Callback<List<Merchant>> callback = new Callback<List<Merchant>>() {
            @Override
            public void success(List<Merchant> merchantList, Response response) {
                merchants.addAll(merchantList);
                hotMerchantAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
//                RequestErrorHandler requestErrorHandler = new RequestErrorHandler(getActivity());
//                try {
//                    requestErrorHandler.handError(error);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        };
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("orderBy", 3);
        GetMerchantListRequester getMerchantListRequester = new GetMerchantListRequester(callback, parameter);
        getMerchantListRequester.request();
    }

    private void getAdData() {
        Callback<List<Advertisement>> callback = new Callback<List<Advertisement>>() {
            @Override
            public void success(List<Advertisement> advertisements, Response response) {
                ads = advertisements;
                for (int i = 0; i < ads.size(); i++) {
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    MyUtil.loadImg(imageView, ads.get(i).getImagePath());
                }
                adBanner.clickable(true);
                adBanner.startAds(ads);
            }

            @Override
            public void failure(RetrofitError error) {
//                RequestErrorHandler requestErrorHandler = new RequestErrorHandler(getActivity());
//                try {
//                    requestErrorHandler.handError(error);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        };
        AdRequester adRequester = new AdRequester(callback);
        adRequester.request();
    }

    @OnClick({R.id.merchant_layout, R.id.moment_layout, R.id.money_layout, R.id.service_layout})
    void topOnClick(View v) {
        switch (v.getId()) {
            case R.id.merchant_layout:
                ((MainActivity) getActivity()).setSelect(1);
                break;
            case R.id.moment_layout:
                startActivity(MomentActivity.class);
                break;
            case R.id.money_layout:
                startActivity(GetMoneyActivity.class);
                break;
            case R.id.service_layout:
                new BottomSheet.Builder(getActivity()).title("选择咨询方式").sheet(R.menu.menu_consult).icon(R.mipmap.ic_servicer).listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case R.id.call:
                                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mCustomerService.getPhone())));
                                break;
                            case R.id.qq:
                                String url11 = "mqqwpa://im/chat?chat_type=wpa&uin=" + mCustomerService.getQq() + "&version=1";
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url11)));
                                break;
                        }
                    }
                }).show();
                break;
        }
    }

    private void getCustomerService() {
        Callback<CustomerService> callback = new Callback<CustomerService>() {
            @Override
            public void success(CustomerService customerService, Response response) {
                mCustomerService = customerService;
            }

            @Override
            public void failure(RetrofitError error) {
//                RequestErrorHandler requestErrorHandler = new RequestErrorHandler(getActivity());
//                try {
//                    requestErrorHandler.handError(error);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        };
        GetCustomerServiceRequester getCustomerServiceRequester = new GetCustomerServiceRequester(callback);
        getCustomerServiceRequester.request();
    }

    @Override
    public void onResume() {
        super.onResume();
        moments.clear();
        getMomentData();
    }
}
