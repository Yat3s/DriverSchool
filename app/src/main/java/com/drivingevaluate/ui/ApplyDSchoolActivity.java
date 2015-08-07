package com.drivingevaluate.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.drivingevaluate.R;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.api.JsonResolveUtils;
import com.drivingevaluate.config.Constants;
import com.drivingevaluate.model.Merchant;
import com.drivingevaluate.model.PayResult;
import com.drivingevaluate.util.AppMethod;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ApplyDSchoolActivity extends Yat3sActivity implements OnClickListener{
    private TextView tvTip,tvSelectCoach,etSchool;
    private EditText etName,etIdNo,etTel;
    private Button btnCommitOrder;
    private LinearLayout layoutSelectSchool;

    private String reString;
    private List<Merchant> list;
    private String sid;

    private static final int SDK_PAY_FLAG = 2;
    private static final int SDK_CHECK_FLAG = 3;

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constants.CODE_COMMIT_ORDER://请求订单
                    if(msg.obj!=null){
                        String str = (String)msg.obj;
                        System.out.println(str);
                        JSONObject jobj = JSON.parseObject(str);
                        final String signedResult = jobj.getString(Constants.RESULT);
                        //传递给支付宝接口处理
                        //必须异步调用
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 构造PayTask 对象
                                PayTask alipay = new PayTask(ApplyDSchoolActivity.this);
                                // 调用支付接口，获取支付结果
                                String result = alipay.pay(signedResult);
                                AppMethod.sendMessage(handler, result, SDK_PAY_FLAG);
                            }
                        }).start();
                    }else{
                        showShortToast("提交订单失败");
                    }
                    break;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackTitleBar();
        setContentView(R.layout.activity_apply);

        initView();
        initEvent();

        getData();
    }
    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tvSelectCoach.setText(bundle.getString("coachName"));
            tvTip.setText(Html.fromHtml("您正在选购  <font color=blue>"+bundle.getString("sName")+"</font> 课程"));
            sid = bundle.getString("sid");
        }
        sid = getIntent().getStringExtra("sid");
    }
    private void initEvent() {
        tvSelectCoach.setOnClickListener(this);
        btnCommitOrder.setOnClickListener(this);
        layoutSelectSchool.setOnClickListener(this);
    }
    private void initView() {
        setTitleBarTitle("报名信息");

        etName = (EditText) findViewById(R.id.et_name);
        etIdNo = (EditText) findViewById(R.id.et_idNo);
        etTel = (EditText) findViewById(R.id.et_tel);

        etSchool = (TextView) findViewById(R.id.et_school);
        tvSelectCoach = (TextView) findViewById(R.id.tv_selectCoach);
        tvTip = (TextView) findViewById(R.id.tv_tip);

        layoutSelectSchool = (LinearLayout) findViewById(R.id.layout_selectSchool);

        btnCommitOrder = (Button) findViewById(R.id.btn_commitOrder);

        tvTip.setText(Html.fromHtml("您正在选购  <font color=blue>"+getIntent().getStringExtra("sName")+"</font> 课程"));
    }


    private void commitOrder(){
        Map<String,Object> param =new HashMap<String, Object>();
        if(AppMethod.getCurUserId()==null) startActivity(LoginActivity.class);
        param.put(Constants.GOODS_ID, sid);
        param.put(Constants.USER_ID, AppMethod.getCurUserId());
        param.put(Constants.ADDRESS, etSchool.getText().toString());
        param.put(Constants.REAL_RName, etName.getText().toString());
        param.put(Constants.ID_CARD_NO, etIdNo.getText().toString());
        param.put(Constants.TEL_NO, this.etTel.getText().toString());
        JsonResolveUtils.commitBuyOrder(param, handler);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_selectCoach:
                Intent toSelectCoachIntent = new Intent(ApplyDSchoolActivity.this,ResultCoachActivity.class);
                toSelectCoachIntent.putExtra("sid", sid);
                startActivityForResult(toSelectCoachIntent, 0);
                break;
            case R.id.btn_commitOrder:
                OrderFilter();
                break;
            case R.id.layout_selectSchool:
                Intent schoolIntent = new Intent(ApplyDSchoolActivity.this,ResultSchoolActivity.class);
                startActivityForResult(schoolIntent, 1);
                break;
            default:
                break;
        }
    }

    private void OrderFilter() {
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
        else if (etSchool.getText().toString().isEmpty()) {
            showShortToast("请选择学校");
        }
        else {
            check();
        }
    }

    /**
     * check whether the device has authentication alipay account.
     * 查询终端设备是否存在支付宝认证账户
     *
     */
    public void check() {
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 0 && resultCode == 0) {
            String coachName = data.getStringExtra("coachName");
            String className = data.getStringExtra("class");
            tvSelectCoach.setText(coachName+" ("+className+")");
        }
        if (requestCode == 1 && resultCode == 1) {
            etSchool.setText(data.getStringExtra("name"));
        }
    }
}
