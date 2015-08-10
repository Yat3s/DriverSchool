package com.drivingevaluate.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.drivingevaluate.app.DEApplication;
import com.drivingevaluate.R;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.util.MyUtil;
import com.drivingevaluate.view.BackTitleBar;

public class MerchantMapActivity extends Yat3sActivity implements OnGetRoutePlanResultListener {
    private TextView tvRoutePlan;

    MapView mMapView = null;    // 地图View
    BaiduMap mBaiduMap = null;
    //搜索相关
    RoutePlanSearch mSearch = null;
    private LatLng myLl,endLatLng;
    private TransitRouteResult transitRouteResult;
    private Dialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BackTitleBar titleBar = new BackTitleBar(this);
        setContentView(R.layout.activity_merchant_map);
        titleBar.setTitle("路线");
        DEApplication.getInstance().initBaiduLocClient();
        initView();
        getData();
        startRoutePlan();
    }

    private void getData() {
        endLatLng = new LatLng(Double.parseDouble(getIntent().getExtras().getString("lat")),Double.parseDouble(getIntent().getExtras().getString("lng")));
    }

    private void startRoutePlan() {
        PlanNode stNode;
        PlanNode enNode;
        // 设置起终点信息，对于tranist search 来说，城市名无意义
        stNode = PlanNode.withLocation(myLl);
        enNode = PlanNode.withLocation(endLatLng);
        mSearch.transitSearch((new TransitRoutePlanOption()).from(stNode)
                .city("南昌").to(enNode));
        // 实际使用中请对起点终点城市进行正确的设定
    }

    private void initView() {
        myLl = mApplication.myLl;

        tvRoutePlan = (TextView) findViewById(R.id.tv_routePlan);
        loading = MyUtil.createLoadingDialog(MerchantMapActivity.this, "正在加载中");
        loading.show();

        mMapView = (MapView) findViewById(R.id.map);
        mBaiduMap = mMapView.getMap();
        mMapView.showZoomControls(false);
        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult arg0) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(MerchantMapActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            //result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            transitRouteResult = result;
            TransitRouteOverlay overlay = new TransitRouteOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
//	            routeOverlay = overlay;
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();

            setData();
        }
    }

    private void setData() {
        String res ="";
        for (int i = 0; i < transitRouteResult.getRouteLines().get(0).getAllStep().size(); i++) {
            res = res + transitRouteResult.getRouteLines().get(0).getAllStep().get(i).getInstructions()+"\n";
        }
        tvRoutePlan.setText(res);
        loading.dismiss();
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult arg0) {

    }

}
