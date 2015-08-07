package com.drivingevaluate.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.model.Moment;
import com.drivingevaluate.ui.MomentDetailActivity;
import com.drivingevaluate.ui.ViewImgActivity;
import com.drivingevaluate.util.MyUtil;
import com.drivingevaluate.view.EmoticonsTextView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Yat3s on 15/8/4.
 */
public class MomentAdapter extends BaseAdapter{
    private Context context;
    private List<Moment> moments;
    private ViewHolder vh;
    private int sort;
    public MomentAdapter(Context mContext, List<Moment> mDatas,int sort) {
        this.context = mContext;
        this.moments = mDatas;
        this.sort = sort;
    }

    @Override
    public int getCount() {
        return moments.size();
    }

    @Override
    public Object getItem(int position) {
        return moments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_moment,null);
            vh = new ViewHolder();

            vh.nameTv = (TextView) convertView.findViewById(R.id.tv_name);
            vh.commentAmountTv = (TextView) convertView.findViewById(R.id.tv_commentAmount);
            vh.contentTv = (EmoticonsTextView) convertView.findViewById(R.id.tv_content);
            vh.distanceTv = (TextView) convertView.findViewById(R.id.tvDistance);
            vh.likeAmountTv = (TextView) convertView.findViewById(R.id.tv_likeAmount);
            vh.likeTagTv = (TextView) convertView.findViewById(R.id.tv_likeTag);
            vh.publicTimeTv = (TextView) convertView.findViewById(R.id.tv_pubTime);

            vh.avatorImg = (ImageView) convertView.findViewById(R.id.img_avator);
            vh.mainImg = (ImageView) convertView.findViewById(R.id.img);

            vh.commentLayout = (LinearLayout) convertView.findViewById(R.id.ll_comment);
            vh.likeLayout = (LinearLayout) convertView.findViewById(R.id.ll_like);

            convertView.setTag(vh);

        }else {
            vh = (ViewHolder)convertView.getTag();
        }

        vh.nameTv.setText(moments.get(position).getAuthorName());
        vh.commentAmountTv.setText("(" + moments.get(position).getCommentAmount() + ")");
        vh.contentTv.setText(moments.get(position).getContent());
        vh.likeAmountTv.setText("(" + moments.get(position).getLikeAmount() + ")");


        MyUtil.loadImg(vh.avatorImg, moments.get(position).getAuthorPic());

        if (!moments.get(position).getPicturePath().isEmpty()) {
            ViewGroup.LayoutParams para =  vh.mainImg.getLayoutParams();
            para.height = 200;
            para.width = 200;
            vh.mainImg.setLayoutParams(para);
            MyUtil.loadImg(vh.mainImg, "http://121.43.234.220:8090/upload/"+moments.get(position).getPicturePath());
        }
        else {
            ViewGroup.LayoutParams para =  vh.mainImg.getLayoutParams();
            para.height = 0;
            para.width = 0;
            vh.mainImg.setLayoutParams(para);
        }

        double distance = moments.get(position).getDistance();

        if (sort == 1) {
            vh.distanceTv.setText("");
        } else if (distance > 1000) {
            vh.distanceTv.setText(new DecimalFormat("#.0").format(distance / 1000) + "km");
        } else {
            vh.distanceTv.setText(new DecimalFormat("#").format(distance) + "m");
        }

        vh.mainImg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent viewImgIntent = new Intent(context, ViewImgActivity.class);
                viewImgIntent.putExtra("imgUrl", moments.get(position).getPicturePath());
                context.startActivity(viewImgIntent);
            }
        });

        vh.likeLayout.setOnClickListener(new lvButtonListener(position, vh.likeAmountTv, vh.likeTagTv));
        vh.commentLayout.setOnClickListener(new lvButtonListener(position));
        return convertView;
    }

    class lvButtonListener implements View.OnClickListener {
        private int position;
        private Boolean liked = false;
        private TextView tvLikeAmout, tvLikeTag;

        lvButtonListener(int pos) {
            position = pos;
        }

        lvButtonListener(int pos, TextView tv, TextView tvTag) {
            position = pos;
            tvLikeAmout = tv;
            tvLikeTag = tvTag;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_like:
//                    JsonResolve.likeMoment(moments.get(position).getId() + "", "1", handler);
                    if (!liked) {
                        liked = true;
                        tvLikeTag.setText("已赞");
                        TextPaint tpaint = tvLikeTag.getPaint();
                        tpaint.setFakeBoldText(true);
                        tvLikeAmout.setText("(" + (moments.get(position).getLikeAmount() + 1) + ")");
                    } else {
                        liked = false;
                        tvLikeTag.setText("赞");
                        TextPaint myPaint = tvLikeTag.getPaint();
                        myPaint.setFakeBoldText(false);
                        tvLikeAmout.setText("(" + moments.get(position).getLikeAmount() + ")");
                    }
                    break;
                case R.id.ll_comment:
                    Intent commentIntent = new
                            Intent(context,MomentDetailActivity.class);
                    commentIntent.putExtra("momentId",
                            moments.get(position).getId());
                    context.startActivity(commentIntent);
                    break;
                default:
                    break;
            }

        }
    }



    private class ViewHolder{
        TextView likeAmountTv ;
        TextView commentAmountTv ;
        TextView likeTagTv ;
        TextView distanceTv ;
        TextView nameTv ;
        TextView publicTimeTv ;
        EmoticonsTextView contentTv;

        ImageView avatorImg;
        ImageView mainImg;

        LinearLayout likeLayout;
        LinearLayout commentLayout;
    }
}
