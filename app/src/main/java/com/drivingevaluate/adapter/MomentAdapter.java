package com.drivingevaluate.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.model.Moment;
import com.drivingevaluate.ui.MomentDetailActivity;
import com.drivingevaluate.ui.ViewImgActivity;
import com.drivingevaluate.util.DateUtils;
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

            vh.nameTv = (TextView) convertView.findViewById(R.id.name_tv);
            vh.commentAmountTv = (TextView) convertView.findViewById(R.id.comment_moment_tv);
            vh.contentTv = (EmoticonsTextView) convertView.findViewById(R.id.content_moment_tv);
            vh.distanceTv = (TextView) convertView.findViewById(R.id.distance_moment_tv);
            vh.likeAmountTv = (TextView) convertView.findViewById(R.id.like_moment_tv);
            vh.publicTimeTv = (TextView) convertView.findViewById(R.id.time_moment_tv);
            vh.commentBtn = (ImageButton) convertView.findViewById(R.id.comment_moment_btn);
            vh.avatarImg = (ImageView) convertView.findViewById(R.id.avatar_img);
            vh.mainImg = (ImageView) convertView.findViewById(R.id.img);

            convertView.setTag(vh);

        }else {
            vh = (ViewHolder)convertView.getTag();
        }

        vh.nameTv.setText(checkData(moments.get(position).getUser().getUserName()));
        vh.commentAmountTv.setText(moments.get(position).getCommentCount() + "评论");
        vh.contentTv.setText(checkData(moments.get(position).getDescription()));
        vh.likeAmountTv.setText(moments.get(position).getPraiseCount() + "赞");
        vh.publicTimeTv.setText(DateUtils.getStandardDate(moments.get(position).getCreateTime()));

        if (moments.get(position).getUser().getHeadPath() != null) {
//            MyUtil.loadImg(vh.avatarImg, moments.get(position).getUser().getHeadPath());
        }

        if (!checkData(moments.get(position).getImgPathsLimit()).isEmpty()) {
            ViewGroup.LayoutParams para =  vh.mainImg.getLayoutParams();
            para.height = 200;
            para.width = 200;
            vh.mainImg.setLayoutParams(para);
            MyUtil.loadImg(vh.mainImg, moments.get(position).getImgPathsLimit());
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
                viewImgIntent.putExtra("imgUrl", moments.get(position).getImgPathsLimit());
                context.startActivity(viewImgIntent);
            }
        });

        vh.commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MomentDetailActivity.class);
                intent.putExtra("momentId",moments.get(position).getId());
                context.startActivity(intent);
            }
        });

        return convertView;
    }


    private class ViewHolder{
        TextView likeAmountTv ;
        TextView commentAmountTv ;
        TextView distanceTv ;
        TextView nameTv ;
        TextView publicTimeTv ;
        EmoticonsTextView contentTv;

        ImageView avatarImg;
        ImageView mainImg;

        ImageButton commentBtn;
    }

    private String checkData(String str){
        if (str == null){
            return "";
        }
        return str;
    }
}
