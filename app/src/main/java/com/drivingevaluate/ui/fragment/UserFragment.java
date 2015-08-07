package com.drivingevaluate.ui.fragment;


import com.drivingevaluate.R;
import com.drivingevaluate.config.AppConf;
import com.drivingevaluate.net.GetUserInfoRequester;
import com.drivingevaluate.ui.SplashActivity;
import com.drivingevaluate.ui.base.Yat3sFragment;
import com.drivingevaluate.ui.AboutActivity;
import com.drivingevaluate.ui.LoginActivity;
import com.drivingevaluate.ui.MsgActivity;
import com.drivingevaluate.ui.MyOrderActivity;
import com.drivingevaluate.ui.UserInfoActivity;
import com.drivingevaluate.api.Api;
import com.drivingevaluate.config.VersionManager;
import com.drivingevaluate.model.User;
import com.drivingevaluate.util.AppMethod;
import com.drivingevaluate.util.SharedPreferencesUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class UserFragment extends Yat3sFragment implements OnClickListener
{
    private LinearLayout llUserInfo;
    private RelativeLayout rlUpdate,rlAbout,rlMyOrder;
    private Button btnLoginout,btnMsg;
    private TextView tvName;
    private View root;

    private User mUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.fragment_user, container, false);
        initView();
        initEvent();
        getUserInfo();
        return root;
    }

    private void getUserInfo() {
        Callback<User> callback = new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                mUser = user;
                tvName.setText(user.getAccount());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        };
        AppConf.USER_ID =(int)SharedPreferencesUtils.get(getActivity(), "userId", -1);
        GetUserInfoRequester getUserInfoRequester = new GetUserInfoRequester(callback, AppConf.USER_ID);
        getUserInfoRequester.request();
    }
    private void initView() {
        llUserInfo = (LinearLayout) root.findViewById(R.id.ll_userInfo);
        rlUpdate = (RelativeLayout) root.findViewById(R.id.rl_update);
        rlAbout = (RelativeLayout) root.findViewById(R.id.rl_about);
        rlMyOrder = (RelativeLayout) root.findViewById(R.id.rl_myOrder);

        tvName = (TextView) root.findViewById(R.id.tv_name);
        btnLoginout = (Button) root.findViewById(R.id.btn_loginout);
        btnMsg = (Button) root.findViewById(R.id.btn_msg);


    }
    private void initEvent() {
        llUserInfo.setOnClickListener(this);
        rlUpdate.setOnClickListener(this);
        rlAbout.setOnClickListener(this);
        rlMyOrder.setOnClickListener(this);

        btnLoginout.setOnClickListener(this);
        btnMsg.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_loginout:
                Intent loginIntent = new Intent(getActivity(),LoginActivity.class);
                AppMethod.Loginout();
                startActivity(loginIntent);
                getActivity().finish();
                break;
            case R.id.ll_userInfo:
                Intent userInfoIntent = new Intent(getActivity(),UserInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", mUser);
                userInfoIntent.putExtras(bundle);
                startActivity(userInfoIntent);
                break;
            case R.id.rl_update:
                VersionManager versionManager = new VersionManager(getActivity(),1);
                versionManager.checkUpdate();
                break;
            case R.id.rl_myOrder:
                startActivity(MyOrderActivity.class);
                break;
            case R.id.rl_about:
                startActivity(AboutActivity.class);
                break;
            case R.id.btn_msg:
                startActivity(MsgActivity.class);
                break;
            default:
                break;
        }
    }
}
