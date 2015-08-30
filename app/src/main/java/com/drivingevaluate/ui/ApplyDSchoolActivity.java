package com.drivingevaluate.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.drivingevaluate.R;
import com.drivingevaluate.api.JsonResolveUtils;
import com.drivingevaluate.app.App;
import com.drivingevaluate.config.Constants;
import com.drivingevaluate.model.Order;
import com.drivingevaluate.model.PayResult;
import com.drivingevaluate.net.CommitOrderRequester;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.util.AppMethod;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ApplyDSchoolActivity extends Yat3sActivity implements OnClickListener{
    private TextView tvSelectCoach,addressTv,prePayTv;
    private EditText etName,etIdNo,etTel;
    private Button btnCommitOrder;
    private int coachId;
    private double prePay;

    private static final int SDK_PAY_FLAG = 2;
    private static final int SDK_CHECK_FLAG = 3;

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SDK_CHECK_FLAG://检查是否存在已认证客户端
                    boolean flag = (Boolean)msg.obj;
                    if(flag){//存在
                        //提交订单
                        commitOrder();
                    }else{//不存在
                        showShortToast("请先安装支付宝客户端");
                    }
                    break;
                case SDK_PAY_FLAG://客户单支付结果
                    PayResult payResult = new PayResult((String) msg.obj);
                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    //System.out.print(msg.obj);
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    //System.out.print(resultStatus);
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (resultStatus.equals("9000")) {//支付成功
                        //CommitOrderActivity.this.showCustomToast("支付成功");
                        //发送支付成功请求
                        Log.e("yat3s","resInfo"+resultInfo);
                        JsonResolveUtils.notifyPayService(resultInfo);
                        finish();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (resultStatus.equals("8000")) {
                            showShortToast("支付结果确认中");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            showShortToast("支付失败");
                        }
                    }
                    break;
                default:
                    break;
            }
        };
    };
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);

        ButterKnife.bind(this);
        setToolbarWithNavigation(toolbar, "报名信息");

        initView();
        initEvent();

        getData();
    }
    private void getData() {
        Bundle bundle = getIntent().getExtras();
        tvSelectCoach.setText("教练:"+bundle.getString("coachName")+"("+bundle.getString("coachSubject")+")");
        coachId = bundle.getInt("coachId");
        prePay = bundle.getDouble("prePay");

        prePayTv.setText(prePay + "元");
    }
    private void initEvent() {
        tvSelectCoach.setOnClickListener(this);
        btnCommitOrder.setOnClickListener(this);
    }
    private void initView() {

        etName = (EditText) findViewById(R.id.et_name);
        etIdNo = (EditText) findViewById(R.id.et_idNo);
        etTel = (EditText) findViewById(R.id.et_tel);

        addressTv = (TextView) findViewById(R.id.address_tv);
        tvSelectCoach = (TextView) findViewById(R.id.tv_selectCoach);
        prePayTv = (TextView) findViewById(R.id.prePay_tv);

        btnCommitOrder = (Button) findViewById(R.id.btn_commitOrder);

    }

    /**
     * 提交订单给服务器
     */
    private void commitOrder(){
        Callback<Order> callback = new Callback<Order>() {
            @Override
            public void success(final Order order, Response response) {
                //传递给支付宝接口处理,必须异步调用
                Log.e("Yat3s",order.toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(ApplyDSchoolActivity.this);
                        // 调用支付接口，获取支付结果
                        String result = alipay.pay(order.getResult());
                        AppMethod.sendMessage(handler, result, SDK_PAY_FLAG);
                    }
                }).start();
            }

            @Override
            public void failure(RetrofitError error) {
                showShortToast("提交订单失败");
            }
        };

        Map<String,Object> param =new HashMap<>();
        param.put(Constants.GOODS_ID, coachId);
        param.put(Constants.USER_ID, App.getUserId());
        param.put(Constants.ADDRESS, addressTv.getText().toString());
        param.put(Constants.REAL_RName, etName.getText().toString());
        param.put(Constants.ID_CARD_NO, etIdNo.getText().toString());
        param.put(Constants.TEL_NO, this.etTel.getText().toString());
        CommitOrderRequester commitOrderRequester = new CommitOrderRequester(callback,param);
        commitOrderRequester.request();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commitOrder:
                inputFilter();
                break;
            default:
                break;
        }
    }

    private void inputFilter() {
        if (tvSelectCoach.getText().toString().isEmpty()) {
            showShortToast("请选择教练");
        }
        else if (etName.getText().toString().isEmpty()) {
            showShortToast("请输入联系人姓名");
        }
        else if (etIdNo.getText().toString().isEmpty() ||etIdNo.getText().toString().length() !=18) {
            showShortToast("请输入正确身份证号");
        }
        else if (etTel.getText().toString().isEmpty()||etTel.getText().toString().length() !=11) {
            showShortToast("请输入正确联系人电话");
        }
        else if (addressTv.getText().toString().isEmpty()) {
            showShortToast("请填写联系地址");
        }
        else {
            showShortToast("正在检查支付宝账户...");
            checkAlipay();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        addressTv.setText(data.getStringExtra("schoolName"));
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * checkAlipay whether the device has authentication alipay account.
     * 查询终端设备是否存在支付宝认证账户
     *
     */
    public void checkAlipay() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask payTask = new PayTask(ApplyDSchoolActivity.this);
                // 调用查询接口，获取查询结果
                boolean isExist = payTask.checkAccountIfExist();
                AppMethod.sendMessage(handler, isExist, SDK_CHECK_FLAG);
            }
        }).start();
    }
}
