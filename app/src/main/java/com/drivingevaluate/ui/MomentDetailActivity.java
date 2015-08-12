package com.drivingevaluate.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.drivingevaluate.R;
import com.drivingevaluate.api.JsonResolve;
import com.drivingevaluate.config.StateConfig;
import com.drivingevaluate.model.Comment;
import com.drivingevaluate.model.Moment;
import com.drivingevaluate.ui.base.Yat3sActivity;

import java.util.List;

/**
 * @author Yat3s
 *
 */
public class MomentDetailActivity extends Yat3sActivity implements OnClickListener{
    private ImageView imgAvator,img;
    private TextView tvName,tvPubTime,tvContent;
    private ListView lvComment;
    private EditText etComment;
    private Button btnCommit;

    private int momentId;

    private Moment moment;
    private List<Comment> comments;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StateConfig.CODE_MOMENT_BY_ID:
                    moment = JSON.parseObject(msg.obj.toString(),Moment.class);
                    setMomentDetails();
                    break;
                case StateConfig.CODE_COMMENT_BY_STYLE:
                    if (!msg.obj.toString().equals("超出页数")) {
                        comments = JSON.parseArray(msg.obj.toString(),Comment.class);
                        setComments();
                    }
                    break;
                case StateConfig.CODE_ADD_COMMENT:
                    if (!msg.obj.toString().equals("评论添加成功")){
                        showShortToast("评论成功");
                        etComment.setText("");
                        getComment();
                        hideSoftInputView();
                    }
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setBackTitleBar();
        setContentView(R.layout.activity_moment_detail);

        initView();
        initEvent();
        momentId = getIntent().getIntExtra("momentId", -1);
        getMoment();
        getComment();
    }

    /**
     * 网络获取Moments详情
     */
    private void getMoment() {
        JsonResolve.getMomentById(momentId+"", handler);
    }
    /**
     * 设置动态详情
     */
    private void setMomentDetails() {
        tvContent.setText(moment.getContent());
        tvName.setText(moment.getAuthorName());
        tvPubTime.setText(moment.getPublicTime());
        if (moment.getPicturePath() ==null || moment.getPicturePath().isEmpty()) {
            img.setVisibility(View.GONE);
        }
        else {
            loadImg(img, moment.getPicturePath());
        }

        loadImg(imgAvator, moment.getAuthorPic());
    }

    /**
     * 网络获取评论
     */
    private void getComment() {
        JsonResolve.getCommentByStyle(momentId+"","动态","1", handler);
    }

    /**
     * 设置评论信息
     */
    private void setComments() {
        CommentAdapter commentAdapter = new CommentAdapter();
        setListViewHeight(lvComment, commentAdapter, comments.size());
        lvComment.setAdapter(commentAdapter);
    }

    /**
     * 提交评论
     */
    private void commitComment() {
        String commentStr = etComment.getText().toString();
        JsonResolve.addComment("1", momentId+"", commentStr, "1.jpg", "动态", handler);
    }
    private void initView() {
        setTitleBarTitle("详情");

        imgAvator = (ImageView) findViewById(R.id.img_avatar);
        img = (ImageView) findViewById(R.id.img);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvPubTime = (TextView) findViewById(R.id.tv_pubTime);
        tvContent = (TextView) findViewById(R.id.tv_content);
        etComment = (EditText) findViewById(R.id.et_comment);
        btnCommit = (Button) findViewById(R.id.btn_commit);

        lvComment = (ListView) findViewById(R.id.lv_comment);
    }
    private void initEvent() {
        btnCommit.setOnClickListener(this);


        img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent viewImgIntent = new Intent(MomentDetailActivity.this, ViewImgActivity.class);
                viewImgIntent.putExtra("imgUrl", moment.getPicturePath());
                startActivity(viewImgIntent);
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commit:
                commitComment();
                break;

            default:
                break;
        }
    }

    class CommentAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return comments.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(MomentDetailActivity.this).inflate(R.layout.item_lv_moment_comment, null);
            }
            TextView tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
            TextView tvPubTime = (TextView) convertView.findViewById(R.id.tv_pubTime);

            ImageView imgAvator = (ImageView) convertView.findViewById(R.id.img_avatar);
            loadImg(imgAvator, comments.get(position).getAuthorPic());
            tvContent.setText(comments.get(position).getInformation());
//            tvPubTime.setText(DateUtils.getStandardDate(comments.get(position).getDate()));
            tvName.setText(comments.get(position).getAuthorName()+":");
            return convertView;
        }
    }


    private void setListViewHeight(ListView listView, CommentAdapter adapter, int count) {
        int totalHeight = 0;
        for (int i = 0; i < count; i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * count);
        listView.setLayoutParams(params);
    }


    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
