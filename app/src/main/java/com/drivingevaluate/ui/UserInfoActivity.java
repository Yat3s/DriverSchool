package com.drivingevaluate.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.model.User;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.util.StringUtil;

public class UserInfoActivity extends Yat3sActivity implements OnClickListener{
    private TextView tvNickName,tvPhone,tvRegDate,tvEmotions;
    private ImageView avatarImg;
    private Button btnAlterPwd;

    private User user;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setBackTitleBar();
        setContentView(R.layout.activity_user_info);

        initView();
        initEvent();

        getDate();
    }

    private void getDate() {
        user = (User) getIntent().getExtras().getSerializable("user");
        if (user != null) {
            tvNickName.setText(user.getUserName());
            tvPhone.setText(StringUtil.getPhoneStrWithStar(user.getAccount()));
            tvEmotions.setText(user.getSign());
        }
    }

    private void initView() {
        setTitleBarTitle("个人信息");

        tvNickName = (TextView) findViewById(R.id.tv_nickName);
        tvPhone = (TextView) findViewById(R.id.tv_phone);
        tvRegDate = (TextView) findViewById(R.id.tv_regDate);
        tvEmotions = (TextView) findViewById(R.id.tv_emotions);
        btnAlterPwd = (Button) findViewById(R.id.btn_alterPwd);

        avatarImg = (ImageView) findViewById(R.id.img_avatar);
    }
    private void initEvent() {
        avatarImg.setOnClickListener(this);
        btnAlterPwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_avatar:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_alterPwd:
                startActivity(AlterPwdActivity.class);
                break;
            default:
                break;
        }
    }
}
