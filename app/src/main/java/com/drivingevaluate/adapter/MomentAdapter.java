package com.drivingevaluate.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.model.Image;
import com.drivingevaluate.model.Moment;
import com.drivingevaluate.ui.MomentDetailActivity;
import com.drivingevaluate.ui.ViewImgActivity;
import com.drivingevaluate.util.DateUtils;
import com.drivingevaluate.util.Infliter;
import com.drivingevaluate.util.MyUtil;
import com.drivingevaluate.view.CustomImageView;
import com.drivingevaluate.view.EmoticonsTextView;
import com.drivingevaluate.view.NineGridlayout;
import com.drivingevaluate.view.ScreenTools;

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
            vh.statusTv = (TextView) convertView.findViewById(R.id.status_moment_tv);
            vh.commentBtn = (ImageButton) convertView.findViewById(R.id.comment_moment_btn);
            vh.avatarImg = (ImageView) convertView.findViewById(R.id.avatar_img);
            vh.nineImages = (NineGridlayout) convertView.findViewById(R.id.images_gv);
            vh.oneIv = (CustomImageView) convertView.findViewById(R.id.one_iv);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder)convertView.getTag();
        }

        vh.nameTv.setText(checkData(moments.get(position).getUser().getUserName()));
        vh.commentAmountTv.setText(moments.get(position).getCommentCount() + "评论");
        vh.contentTv.setText(checkData(moments.get(position).getDescription()));
        vh.likeAmountTv.setText(moments.get(position).getPraiseCount() + "赞");
        vh.publicTimeTv.setText(DateUtils.getStandardDate(moments.get(position).getCreateTime()));
        vh.statusTv.setText(Infliter.statusInfliter(moments.get(position).getUser().getStatus()));

        //头像处理
        if (moments.get(position).getUser().getHeadPath() != null && !moments.get(position).getUser().getHeadPath().equals("")) {
            MyUtil.loadImg(vh.avatarImg, moments.get(position).getUser().getHeadPath());
        }
        else {
            vh.avatarImg.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_user_default));
        }
        //距离处理
        double distance = moments.get(position).getDistance();
        if (sort == 1) {
            vh.distanceTv.setText("");
        } else if (distance > 1000) {
            vh.distanceTv.setText(new DecimalFormat("#.0").format(distance / 1000) + "km");
        } else {
            vh.distanceTv.setText(new DecimalFormat("#").format(distance) + "m");
        }

        //图像处理
        List<Image> imgList = moments.get(position).getImgList();
        Log.e("Yat3s",position+":"+imgList.size());
        if (imgList.isEmpty() || imgList.isEmpty()) {
            vh.nineImages.setVisibility(View.GONE);
            vh.oneIv.setVisibility(View.GONE);
        } else if (imgList.size() == 1) {
            vh.nineImages.setVisibility(View.GONE);
            vh.oneIv.setVisibility(View.VISIBLE);
            vh.oneIv.setClickable(true);
            vh.oneIv.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
            vh.oneIv.setImageUrl(imgList.get(0).getImgPath());
//            handlerOneImage(vh, imgList.get(0));
        } else {
            vh.nineImages.setVisibility(View.VISIBLE);
            vh.oneIv.setVisibility(View.GONE);

            vh.nineImages.setImagesData(imgList);
        }

        vh.oneIv.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(context, MomentDetailActivity.class);
                intent.putExtra("momentId", moments.get(position).getId());
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
        TextView statusTv ;
        EmoticonsTextView contentTv;

        ImageView avatarImg;

        ImageButton commentBtn;

        NineGridlayout nineImages;
        public CustomImageView oneIv;
    }

    private String checkData(String str){
        if (str == null){
            return "";
        }
        return str;
    }


    private void handlerOneImage(ViewHolder viewHolder, Image image) {
        Log.e("Yat3sone",image.getUrl());
        int totalWidth;
        int imageWidth;
        int imageHeight;
        ScreenTools screentools = ScreenTools.instance(context);
        totalWidth = screentools.getScreenWidth() - screentools.dip2px(80);
        imageWidth = screentools.dip2px(image.getWidth());
        imageHeight = screentools.dip2px(image.getHeight());
        if (image.getWidth() <= image.getHeight()) {
            if (imageHeight > totalWidth) {
                imageHeight = totalWidth;
                imageWidth = (imageHeight * image.getWidth()) / image.getHeight();
            }
        } else {
            if (imageWidth > totalWidth) {
                imageWidth = totalWidth;
                imageHeight = (imageWidth * image.getHeight()) / image.getWidth();
            }
        }
        ViewGroup.LayoutParams layoutparams = viewHolder.oneIv.getLayoutParams();
        layoutparams.height = imageHeight;
        layoutparams.width = imageWidth;
        viewHolder.oneIv.setLayoutParams(layoutparams);
        viewHolder.oneIv.setClickable(true);
        viewHolder.oneIv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.oneIv.setImageUrl(image.getImgPath());

    }

}
