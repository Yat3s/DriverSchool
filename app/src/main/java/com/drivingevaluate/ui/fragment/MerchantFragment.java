package com.drivingevaluate.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.adapter.MerchantAdapter;
import com.drivingevaluate.model.Merchant;
import com.drivingevaluate.ui.base.Yat3sFragment;
import com.drivingevaluate.ui.MerchantInfoActivity;
import com.drivingevaluate.ui.SelectCityActivity;
import com.drivingevaluate.net.GetMerchantListRequester;
import com.drivingevaluate.view.RefreshLayout;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MerchantFragment extends Yat3sFragment implements OnClickListener {
    private LinearLayout llRating, llDistance, llPrice, llMember;
    private ImageView imgRating, imgDistance, imgPrice, imgMember;

    private EditText searchEditText;
    private Button btnSelectCity;
    private TextView searchTextView;
    private View rootView;
    private RefreshLayout merchantRefresh;
    private ListView merchantLv;
    private List<Merchant> merchants = new ArrayList<Merchant>();
    private String sort = "4";
    private int page = 1;
    private int loadType = 0;
    private MerchantAdapter merchantAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_merchant, viewGroup, false);
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

        btnSelectCity = (Button) rootView.findViewById(R.id.btn_city);

        searchTextView = (TextView) rootView.findViewById(R.id.tvSearch);


        merchantLv = (ListView) rootView.findViewById(R.id.dschool_lv);
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_header,null);
        merchantLv.addHeaderView(headerView,null,false);
        merchantRefresh = (RefreshLayout) rootView.findViewById(R.id.dschool_fresh);
        merchantRefresh.setColorSchemeResources(R.color.md_blue_300,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                R.color.md_grey_500);
        merchantAdapter = new MerchantAdapter(merchants,getActivity());
        merchantLv.setAdapter(merchantAdapter);
    }

    private void initEvent() {
        llRating.setOnClickListener(this);
        llDistance.setOnClickListener(this);
        llPrice.setOnClickListener(this);
        llMember.setOnClickListener(this);
        btnSelectCity.setOnClickListener(this);
        searchTextView.setOnClickListener(this);

        merchantRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                loadType = 0;
                getData();
            }
        });

        merchantRefresh.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                loadType = 1;
                page++;
                getData();
            }
        });


        merchantLv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                Bundle paramBundle = new Bundle();
                paramBundle.putInt("merchantId",merchants.get(position-1).getSid());
                startActivity(MerchantInfoActivity.class, paramBundle);
            }
        });
    }

    private void getData() {
        Callback<List<Merchant>> callback = new Callback<List<Merchant>>() {
            @Override
            public void success(List<Merchant> merchantList, Response response) {
                if (loadType == 0){
                    merchants.clear();
                }else {
                    merchantRefresh.setLoading(false);
                }
                merchants.addAll(merchantList);
                merchantAdapter.notifyDataSetChanged();
                merchantRefresh.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
            }
        };
        Map<String,Object> parameter = new HashMap<>();
        parameter.put("pageNo",page);
        parameter.put("orderBy",sort);
        parameter.put("lat",mApplication.myLl.latitude);
        parameter.put("lon", mApplication.myLl.longitude);
        GetMerchantListRequester getMerchantListRequester = new GetMerchantListRequester(callback,parameter);
        getMerchantListRequester.request();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_orderByRating:
                resetImgs();
                imgRating.setImageResource(R.mipmap.ic_order_rating_pressed);
                setSort("5");
                break;
            case R.id.ll_orderByDistance:
                resetImgs();
                imgDistance.setImageResource(R.mipmap.ic_order_distance_pressed);
                setSort("1");
                break;
            case R.id.ll_orderByPrice:
                resetImgs();
                imgPrice.setImageResource(R.mipmap.ic_order_price_pressed);
                setSort("2");
                break;
            case R.id.ll_orderByMember:
                resetImgs();
                imgMember.setImageResource(R.mipmap.ic_order_member_pressed);
                setSort("3");
                break;
            case R.id.btn_city:
                startActivity(SelectCityActivity.class);
                break;
            case R.id.tvSearch:
                searchMerchantDialog();
                break;
            case R.id.btnSearch:
                searchMerchant();
                break;
            default:
                break;
        }
    }

    private void setSort(String sort) {
        page = 1;
        this.sort = sort;
        loadType = 0;
        merchantRefresh.setRefreshing(true);
        getData();
    }

    /**
     * reset img to dark(normal)
     */
    private void resetImgs() {
        imgRating.setImageResource(R.mipmap.ic_order_rating_normal);
        imgDistance.setImageResource(R.mipmap.ic_order_distance_normal);
        imgPrice.setImageResource(R.mipmap.ic_order_price_normal);
        imgMember.setImageResource(R.mipmap.ic_order_member_normal);
    }


    /**
     * search dialog
     */
    private void searchMerchantDialog() {
        View view = LayoutInflater.from(getActivity()).inflate(
                R.layout.diaglog_search, null);
        LinearLayout layout = (LinearLayout) view
                .findViewById(R.id.dialog_search);
        searchEditText = (EditText) view.findViewById(R.id.et_search);
        Button searchButton = (Button) view.findViewById(R.id.btnSearch);
        Dialog searchDialog = new Dialog(getActivity(), R.style.loading_dialog);
        searchDialog.setContentView(layout);// 设置布局
        Window dialogWindow = searchDialog.getWindow();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.TOP);
        searchEditText.setFocusable(true);
        searchEditText.setFocusableInTouchMode(true);
        searchEditText.requestFocus();
        searchDialog.show();
        searchButton.setOnClickListener(this);
    }

    private void searchMerchant() {
//        JsonResolve.findSchoolByName(searchEditText.getText().toString(),
//                handler);
    }
}
