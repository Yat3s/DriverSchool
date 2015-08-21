package com.drivingevaluate.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.config.AppConf;
import com.drivingevaluate.model.User;
import com.drivingevaluate.net.GetUserInfoRequester;
import com.drivingevaluate.net.UpdateUserInfoRequester;
import com.drivingevaluate.ui.base.Yat3sActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserInfoActivity extends Yat3sActivity{
    private User user;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.avatar_user_layout) RelativeLayout avatarLayout;
    @Bind(R.id.avatar_user_img) ImageView avatarImg;
    @Bind(R.id.sign_et) EditText signEt;
    @Bind(R.id.name_et) EditText nameEt;
    @Bind(R.id.phone_tv) TextView phoneTv;
    @Bind(R.id.gender_tv) TextView genderTv;
    @Bind(R.id.status_tv) TextView statusTv;

    private String avatarUrl;
    private int gender = 1,status = 1;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        setToolbarWithNavigation(toolbar, "个人信息");
        initView();
        getDate();
    }

    private void getDate() {
        Callback<User> callback = new Callback<User>() {
            @Override
            public void success(User remoteUser, Response response) {
                user = remoteUser;
                nameEt.setText(user.getUserName());
                phoneTv.setText(user.getAccount());
                signEt.setText(user.getSign());
                genderTv.setText(user.getSex());
                statusTv.setText(user.getStatus()+"");
                avatarUrl = user.getHeadPath();
                loadImg(avatarImg,user.getHeadPath());
            }

            @Override
            public void failure(RetrofitError error) {
                showShortToast(error.getMessage());
            }
        };
        GetUserInfoRequester getUserInfoRequester = new GetUserInfoRequester(callback, AppConf.USER_ID);
        getUserInfoRequester.request();
    }

    private void initView() {


    }

    @OnClick(R.id.avatar_user_layout)
    void changeAvatar(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.modify_pwd_layout)
    void modifyPwd(){
        startActivity(AlterPwdActivity.class);
    }

    @OnClick(R.id.save_user_info_btn)
    void saveUserInfo(){
        Callback<String > callback = new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                showShortToast("跟新成功");
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                showShortToast(error.getMessage());
            }
        };
        Map<String,Object> param = new HashMap<>();
        param.put("userId",AppConf.USER_ID);
        param.put("nickName",nameEt.getText().toString());
        param.put("sex",gender);
        param.put("sign",signEt.getText().toString());
        param.put("status",status);
        param.put("imgPath",avatarUrl);
        UpdateUserInfoRequester updateUserInfoRequester = new UpdateUserInfoRequester(callback,param);
        updateUserInfoRequester.request();
    }
}
