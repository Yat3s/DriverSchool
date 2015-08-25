package com.drivingevaluate.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.adapter.EmotionGvAdapter;
import com.drivingevaluate.adapter.EmotionPagerAdapter;
import com.drivingevaluate.adapter.WriteStatusGridImgsAdapter;
import com.drivingevaluate.app.App;
import com.drivingevaluate.model.Emotion;
import com.drivingevaluate.model.Image;
import com.drivingevaluate.net.PostMomentRequester;
import com.drivingevaluate.net.UploadFileRequester;
import com.drivingevaluate.net.component.RequestErrorHandler;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.util.BitmapUtil;
import com.drivingevaluate.util.ImageUtils.Bimp;
import com.drivingevaluate.util.ImageUtils.DisplayUtils;
import com.drivingevaluate.util.ImageUtils.FileUtils;
import com.drivingevaluate.util.ImageUtils.ImageItem;
import com.drivingevaluate.util.ImageUtils.PublicWay;
import com.drivingevaluate.util.ImageUtils.Res;
import com.drivingevaluate.util.StringUtils;
import com.drivingevaluate.view.EmoticonsEditText;
import com.drivingevaluate.view.WrapHeightGridView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class AddMomentActivity extends Yat3sActivity implements OnClickListener,OnItemClickListener {
    private EditText etContent;
    private boolean located = false;
    private Button  btnCommit;
    private ImageButton btnBack;
    private LinearLayout layout_emo;
    private StringBuffer picPath = new StringBuffer();
    private StringBuffer count = new StringBuffer();
    private WriteStatusGridImgsAdapter imageAdapter;
    @Bind(R.id.gv_write_status)
    WrapHeightGridView imagesRv;
    @Bind(R.id.address_tv) TextView addressTv;
    public static Bitmap bimap ;

    // 表情选择面板
    @Bind(R.id.ll_emotion_dashboard) LinearLayout ll_emotion_dashboard;
    @Bind(R.id.vp_emotion_dashboard) ViewPager vp_emotion_dashboard;
    private EmotionPagerAdapter emotionPagerGvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_moment);
        ButterKnife.bind(this);
        Res.init(this);
        PublicWay.activityList.add(this);
        Loc();
        initView();
        initEmotion();
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
    }

    private void initView() {
        etContent = (EmoticonsEditText) findViewById(R.id.et_content);

        btnCommit = (Button) findViewById(R.id.btn_commit);
        btnBack = (ImageButton) findViewById(R.id.btn_back);

        layout_emo = (LinearLayout) findViewById(R.id.layout_emo);

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
        if (ll_emotion_dashboard.VISIBLE == View.VISIBLE) {
            ll_emotion_dashboard.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.emotion_iv)
    void showEmotion(){
        if(ll_emotion_dashboard.getVisibility() == View.VISIBLE) {
            // 显示表情面板时点击,将按钮图片设为笑脸按钮,同时隐藏面板
//            iv_emoji.setImageResource(R.drawable.btn_insert_emotion);
            ll_emotion_dashboard.setVisibility(View.GONE);
            showSoftInputView();
        } else {
            // 未显示表情面板时点击,将按钮图片设为键盘,同时显示面板
//            iv_emoji.setImageResource(R.drawable.btn_insert_keyboard);
            ll_emotion_dashboard.setVisibility(View.VISIBLE);
            hideSoftInputView();
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
        if (located) {
            param.put("pubAddr", mApplication.myAddr);
            param.put("latitude", mApplication.myLl.latitude);
            param.put("longitude", mApplication.myLl.longitude);
            param.put("city_code", mApplication.cityCode);
        }
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



    /**
     *  初始化表情面板内容
     */
    private void initEmotion() {
        // 获取屏幕宽度
        int gvWidth = DisplayUtils.getScreenWidthPixels(this);
        // 表情边距
        int spacing = DisplayUtils.dp2px(this, 8);
        // GridView中item的宽度
        int itemWidth = (gvWidth - spacing * 8) / 7;
        int gvHeight = itemWidth * 3 + spacing * 4;

        List<GridView> gvs = new ArrayList<GridView>();
        List<String> emotionNames = new ArrayList<String>();
        // 遍历所有的表情名字
        for (String emojiName : Emotion.emojiMap.keySet()) {
            emotionNames.add(emojiName);
            // 每20个表情作为一组,同时添加到ViewPager对应的view集合中
            if (emotionNames.size() == 20) {
                GridView gv = createEmotionGridView(emotionNames, gvWidth, spacing, itemWidth, gvHeight);
                gvs.add(gv);
                // 添加完一组表情,重新创建一个表情名字集合
                emotionNames = new ArrayList<String>();
            }
        }

        // 检查最后是否有不足20个表情的剩余情况
        if (emotionNames.size() > 0) {
            GridView gv = createEmotionGridView(emotionNames, gvWidth, spacing, itemWidth, gvHeight);
            gvs.add(gv);
        }

        // 将多个GridView添加显示到ViewPager中
        emotionPagerGvAdapter = new EmotionPagerAdapter(gvs);
        vp_emotion_dashboard.setAdapter(emotionPagerGvAdapter);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gvWidth, gvHeight);
        vp_emotion_dashboard.setLayoutParams(params);
    }

    /**
     * 创建显示表情的GridView
     */
    private GridView createEmotionGridView(List<String> emotionNames, int gvWidth, int padding, int itemWidth, int gvHeight) {
        // 创建GridView
        GridView gv = new GridView(this);
        gv.setBackgroundResource(R.color.gray);
        gv.setSelector(R.color.transparent);
        gv.setNumColumns(7);
        gv.setPadding(padding, padding, padding, padding);
        gv.setHorizontalSpacing(padding);
        gv.setVerticalSpacing(padding);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(gvWidth, gvHeight);
        gv.setLayoutParams(params);
        // 给GridView设置表情图片
        EmotionGvAdapter adapter = new EmotionGvAdapter(this, emotionNames, itemWidth);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(this);
        return gv;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object itemAdapter = parent.getAdapter();
            if (itemAdapter instanceof EmotionGvAdapter) {
            // 点击的是表情
            EmotionGvAdapter emotionGvAdapter = (EmotionGvAdapter) itemAdapter;

            if (position == emotionGvAdapter.getCount() - 1) {
                // 如果点击了最后一个回退按钮,则调用删除键事件
                etContent.dispatchKeyEvent(new KeyEvent(
                        KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            } else {
                // 如果点击了表情,则添加到输入框中
                String emotionName = emotionGvAdapter.getItem(position);

                // 获取当前光标位置,在指定位置上添加表情图片文本
                int curPosition = etContent.getSelectionStart();
                StringBuilder sb = new StringBuilder(etContent.getText().toString());
                sb.insert(curPosition, emotionName);

                // 特殊文字处理,将表情等转换一下
                etContent.setText(StringUtils.getWeiboContent(
                        AddMomentActivity.this, etContent, sb.toString()));
                Log.e("Yat3s", "span:" + StringUtils.getWeiboContent(
                        this, etContent, sb.toString()));
                // 将光标设置到新增完表情的右侧
                etContent.setSelection(curPosition + emotionName.length());
            }
        }
    }


    /**
     * 根据是否点击笑脸来显示文本输入框的状态
     *
     * @Title: showEditState
     * @Description: TODO
     * @param @param isEmo: 用于区分文字和表情
     * @return void
     * @throws
     */
    private void showEditState(boolean isEmo) {
        etContent.setVisibility(View.VISIBLE);
        etContent.requestFocus();
        if (isEmo) {
            layout_emo.setVisibility(View.VISIBLE);
            hideSoftInputView();
        } else {
            showSoftInputView();
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
}
