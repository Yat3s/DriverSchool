package com.drivingevaluate.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import com.drivingevaluate.R;
import com.drivingevaluate.config.AppConf;
import com.drivingevaluate.model.Comment;
import com.drivingevaluate.model.Moment;
import com.drivingevaluate.net.GetCommentListRequester;
import com.drivingevaluate.net.GetMomentDetailRequester;
import com.drivingevaluate.net.LikeMomentRequester;
import com.drivingevaluate.net.PostCommentRequester;
import com.drivingevaluate.net.component.RequestErrorHandler;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.util.DateUtils;

import org.json.JSONException;

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
    private List<Comment> mComments = new ArrayList<>();
    private CommentAdapter commentAdapter;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_moment_detail);
        ButterKnife.bind(this);
        setToolbarWithNavigation(toolbar, "动态详情");
        initView();
        initEvent();

        getData();
    }

    private void getData() {
        momentId = getIntent().getExtras().getInt("momentId");
        getMoment();
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
                getComment(mMoment.getFirstReply());
            }

            @Override
            public void failure(RetrofitError error) {
                RequestErrorHandler requestErrorHandler = new RequestErrorHandler(MomentDetailActivity.this);
                try {
                    requestErrorHandler.handError(error);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
    private void getComment(Long timestamp) {
        Callback<List<Comment>> callback = new Callback<List<Comment>>() {
            @Override
            public void success(List<Comment> comments, Response response) {
                mComments.clear();
                mComments.addAll(comments);
                setListViewHeight(lvComment, commentAdapter, mComments.size());
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                RequestErrorHandler requestErrorHandler = new RequestErrorHandler(MomentDetailActivity.this);
                try {
                    requestErrorHandler.handError(error);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Map<String,Object> param = new HashMap<>();
        param.put("publishId", mMoment.getId());
        param.put("firstReply", timestamp);

        GetCommentListRequester getCommentListRequester = new GetCommentListRequester(callback,param);
        getCommentListRequester.request();
    }


    /**
     * 点赞
     */
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
                RequestErrorHandler requestErrorHandler = new RequestErrorHandler(MomentDetailActivity.this);
                try {
                    requestErrorHandler.handError(error);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Map<String,Object> param = new HashMap<>();
        param.put("userId",AppConf.USER_ID);
        param.put("publishId",mMoment.getId());
        LikeMomentRequester likeMomentRequester = new LikeMomentRequester(callback,param);
        likeMomentRequester.request();
    }
    /**
     * 提交评论
     */
    private void commitComment() {
        String commentContent = etComment.getText().toString();
        Callback<String> callback = new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                showShortToast("评论成功");
                etComment.setText("");
                getMoment();
                hideSoftInputView();
            }

            @Override
            public void failure(RetrofitError error) {
                RequestErrorHandler requestErrorHandler = new RequestErrorHandler(MomentDetailActivity.this);
                try {
                    requestErrorHandler.handError(error);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Map<String,Object> param = new HashMap<>();
        param.put("publishId",mMoment.getId());
        param.put("userId",AppConf.USER_ID);
        param.put("content",commentContent);

        PostCommentRequester postCommentRequester = new PostCommentRequester(callback,param);
        postCommentRequester.request();
    }
    private void initView() {

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
        commentAdapter = new CommentAdapter();
        lvComment.setAdapter(commentAdapter);
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



    class CommentAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mComments.size();
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
            loadImg(imgAvator, mComments.get(position).getUser().getHeadPath());
            tvContent.setText(mComments.get(position).getContent());
            tvPubTime.setText(DateUtils.getStandardDate(mComments.get(position).getCreatedTime()));
            tvName.setText(mComments.get(position).getUser().getUserName());
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
