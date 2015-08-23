package com.drivingevaluate.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.drivingevaluate.R;
import com.drivingevaluate.config.AppConf;
import com.drivingevaluate.net.ModifyPwdRequester;
import com.drivingevaluate.ui.base.Yat3sActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AlterPwdActivity extends Yat3sActivity{
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.oldPwd_et) EditText oldPwdEt;
    @Bind(R.id.newPwd_et) EditText newPwdEt;
    @Bind(R.id.reNewPwd_et) EditText reNewPwdEt;

    private String oldPwd,newPwd,reNewPwd;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_alterpwd);

        ButterKnife.bind(this);
        setToolbarWithNavigation(toolbar, "修改密码");

        initView();
    }

    private void initView() {
    }

    @OnClick(R.id.commit_btn)
    void commit(){
        oldPwd = oldPwdEt.getText().toString();
        newPwd = newPwdEt.getText().toString();
        reNewPwd = reNewPwdEt.getText().toString();

        if (oldPwd.isEmpty() || newPwd.isEmpty() || reNewPwd.isEmpty()){
            showShortToast("新密码或者旧密码不能为空");
            return;
        }
        if (!newPwd.equals(reNewPwd)){
            showShortToast("两次输入的密码不一致");
            return;
        }
        Callback<String> callback = new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                showShortToast("修改成功");
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                if (error.getKind() == RetrofitError.Kind.NETWORK) {
                    showShortToast("网络连接异常");
                    return;
                }
                int code = error.getResponse().getStatus();
                switch (code) {
                    case 401:
                        showShortToast("旧密码错误");
                        break;
                    case 404:
                        showShortToast("该账号不存在");
                        break;
                    case 500:
                        showShortToast("服务器内部错误");
                        break;
                    default:
                        showShortToast("未知错误");
                        break;
                }
            }
        };
        Map<String ,Object> param = new HashMap<>();
        param.put("userId", AppConf.USER_ID);
        param.put("oldPwd",oldPwd);
        param.put("newPwd", newPwd);
        ModifyPwdRequester modifyPwdRequester = new ModifyPwdRequester(callback, param);
        modifyPwdRequester.request();
    }
}
