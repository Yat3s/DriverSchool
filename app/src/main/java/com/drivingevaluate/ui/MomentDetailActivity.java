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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.drivingevaluate.R;
import com.drivingevaluate.api.JsonResolve;
import com.drivingevaluate.config.AppConf;
import com.drivingevaluate.config.StateConfig;
import com.drivingevaluate.model.Comment;
import com.drivingevaluate.model.Moment;
import com.drivingevaluate.net.GetMomentDetailRequester;
import com.drivingevaluate.net.LikeMomentRequester;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.util.DateUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author Yat3s
 *
 */
public class MomentDetailActivity extends Yat3sActivity implements OnClickListener{
    private ImageView avatarImg,img,likeImg;
    private TextView tvName,tvPubTime,tvContent,likeCountTv,commentCountTv,likeTv;
    private ListView lvComment;
    private EditText etComment;
    private Button btnCommit;
    private LinearLayout likeLayout;
    private int momentId;

    private Moment mMoment;
    private List<Comment> comments;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
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

        getData();
    }

    private void getData() {
        momentId = getIntent().getIntExtra("momentId", -1);
        getMoment();
//        getComment();
    }

    /**
     * 网络获取Moments详情
     */
    private void getMoment() {
        Callback<Moment> callback = new Callback<Moment>() {
            @Override
            public void success(Moment moment, Response response) {
                mMoment = moment;
                setMomentDetails();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        };

        Map<String,Object> param = new HashMap<>();
        param.put("publishId",momentId);
        param.put("userId", AppConf.USER_ID);
        GetMomentDetailRequester getMomentDetailRequester = new GetMomentDetailRequester(callback,param);
        getMomentDetailRequester.request();
    }
    /**
     * 设置动态详情
     */
    private void setMomentDetails() {
        tvContent.setText(mMoment.getDescription());
        tvName.setText(mMoment.getUser().getUserName());
        tvPubTime.setText(DateUtils.getStandardDate(mMoment.getCreateTime()));
        likeCountTv.setText(mMoment.getPraiseCount() + "赞");
        commentCountTv.setText(mMoment.getCommentCount() + "评论");
        if (mMoment.getImgPathsLimit() ==null || mMoment.getImgPathsLimit().isEmpty()) {
            img.setVisibility(View.GONE);
        }
        else {
            loadImg(img, mMoment.getImgPathsLimit());
        }

        if (mMoment.isPraised()){
            likeTv.setText("已赞");
        }else {
            likeImg.setImageDrawable(getResources().getDrawable(R.mipmap.ic_like));
        }

        loadImg(avatarImg, mMoment.getUser().getHeadPath());
    }

    /**
     * 网络获取评论
     */
    private void getComment() {
        JsonResolve.getCommentByStyle(momentId + "", "动态", "1", handler);
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
        JsonResolve.addComment("1", momentId + "", commentStr, "1.jpg", "动态", handler);
    }
    private void initView() {
        setTitleBarTitle("详情");

        avatarImg = (ImageView) findViewById(R.id.avatar_img);
        img = (ImageView) findViewById(R.id.img);
        tvName = (TextView) findViewById(R.id.name_tv);
        tvPubTime = (TextView) findViewById(R.id.time_moment_tv);
        tvContent = (TextView) findViewById(R.id.content_moment_tv);
        etComment = (EditText) findViewById(R.id.et_comment);
        btnCommit = (Button) findViewById(R.id.btn_commit);
        likeCountTv = (TextView) findViewById(R.id.like_count_moment_tv);
        commentCountTv = (TextView) findViewById(R.id.comment_count_moment_tv);
        
        likeTv = (TextView) findViewById(R.id.like_moment_tv);
        likeImg = (ImageView) findViewById(R.id.like_moment_img);
        likeLayout = (LinearLayout) findViewById(R.id.like_layout);

        lvComment = (ListView) findViewById(R.id.lv_comment);
    }
    private void initEvent() {
        btnCommit.setOnClickListener(this);
        likeLayout.setOnClickListener(this);

        img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent viewImgIntent = new Intent(MomentDetailActivity.this, ViewImgActivity.class);
                viewImgIntent.putExtra("imgUrl", mMoment.getImgPathsLimit());
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
            case R.id.like_layout:
                likeMoment();
                break;
            default:
                break;
        }
    }

    private void likeMoment() {
        Callback<String> callback = new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                likeImg.setVisibility(View.INVISIBLE);
                likeTv.setText("已赞");
                likeCountTv.setText((mMoment.getPraiseCount()+1) + "赞");
            }

            @Override
            public void failure(RetrofitError error) {

            }
        };

        Map<String,Object> param = new HashMap<>();
        param.put("userId",AppConf.USER_ID);
        param.put("publishId",mMoment.getId());
        LikeMomentRequester likeMomentRequester = new LikeMomentRequester(callback,param);
        likeMomentRequester.request();
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
