package com.drivingevaluate.ui;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.drivingevaluate.net.component.RequestErrorHandler;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.util.BitmapUtil;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
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
    private ArrayList<String> mSelectPath;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_first_setting_info);
        ButterKnife.bind(this);
        setToolbarWithNavigation(toolbar, "开始");
    }

    @OnClick(R.id.avatar_img)
    void changeAvatar(){
        Intent intent = new Intent(this, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
        // 选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
        // 默认选择
        if (mSelectPath != null && mSelectPath.size() > 0) {
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
        }
        startActivityForResult(intent, 2);
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
                RequestErrorHandler requestErrorHandler = new RequestErrorHandler(FirstSettingInfoActivity.this);
                try {
                    requestErrorHandler.handError(error);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        if (mSelectPath != null && mSelectPath.size() > 0) {
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
                    RequestErrorHandler requestErrorHandler = new RequestErrorHandler(FirstSettingInfoActivity.this);
                    try {
                        requestErrorHandler.handError(error);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == -1) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                try {
                    Bitmap bitmap = BitmapUtil.revitionImageSize(mSelectPath.get(0));
                    avatarImg.setImageBitmap(bitmap);
                    avatarFile = BitmapUtil.saveBitmap2file2(bitmap, FirstSettingInfoActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
