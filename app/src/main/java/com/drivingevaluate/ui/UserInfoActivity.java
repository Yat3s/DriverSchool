package com.drivingevaluate.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;
import com.drivingevaluate.R;
import com.drivingevaluate.config.AppConf;
import com.drivingevaluate.model.Image;
import com.drivingevaluate.model.User;
import com.drivingevaluate.net.GetUserInfoRequester;
import com.drivingevaluate.net.UpdateUserInfoRequester;
import com.drivingevaluate.net.UploadFileRequester;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.util.BitmapUtil;
import com.drivingevaluate.util.Infliter;

import net.yazeed44.imagepicker.model.ImageEntry;
import net.yazeed44.imagepicker.util.Picker;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class UserInfoActivity extends Yat3sActivity{
    private User user;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.avatar_user_img) ImageView avatarImg;
    @Bind(R.id.sign_tv) TextView signTv;
    @Bind(R.id.name_et) EditText nameEt;
    @Bind(R.id.phone_tv) TextView phoneTv;
    @Bind(R.id.gender_tv) TextView genderTv;
    @Bind(R.id.status_tv) TextView statusTv;

    private String avatarUrl,compressPath;
    private String gender;
    private int status;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        setToolbarWithNavigation(toolbar, "个人信息");
        nameEt.clearFocus();
        getDate();
    }

    private void getDate() {
        Callback<User> callback = new Callback<User>() {
            @Override
            public void success(User remoteUser, Response response) {
                user = remoteUser;
                nameEt.setText(user.getUserName());
                phoneTv.setText(user.getAccount());
                signTv.setText(user.getSign());
                gender = user.getSex();
                if (gender.equals("0")){
                    genderTv.setText("女");
                }else
                    genderTv.setText("男");
                statusTv.setText(Infliter.statusInfliter(user.getStatus()));
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

    @OnClick(R.id.avatar_user_layout)
    void changeAvatar(){
        new Picker.Builder(this,new MyPickListener(),R.style.AppTheme)
                .setLimit(1)
                .setImageBackgroundColorWhenChecked(getResources().getColor(R.color.theme_blue))
                .setAlbumBackgroundColor(getResources().getColor(R.color.theme_blue))
                .setFabBackgroundColor(getResources().getColor(R.color.theme_blue))
                .setFabBackgroundColorWhenPressed(getResources().getColor(R.color.md_purple_300))
                .build()
                .startActivity();
    }

    private class MyPickListener implements Picker.PickListener
    {
        @Override
        public void onPickedSuccessfully(ArrayList<ImageEntry> arrayList) {
                avatarUrl = arrayList.get(0).path;
                Bitmap bitmap = BitmapUtil.getSmallBitmap(avatarUrl,240,400);
                avatarImg.setImageBitmap(bitmap);
                compressPath = BitmapUtil.saveBitmap2file(bitmap);
        }
        @Override
        public void onCancel(){
            //User cancled the pick activity
        }
    }

    @OnClick(R.id.modify_pwd_layout)
    void modifyPwd(){
        startActivity(AlterPwdActivity.class);
    }

    @OnClick(R.id.gender_layout)
    void changeGender(){
        new BottomSheet.Builder(this).title("选择您的性别").sheet(R.menu.menu_gender).icon(R.mipmap.ic_servicer).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.male:
                        gender = "1";
                        genderTv.setText("男");
                        break;
                    case R.id.female:
                        gender = "0";
                        genderTv.setText("女");
                        break;
                }
            }
        }).show();
    }

    @OnClick(R.id.status_layout)
    void changeStatus(){
        new BottomSheet.Builder(this).title("选择您的学车状态").sheet(R.menu.menu_status).icon(R.mipmap.ic_servicer).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.no_status_study:
                        status = 1;
                        break;
                    case R.id.ing_status_study:
                        status = 2;
                        break;
                    case R.id.complete_status_study:
                        status = 3;
                        break;
                    case R.id.god_status_study:
                        status = 4;
                        break;
                }
                statusTv.setText(Infliter.statusInfliter(status));
            }
        }).show();
    }

    @OnClick(R.id.sign_layout)
    void changeSign(){
        Intent intent  = new Intent(this,UserSignActivity.class);
        intent.putExtra("sign",user.getSign());
        startActivityForResult(intent,100);
    }


    @OnClick(R.id.save_user_info_btn)
    void saveUserInfo(){
        showLoading();
        final Map<String,Object> param = new HashMap<>();
        param.put("userId",AppConf.USER_ID);
        param.put("nickName",nameEt.getText().toString());
        param.put("sex",gender);
        param.put("sign",signTv.getText().toString());
        param.put("status", status);
        final Callback<String> callback = new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                showShortToast("跟新成功");
                dismissLoading();
                finish();
            }
            @Override
            public void failure(RetrofitError error) {
                showShortToast(error.getMessage());
            }
        };

        if (avatarUrl != null) {
            Callback<Image> imageCallback = new Callback<Image>() {
                @Override
                public void success(Image image, Response response) {
                    param.put("imgPath", image.getImgPath());
                    Log.e("Yat3s", "imgPath-->" + image.getImgPath());
                    UpdateUserInfoRequester updateUserInfoRequester = new UpdateUserInfoRequester(callback, param);
                    updateUserInfoRequester.request();
                }

                @Override
                public void failure(RetrofitError error) {
                    showShortToast("uploadImg-->" + error.getMessage());
                }
            };
            UploadFileRequester uploadFileRequester = new UploadFileRequester(imageCallback, new TypedFile("image/jpg", new File(compressPath)));
            uploadFileRequester.uploadFileForPath();
        }
        else {
            param.put("imgPath", user.getHeadPath());
            UpdateUserInfoRequester updateUserInfoRequester = new UpdateUserInfoRequester(callback, param);
            updateUserInfoRequester.request();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 100){
            signTv.setText(data.getStringExtra("sign"));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
