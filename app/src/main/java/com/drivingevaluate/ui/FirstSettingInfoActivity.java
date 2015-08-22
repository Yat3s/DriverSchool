package com.drivingevaluate.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.File;
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
    @Bind(R.id.avatar_user_img)
    ImageView avatarImg;
    @Bind(R.id.sign_tv)
    TextView signTv;
    @Bind(R.id.name_et)
    EditText nameEt;
    @Bind(R.id.phone_tv) TextView phoneTv;
    @Bind(R.id.gender_tv) TextView genderTv;
    @Bind(R.id.status_tv) TextView statusTv;

    private String avatarUrl;
    private String gender;
    private int status;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_first_setting_info);
        ButterKnife.bind(this);
        setToolbarWithNavigation(toolbar, "开始");
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
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
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
            UploadFileRequester uploadFileRequester = new UploadFileRequester(imageCallback, new TypedFile("image/jpg", new File(avatarUrl)));
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
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            try {
                String[] pojo = { MediaStore.Images.Media.DATA };
                Cursor cursor = managedQuery(uri, pojo, null, null, null);
                if (cursor != null) {
                    ContentResolver cr = this.getContentResolver();
                    int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String path = cursor.getString(colunm_index);
                    /***
                     * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，你选择的文件就不一定是图片了，
                     * 这样的话，我们判断文件的后缀名 如果是图片格式的话，那么才可以
                     */
                    if (path.endsWith("jpg") || path.endsWith("png")) {
                        avatarUrl = path;
                        avatarImg.setImageBitmap(BitmapUtil.getSmallBitmap(avatarUrl));
                    } else {
                        alert();
                    }
                } else {
                    alert();
                }

            } catch (Exception e) {
            }
        }

        if (resultCode == 100){
            signTv.setText(data.getStringExtra("sign"));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void alert() {
        Dialog dialog = new AlertDialog.Builder(this).setTitle("提示").setMessage("您选择的不是有效的图片")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        avatarUrl = null;
                    }
                }).create();
        dialog.show();
    }
}
