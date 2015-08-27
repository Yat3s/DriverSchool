package com.drivingevaluate.ui;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.cocosw.bottomsheet.BottomSheet;
import com.drivingevaluate.R;
import com.drivingevaluate.app.App;
import com.drivingevaluate.model.Image;
import com.drivingevaluate.model.User;
import com.drivingevaluate.net.UpdateUserInfoRequester;
import com.drivingevaluate.net.UploadFileRequester;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.util.BitmapUtil;

import net.yazeed44.imagepicker.model.ImageEntry;
import net.yazeed44.imagepicker.util.Picker;

import java.io.File;
import java.io.IOException;
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

/**
 * Created by Yat3s on 8/22/15.
 * Email:hawkoyates@gmail.com
 */
public class FirstSettingInfoActivity extends Yat3sActivity{
    private User user;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.avatar_img) ImageView avatarImg;
    @Bind(R.id.nickname_et) EditText nameEt;
    @Bind(R.id.gender_et) EditText genderTv;
    private String avatarUrl;
    private String gender;
    private File avatarFile;
    private int status = 1;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_first_setting_info);
        ButterKnife.bind(this);
        setToolbarWithNavigation(toolbar, "开始");
    }

    @OnClick(R.id.avatar_img)
    void changeAvatar(){
        new Picker.Builder(this,new MyPickListener(),R.style.AppTheme)
                .setLimit(1)
                .setImageBackgroundColorWhenChecked(getResources().getColor(R.color.theme_blue))
                .setAlbumBackgroundColor(getResources().getColor(R.color.bg_dialog))
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
            Bitmap bitmap = null;
            try {
                bitmap = BitmapUtil.revitionImageSize(avatarUrl);
                avatarImg.setImageBitmap(bitmap);
                avatarFile = BitmapUtil.saveBitmap2file2(bitmap,FirstSettingInfoActivity.this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onCancel(){
            //User cancled the pick activity
        }
    }

    @OnClick(R.id.gender_et)
    void changeGender(){
        new BottomSheet.Builder(this).title("选择您的性别").sheet(R.menu.menu_gender).icon(R.mipmap.ic_servicer).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.male:
                        gender = "1";
                        genderTv.setText("帅哥");
                        break;
                    case R.id.female:
                        gender = "0";
                        genderTv.setText("美女");
                        break;
                }
            }
        }).show();
    }

    @OnClick(R.id.start_btn)
    void saveUserInfo(){
        if (nameEt.getText().toString().isEmpty()){
            showShortToast("取个帅气的名吧");
            return;
        }
        if (gender == null){
            showShortToast("告诉大家你的性别吧");
            return;
        }
        final Map<String,Object> param = new HashMap<>();
        param.put("userId",App.getUserId());
        param.put("nickName",nameEt.getText().toString());
        param.put("sex",gender);
        param.put("sign","新手起步");
        param.put("status", status);
        final Callback<String> callback = new Callback<String>() {
            @Override
            public void success(String s, Response response) {
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
            UploadFileRequester uploadFileRequester = new UploadFileRequester(imageCallback, new TypedFile("image/jpg", avatarFile));
            uploadFileRequester.uploadFileForPath();
        }
        else {
            UpdateUserInfoRequester updateUserInfoRequester = new UpdateUserInfoRequester(callback, param);
            updateUserInfoRequester.request();
        }
    }
}
