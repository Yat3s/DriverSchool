package com.drivingevaluate.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.adapter.EmoViewPagerAdapter;
import com.drivingevaluate.adapter.EmoteAdapter;
import com.drivingevaluate.adapter.WriteStatusGridImgsAdapter;
import com.drivingevaluate.config.AppConf;
import com.drivingevaluate.model.FaceText;
import com.drivingevaluate.model.Image;
import com.drivingevaluate.net.PostMomentRequester;
import com.drivingevaluate.net.UploadFileRequester;
import com.drivingevaluate.net.component.RequestErrorHandler;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.util.FaceTextUtils;
import com.drivingevaluate.view.EmoticonsEditText;
import com.drivingevaluate.view.WrapHeightGridView;

import net.yazeed44.imagepicker.model.ImageEntry;
import net.yazeed44.imagepicker.util.Picker;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class AddMomentActivity extends Yat3sActivity implements OnClickListener {
    private TextView tvAddr;
    private EmoticonsEditText etContent;
    private boolean located = false;
    private Button  btnCommit;
    private ImageButton btnBack;
    private ImageButton btnAddEmo;
    private List<ImageEntry> picPaths;
    private List<Image> compressPaths = new ArrayList<>();
    private LinearLayout layout_emo;
    private StringBuffer picPath = new StringBuffer();
    private WriteStatusGridImgsAdapter imageAdapter;
    @Bind(R.id.gv_write_status)
    WrapHeightGridView imagesRv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_moment);
        ButterKnife.bind(this);
        Loc();
        initView();
        initEvent();
    }

    private void initEvent() {
        imagesRv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == imageAdapter.getCount() - 1) {
                    // 如果点击了最后一个加号图标,则显示选择图片对话框
                    new Picker.Builder(AddMomentActivity.this,new MyPickListener(),R.style.AppTheme)
                            .setLimit(9)
                            .setImageBackgroundColorWhenChecked(getResources().getColor(R.color.theme_blue))
                            .setAlbumBackgroundColor(getResources().getColor(R.color.theme_blue))
                            .setFabBackgroundColor(getResources().getColor(R.color.theme_blue))
                            .setFabBackgroundColorWhenPressed(getResources().getColor(R.color.md_purple_300))
                            .setAlbumImagesCountTextColor(getResources().getColor(R.color.md_white_1000))
                            .build()
                            .startActivity();
                }
            }
        });
    }

    private void initView() {
        tvAddr = (TextView) findViewById(R.id.tv_address);
        etContent = (EmoticonsEditText) findViewById(R.id.et_content);

        btnCommit = (Button) findViewById(R.id.btn_commit);
        btnAddEmo = (ImageButton) findViewById(R.id.btn_addEmo);
        btnBack = (ImageButton) findViewById(R.id.btn_back);

        layout_emo = (LinearLayout) findViewById(R.id.layout_emo);

        initEmoView();
        picPaths = new ArrayList<>();
        imageAdapter = new WriteStatusGridImgsAdapter(this,picPaths,imagesRv);
        imagesRv.setAdapter(imageAdapter);

        tvAddr.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        btnAddEmo.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_address:
                if (located) {
                    located = false;
                    tvAddr.setTextColor(getResources().getColor(R.color.font_black));
                    tvAddr.setText("地点");
                } else {
                    located = true;
                    tvAddr.setTextColor(getResources().getColor(R.color.theme_blue));
                    tvAddr.setText(mApplication.myAddr);
                }
                break;
            case R.id.btn_addEmo:
                if (layout_emo.getVisibility() == View.GONE) {
                    layout_emo.setVisibility(View.VISIBLE);
                    hideSoftInputView();
                } else {
                    layout_emo.setVisibility(View.GONE);
                }
                break;
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

    private class MyPickListener implements Picker.PickListener
    {
        @Override
        public void onPickedSuccessfully(ArrayList<ImageEntry> arrayList) {
            picPaths.addAll(arrayList);
//            for (int i = 0; i < picPaths.size(); i++) {
//                Bitmap bitmap = BitmapUtil.getSmallBitmap(picPaths.get(i).path);
//                compressPaths.add(new Image(BitmapUtil.saveBitmap2file(bitmap)));
//            }
            if(arrayList.size() > 0) {
                // 如果有图片则显示GridView,同时更新内容
                imagesRv.setVisibility(View.VISIBLE);
                imageAdapter.notifyDataSetChanged();
            } else {
                // 无图则不显示GridView
                imagesRv.setVisibility(View.GONE);
            }
        }
        @Override
        public void onCancel(){
            //User cancled the pick activity
        }
    }

    private void commitMoment() {
        showLoading();
        String content = etContent.getText().toString();
        final Map<String,Object> param = new HashMap<>();
        param.put("userId", AppConf.USER_ID);
        param.put("content", content);
        Log.e("Yat3s","userID---->"+AppConf.USER_ID);
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

       /////////////


        if (located) {
            param.put("pubAddr", mApplication.myAddr);
            param.put("latitude", mApplication.myLl.latitude);
            param.put("longitude", mApplication.myLl.longitude);
            param.put("city_code", mApplication.cityCode);
        }
        if (compressPaths.size() > 0){
            for (int i = 0; i < compressPaths.size(); i++) {
                final int finalI = i;
                Callback<Image> ImageCallback = new Callback<Image>() {
                    @Override
                    public void success(Image image, Response response) {
                        picPath.append(image.getImgId() + ",");

                        if (finalI == compressPaths.size()-1){
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
                UploadFileRequester uploadFileRequester = new UploadFileRequester(ImageCallback,new TypedFile("image/jpg", new File(compressPaths.get(i).getImgPath())));
                uploadFileRequester.uploadFileForId();
            }
        }
        else {
            PostMomentRequester postMomentRequester = new PostMomentRequester(callback,param);
            postMomentRequester.request();
        }
    }

    List<FaceText> emos;
    private ViewPager pager_emo;

    /**
     * 初始化表情布局
     *
     * @Title: initEmoView
     * @Description: TODO
     * @param
     * @return void
     * @throws
     */
    private void initEmoView() {
        pager_emo = (ViewPager) findViewById(R.id.pager_emo);
        emos = FaceTextUtils.faceTexts;

        List<View> views = new ArrayList<View>();
        for (int i = 0; i < 2; ++i) {
            views.add(getGridView(i));
        }
        pager_emo.setAdapter(new EmoViewPagerAdapter(views));
    }

    private View getGridView(final int i) {
        View view = View.inflate(this, R.layout.include_emo_gridview, null);
        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        List<FaceText> list = new ArrayList<FaceText>();
        if (i == 0) {
            list.addAll(emos.subList(0, 21));
        } else if (i == 1) {
            list.addAll(emos.subList(21, emos.size()));
        }
        final EmoteAdapter gridAdapter = new EmoteAdapter(AddMomentActivity.this, list);
        gridview.setAdapter(gridAdapter);
        gridview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                FaceText name = (FaceText) gridAdapter.getItem(position);
                String key = name.text.toString();
                try {
                    if (etContent != null && !TextUtils.isEmpty(key)) {
                        int start = etContent.getSelectionStart();
                        CharSequence content = etContent.getText().insert(start, key);
                        etContent.setText(content);
                        // 定位光标位置
                        CharSequence info = etContent.getText();
                        if (info instanceof Spannable) {
                            Spannable spanText = (Spannable) info;
                            Selection.setSelection(spanText, start + key.length());
                        }
                    }
                } catch (Exception e) {

                }

            }
        });
        return view;
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
}
