package com.drivingevaluate.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;
import com.drivingevaluate.R;
import com.drivingevaluate.app.App;
import com.drivingevaluate.model.Image;
import com.drivingevaluate.model.User;
import com.drivingevaluate.net.GetUserInfoRequester;
import com.drivingevaluate.net.UpdateUserInfoRequester;
import com.drivingevaluate.net.UploadFileRequester;
import com.drivingevaluate.net.component.RequestErrorHandler;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.util.BitmapUtil;
import com.drivingevaluate.util.Infliter;

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
    private File avatarFile;
    private ArrayList<String> mSelectPath;
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
                RequestErrorHandler requestErrorHandler = new RequestErrorHandler(UserInfoActivity.this);
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
        getUserInfoRequester.request();
    }

    @OnClick(R.id.avatar_user_layout)
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

    @OnClick(R.id.modify_pwd_layout)
    void modifyPwd(){
        startActivity(AlterPwdActivity.class);
    }

    @OnClick(R.id.gender_layout)
    void changeGender(){
        new BottomSheet.Builder(this).title("选择您的性别").sheet(R.menu.menu_gender).listener(new DialogInterface.OnClickListener() {
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
        param.put("userId",App.getUserId());
        param.put("nickName",nameEt.getText().toString());
        param.put("sex",gender);
        param.put("sign",signTv.getText().toString());
        param.put("status", status);
        final Callback<String> callback = new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                showShortToast("更新成功");
                dismissLoading();
                finish();
            }
            @Override
            public void failure(RetrofitError error) {
                RequestErrorHandler requestErrorHandler = new RequestErrorHandler(UserInfoActivity.this);
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
                    UpdateUserInfoRequester updateUserInfoRequester = new UpdateUserInfoRequester(callback, param);
                    updateUserInfoRequester.request();
                }
                @Override
                public void failure(RetrofitError error) {
                    RequestErrorHandler requestErrorHandler = new RequestErrorHandler(UserInfoActivity.this);
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
        if (requestCode == 2) {
            if (resultCode == -1) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                try {
                    Bitmap bitmap = BitmapUtil.revitionImageSize(mSelectPath.get(0));
                    avatarImg.setImageBitmap(bitmap);
                    avatarFile = BitmapUtil.saveBitmap2file2(bitmap, UserInfoActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
