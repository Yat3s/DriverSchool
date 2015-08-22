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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.adapter.EmoViewPagerAdapter;
import com.drivingevaluate.adapter.EmoteAdapter;
import com.drivingevaluate.config.AppConf;
import com.drivingevaluate.model.FaceText;
import com.drivingevaluate.model.Image;
import com.drivingevaluate.net.PostMomentRequester;
import com.drivingevaluate.net.UploadFileRequester;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.util.BitmapUtil;
import com.drivingevaluate.util.FaceTextUtils;
import com.drivingevaluate.view.EmoticonsEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class AddMomentActivity extends Yat3sActivity implements OnClickListener {
    private TextView tvAddr;
    private ImageView imgAddPic;
    private EmoticonsEditText etContent;
    private boolean located = false;
    private Button  btnCommit;
    private ImageButton btnBack;
    private ImageButton btnAddEmo;
    private String picPath;
    private LinearLayout layout_emo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_moment);
        Loc();
        initView();
    }

    private void initView() {
        tvAddr = (TextView) findViewById(R.id.tv_address);
        imgAddPic = (ImageView) findViewById(R.id.img_addPic);
        etContent = (EmoticonsEditText) findViewById(R.id.et_content);

        btnCommit = (Button) findViewById(R.id.btn_commit);
        btnAddEmo = (ImageButton) findViewById(R.id.btn_addEmo);
        btnBack = (ImageButton) findViewById(R.id.btn_back);

        layout_emo = (LinearLayout) findViewById(R.id.layout_emo);

        initEmoView();

        tvAddr.setOnClickListener(this);
        imgAddPic.setOnClickListener(this);
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
            case R.id.img_addPic:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
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

    private void alert() {
        Dialog dialog = new AlertDialog.Builder(this).setTitle("提示").setMessage("您选择的不是有效的图片")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        picPath = null;
                    }
                }).create();
        dialog.show();
    }

    private void commitMoment() {
        showLoading();
        String content = etContent.getText().toString();
        final Map<String,Object> param = new HashMap<>();
        param.put("userId", AppConf.USER_ID);
        param.put("content", content);
        if (located) {
            param.put("pubAddr", mApplication.myAddr);
            param.put("latitude", mApplication.myLl.latitude);
            param.put("longitude", mApplication.myLl.longitude);
            param.put("city_code", mApplication.cityCode);
        }
        if (picPath != null){
            Callback<Image> callback = new Callback<Image>() {
                @Override
                public void success(Image image, Response response) {
                    param.put("imgPath", image.getImgId());
                    Log.e("Yat3s","imgPath-->"+image.getImgId());
                    Callback<String> callback = new Callback<String>() {
                        @Override
                        public void success(String s, Response response) {
                            dismissLoading();
                            showShortToast("发布成功");
                            Intent back = getIntent();
                            back.putExtra("picPath", picPath);
                            setResult(Activity.RESULT_OK, back);
                            finish();
                        }
                        @Override
                        public void failure(RetrofitError error) {
                            showShortToast("commit-->"+error.getMessage());
                        }
                    };
                    PostMomentRequester postMomentRequester = new PostMomentRequester(callback,param);
                    postMomentRequester.request();
                }

                @Override
                public void failure(RetrofitError error) {
                    showShortToast("uploadImg-->"+error.getMessage());
                }
            };
            UploadFileRequester uploadFileRequester = new UploadFileRequester(callback,new TypedFile("image/jpg", new File(picPath)));
            Log.e("Yat3s", picPath);
            uploadFileRequester.uploadFileForId();
        }
        else {
            Callback<String> callback = new Callback<String>() {
                @Override
                public void success(String s, Response response) {
                    dismissLoading();
                    showShortToast("发布成功");
                    Intent back = getIntent();
                    back.putExtra("picPath", picPath);
                    setResult(Activity.RESULT_OK, back);
                    finish();
                }

                @Override
                public void failure(RetrofitError error) {
                    showShortToast("commit-->"+error.getMessage());
                }
            };
            PostMomentRequester postMomentRequester = new PostMomentRequester(callback,param);
            postMomentRequester.request();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            /**
             * 当选择的图片不为空的话，在获取到图片的途径
             */
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
                        picPath = path;
//                        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                        imgAddPic.setImageBitmap(BitmapUtil.getSmallBitmap(picPath));
                    } else {
                        alert();
                    }
                } else {
                    alert();
                }

            } catch (Exception e) {
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
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
