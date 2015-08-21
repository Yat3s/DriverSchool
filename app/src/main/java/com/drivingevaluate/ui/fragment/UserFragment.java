package com.drivingevaluate.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.drivingevaluate.R;
import com.drivingevaluate.config.AppConf;
import com.drivingevaluate.config.VersionManager;
import com.drivingevaluate.model.User;
import com.drivingevaluate.net.GetUserInfoRequester;
import com.drivingevaluate.ui.AboutActivity;
import com.drivingevaluate.ui.ConsultActivity;
import com.drivingevaluate.ui.LoginActivity;
import com.drivingevaluate.ui.UserInfoActivity;
import com.drivingevaluate.ui.base.Yat3sFragment;
import com.drivingevaluate.util.SharedPreferencesUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class UserFragment extends Yat3sFragment implements OnClickListener
{
    private LinearLayout infoLayout,orderLayout,updateLayout,feedbackLayout,aboutLayout;
    private Button loginOutBtn;
    private TextView nameTv,phoneTv;
    private View rootView;
    @Bind(R.id.avatar_user_img)
    ImageView avatarImg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        rootView = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this,rootView);
        initView();
        initEvent();

        return rootView;
    }

    private void getUserInfo() {
//        if (SharedPreferencesUtils.contains(getActivity(),"token")){
//            nameTv.setText(SharedPreferencesUtils.get(getActivity(),"userName","").toString());
//            phoneTv.setText(SharedPreferencesUtils.get(getActivity(),"phone","").toString());
//            loginOutBtn.setVisibility(View.VISIBLE);
//        }
//        else {
//            loginOutBtn.setVisibility(View.INVISIBLE);
//            nameTv.setText("点击登录");
//            phoneTv.setText("登录后查看更多优惠信息");
//        }

        Callback<User> callback = new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                nameTv.setText(user.getUserName());
                phoneTv.setText(user.getAccount());
                if (user.getHeadPath()!= null)
                    loadImg(avatarImg, user.getHeadPath());
            }

            @Override
            public void failure(RetrofitError error) {
//                showShortToast(error.getMessage());
            }
        };
        GetUserInfoRequester getUserInfoRequester = new GetUserInfoRequester(callback, AppConf.USER_ID);
        getUserInfoRequester.request();

    }
    private void initView() {
        infoLayout = (LinearLayout) rootView.findViewById(R.id.info_user_layout);
        orderLayout = (LinearLayout) rootView.findViewById(R.id.order_user_layout);
        updateLayout = (LinearLayout) rootView.findViewById(R.id.update_user_layout);
        feedbackLayout = (LinearLayout) rootView.findViewById(R.id.feedback_user_layout);
        aboutLayout = (LinearLayout) rootView.findViewById(R.id.about_user_layout);

        MaterialRippleLayout.on(infoLayout)
                .rippleColor(getResources().getColor(R.color.md_blue_400))
                .rippleDuration(400)
                .rippleAlpha(0.6f)
                .create();
        MaterialRippleLayout.on(orderLayout)
                .rippleColor(getResources().getColor(R.color.md_blue_400))
                .rippleDuration(400)
                .rippleAlpha(0.6f)
                .create();
        MaterialRippleLayout.on(updateLayout)
                .rippleColor(getResources().getColor(R.color.md_blue_400))
                .rippleDuration(400)
                .rippleAlpha(0.6f)
                .create();
        MaterialRippleLayout.on(feedbackLayout)
                .rippleColor(getResources().getColor(R.color.md_blue_400))
                .rippleDuration(400)
                .rippleAlpha(0.6f)
                .create();
        MaterialRippleLayout.on(aboutLayout)
                .rippleColor(getResources().getColor(R.color.md_blue_400))
                .rippleDuration(400)
                .rippleAlpha(0.6f)
                .create();

        nameTv = (TextView) rootView.findViewById(R.id.name_user_tv);
        phoneTv = (TextView) rootView.findViewById(R.id.phone_user_tv);
        loginOutBtn = (Button) rootView.findViewById(R.id.login_out_btn);


    }
    private void initEvent() {
        infoLayout.setOnClickListener(this);
        orderLayout.setOnClickListener(this);
        updateLayout.setOnClickListener(this);
        feedbackLayout.setOnClickListener(this);
        aboutLayout.setOnClickListener(this);
        nameTv.setOnClickListener(this);
        avatarImg.setOnClickListener(this);

        loginOutBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_out_btn:
                if (SharedPreferencesUtils.contains(getActivity(),"token")){
                    SharedPreferencesUtils.clear(getActivity());
                }
                startActivity(LoginActivity.class);
                break;
            case R.id.info_user_layout:
                checkLogin2startActivity(UserInfoActivity.class, null);
                break;
            case R.id.update_user_layout:
                VersionManager versionManager = new VersionManager(getActivity(),1);
                versionManager.checkUpdate();
                break;
            case R.id.order_user_layout:

                break;
            case R.id.feedback_user_layout:
                checkLogin2startActivity(ConsultActivity.class,null);
                break;
            case R.id.about_user_layout:
                startActivity(AboutActivity.class);
                break;
            case R.id.name_user_tv:
                if (!SharedPreferencesUtils.contains(getActivity(),"token")){
                    startActivity(LoginActivity.class);
                }
                break;
            case R.id.avatar_user_img:
                checkLogin2startActivity(UserInfoActivity.class,null);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();
    }
}
