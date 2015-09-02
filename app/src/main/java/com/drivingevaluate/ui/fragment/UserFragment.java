package com.drivingevaluate.ui.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.cocosw.bottomsheet.BottomSheet;
import com.drivingevaluate.R;
import com.drivingevaluate.app.App;
import com.drivingevaluate.config.VersionManager;
import com.drivingevaluate.model.CustomerService;
import com.drivingevaluate.model.User;
import com.drivingevaluate.net.GetCustomerServiceRequester;
import com.drivingevaluate.net.GetUserInfoRequester;
import com.drivingevaluate.net.component.RequestErrorHandler;
import com.drivingevaluate.ui.AboutActivity;
import com.drivingevaluate.ui.LoginActivity;
import com.drivingevaluate.ui.UserInfoActivity;
import com.drivingevaluate.ui.UserOrderActivity;
import com.drivingevaluate.ui.base.Yat3sFragment;
import com.drivingevaluate.util.ACache;
import com.drivingevaluate.util.SharedPreferencesUtils;

import org.json.JSONException;

import java.io.IOException;

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
    private CustomerService mCustomerService;
    @Bind(R.id.avatar_user_img)
    ImageView avatarImg;
    private User mUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        rootView = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this,rootView);
        initView();
        initEvent();
        getCustomerService();
        return rootView;
    }

    private void getUserInfo() {
        Callback<User> callback = new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                ACache.get(getActivity()).put("user", user);
                mUser = user;
                setUserInfo();
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
        GetUserInfoRequester getUserInfoRequester = new GetUserInfoRequester(callback, App.getUserId());
        if (SharedPreferencesUtils.contains(getActivity(),"token")) {
            getUserInfoRequester.request();
            loginOutBtn.setVisibility(View.VISIBLE);
        }
        else {
            loginOutBtn.setVisibility(View.INVISIBLE);
            nameTv.setText("点击登录");
            phoneTv.setText("登录后查看更多优惠信息");
        }

    }

    private void setUserInfo() {
        if (mUser.getUserName().isEmpty()) {
            nameTv.setText("未设置昵称");
        } else {
            nameTv.setText(mUser.getUserName());
        }
        phoneTv.setText(mUser.getSign());
        if (mUser.getHeadPath() != null && !mUser.getHeadPath().equals(""))
            loadImg(avatarImg, mUser.getHeadPath());
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

        //缓存处理
        mUser = (User) ACache.get(getActivity()).getAsObject("user");
        if (mUser != null) {
            setUserInfo();
        }
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
                SharedPreferencesUtils.remove(getActivity(), "token");
                SharedPreferencesUtils.remove(getActivity(), "userId");
                startActivity(LoginActivity.class);
                break;
            case R.id.info_user_layout:
                checkLogin2startActivity(UserInfoActivity.class, null);
                break;
            case R.id.update_user_layout:
                VersionManager versionManager = new VersionManager(getActivity(), 1);
                versionManager.checkUpdate();
                break;
            case R.id.order_user_layout:
                checkLogin2startActivity(UserOrderActivity.class,null);
                break;
            case R.id.feedback_user_layout:
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

    private void getCustomerService() {
        Callback<CustomerService> callback = new Callback<CustomerService>() {
            @Override
            public void success(CustomerService customerService, Response response) {
                mCustomerService = customerService;
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
        GetCustomerServiceRequester getCustomerServiceRequester = new GetCustomerServiceRequester(callback);
        getCustomerServiceRequester.request();
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();
    }
}
