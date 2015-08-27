package com.drivingevaluate.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.adapter.WriteStatusGridImgsAdapter;
import com.drivingevaluate.app.App;
import com.drivingevaluate.model.Image;
import com.drivingevaluate.net.PostMomentRequester;
import com.drivingevaluate.net.UploadFileRequester;
import com.drivingevaluate.net.component.RequestErrorHandler;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.util.BitmapUtil;
import com.drivingevaluate.util.ImageUtils.Bimp;
import com.drivingevaluate.util.ImageUtils.FileUtils;
import com.drivingevaluate.util.ImageUtils.ImageItem;
import com.drivingevaluate.util.ImageUtils.PublicWay;
import com.drivingevaluate.util.ImageUtils.Res;
import com.drivingevaluate.util.TextWatcherAdapter;
import com.drivingevaluate.view.WrapHeightGridView;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class AddMomentActivity extends Yat3sActivity implements OnClickListener,EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener {
    private EmojiconEditText etContent;
    private boolean located = false;
    private Button  btnCommit;
    private ImageButton btnBack;

    private StringBuffer picPath = new StringBuffer();
    private StringBuffer count = new StringBuffer();
    private WriteStatusGridImgsAdapter imageAdapter;
    @Bind(R.id.emotion_layout) LinearLayout emotionLayout;
    @Bind(R.id.gv_write_status)
    WrapHeightGridView imagesRv;
    @Bind(R.id.address_tv) TextView addressTv;
    public static Bitmap bimap ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_moment);
        ButterKnife.bind(this);
        Res.init(this);
        PublicWay.activityList.add(this);
        Loc();
        initView();
        initEvent();
    }

    private void initEvent() {
        imagesRv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == imageAdapter.getCount() - 1) {
                    Intent intent = new Intent(AddMomentActivity.this,
                            AlbumActivity.class);
                    startActivity(intent);
                }
            }
        });
        etContent.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private void initView() {
        etContent = (EmojiconEditText) findViewById(R.id.et_content);

        btnCommit = (Button) findViewById(R.id.btn_commit);
        btnBack = (ImageButton) findViewById(R.id.btn_back);

        imageAdapter = new WriteStatusGridImgsAdapter(this, Bimp.tempSelectBitmap,imagesRv);
        imagesRv.setAdapter(imageAdapter);

        btnCommit.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commit:
                commitMoment();
                break;
            case R.id.btn_back:
                finish();
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.picture_iv)
    void selectPicture(){
        startActivity(AlbumActivity.class);
    }
    private static final int TAKE_PICTURE = 0x000001;
    @OnClick(R.id.camera_iv)
    void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    @OnClick(R.id.et_content)
    void clickEditText(){
        if (emotionLayout.getVisibility() == View.VISIBLE){
            emotionLayout.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.emotion_iv)
    void showEmotion(){
        if (emotionLayout.getVisibility() == View.GONE){
            emotionLayout.setVisibility(View.VISIBLE);
            hideSoftInputView();
        }else {
            emotionLayout.setVisibility(View.GONE);
        }
    }
    @OnClick(R.id.address_tv)
    void showAddress(){
        if (located) {
            located = false;
            addressTv.setTextColor(getResources().getColor(R.color.font_black));
            addressTv.setText("显示定位");
        } else {
            located = true;
            addressTv.setTextColor(getResources().getColor(R.color.theme_blue));
            addressTv.setText(mApplication.myAddr);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {
                    String fileName = String.valueOf(System.currentTimeMillis());
                    Bitmap bm = (Bitmap) data.getExtras().get("data");
                    FileUtils.saveBitmap(bm, fileName);

                    ImageItem takePhoto = new ImageItem();
                    takePhoto.setBitmap(bm);
                    Bimp.tempSelectBitmap.add(takePhoto);
                }
                break;
        }
    }

    private void commitMoment() {
        String content = etContent.getText().toString();
        if (content.isEmpty() || content.equals("")){
            showShortToast("你还是说点什么吧");
            return;
        }
        showLoading();
        final Map<String,Object> param = new HashMap<>();
        param.put("userId", App.getUserId());
        param.put("content", content);
        param.put("latitude", mApplication.myLl.latitude);
        param.put("longitude", mApplication.myLl.longitude);
        param.put("city_code", mApplication.cityCode);
        if(located) {
            param.put("pubAddr", mApplication.myAddr);
        }else {
            param.put("pubAddr", "null");
        }
        /////////
        final Callback<String> callback = new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                dismissLoading();
                showShortToast("发布成功");
                Intent back = getIntent();
                setResult(Activity.RESULT_OK, back);
                finish();
            }
            @Override
            public void failure(RetrofitError error) {
                //如果上传失败清空，防止多张图片
                picPath = new StringBuffer();
                RequestErrorHandler requestErrorHandler = new RequestErrorHandler(AddMomentActivity.this);
                try {
                    requestErrorHandler.handError(error);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        if (Bimp.tempSelectBitmap.size() > 0){
            for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
                final int finalI = i;
                Callback<Image> ImageCallback = new Callback<Image>() {
                    @Override
                    public void success(Image image, Response response) {
                        picPath.append(image.getImgId() + ",");
                        count.append("1");
                        if (count.length() == Bimp.tempSelectBitmap.size()){
                            param.put("imgPath", picPath.substring(0,picPath.length()-1));
                            Log.e("Yat3s","multi_pic"+picPath.substring(0,picPath.length()-1));
                            PostMomentRequester postMomentRequester = new PostMomentRequester(callback,param);
                            postMomentRequester.request();
                        }
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        showShortToast("uploadImg-->"+error.getMessage());
                    }
                };
                UploadFileRequester uploadFileRequester = new UploadFileRequester(ImageCallback,new TypedFile("image/jpg", BitmapUtil.saveBitmap2file2(Bimp.tempSelectBitmap.get(i).getBitmap(), AddMomentActivity.this)));
                uploadFileRequester.uploadFileForId();
            }
        }
        else {
            PostMomentRequester postMomentRequester = new PostMomentRequester(callback,param);
            postMomentRequester.request();
        }
    }

    // 显示软键盘
    public void showSoftInputView() {
        if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).showSoftInput(etContent, 0);
        }
    }

    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        imageAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bimp.tempSelectBitmap.clear();
    }

    @Override
    public void onEmojiconBackspaceClicked(View view) {
        EmojiconsFragment.backspace(etContent);
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(etContent, emojicon);
    }
}
