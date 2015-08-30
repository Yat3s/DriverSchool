package com.drivingevaluate.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.drivingevaluate.R;
import com.drivingevaluate.adapter.component.AutoRVAdapter;
import com.drivingevaluate.adapter.component.ViewHolder;
import com.drivingevaluate.model.Image;
import com.drivingevaluate.model.Moment;
import com.drivingevaluate.ui.MomentDetailActivity;
import com.drivingevaluate.ui.ViewImgActivity;
import com.drivingevaluate.util.DateUtils;
import com.drivingevaluate.util.Infliter;
import com.drivingevaluate.util.MyUtil;
import com.drivingevaluate.view.CustomImageView;
import com.drivingevaluate.view.NineGridlayout;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Yat3s on 8/28/15.
 * Email:hawkoyates@gmail.com
 */
public class MomentAdapter2 extends AutoRVAdapter {
    private Context context;

    public MomentAdapter2(Context context, List<?> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_lv_moment;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Moment moment = (Moment) list.get(position);
        holder.setTextView(R.id.name_tv, checkData(moment.getUser().getUserName()));
        holder.setTextView(R.id.comment_moment_tv, moment.getCommentCount() + "评论");
        holder.setTextView(R.id.content_moment_tv, checkData(moment.getDescription()));
        holder.setTextView(R.id.like_moment_tv, moment.getPraiseCount() + "赞");
        holder.setTextView(R.id.time_moment_tv, DateUtils.getStandardDate(moment.getCreateTime()));
        holder.setTextView(R.id.status_moment_tv, Infliter.statusInfliter(moment.getUser().getStatus()));

        //头像处理
        if (moment.getUser().getHeadPath() != null && !moment.getUser().getHeadPath().equals("")) {
            MyUtil.loadImg(holder.getImageView(R.id.avatar_img), moment.getUser().getHeadPath());
        } else {
            holder.getImageView(R.id.avatar_img).setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_user_default));
        }

        holder.setTextView(R.id.type_tv, "学员" + (moment.getUser().getGrade() + 1) + "级");
        //性别处理
        if (moment.getUser().getSex().equals("1")) {
            holder.getImageView(R.id.gender_img).setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_male_small));
            holder.getLinearLayout(R.id.gender_layout).setBackgroundColor(context.getResources().getColor(R.color.md_blue_200));
        } else {
            holder.getImageView(R.id.gender_img).setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_female_small));
            holder.getLinearLayout(R.id.gender_layout).setBackgroundColor(context.getResources().getColor(R.color.md_red_300));
        }
        //距离处理
        if (moment.getDistance() != null) {
            int distance = moment.getDistance().intValue();
            if (distance > 1000) {
                holder.setTextView(R.id.distance_moment_tv, new DecimalFormat("#.0").format(distance / 1000) + "km");
            } else {
                holder.setTextView(R.id.distance_moment_tv, new DecimalFormat("#").format(distance) + "m");
            }
        } else {
            holder.setTextView(R.id.distance_moment_tv, "");
        }

        //地址处理
        if (moment.getPubAddr() != null && !moment.getPubAddr().equals("null")) {
            holder.setTextView(R.id.address_moment_tv, moment.getPubAddr());
            holder.getImageView(R.id.address_moment_img).setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_current_loc));
        } else {
            holder.setTextView(R.id.address_moment_tv, "");
            holder.getImageView(R.id.address_moment_img).setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_null));
        }

        //图像处理
        List<Image> imgList = moment.getImgList();
        NineGridlayout nineGridlayout = holder.getNineGridLayout(R.id.images_gv);
        CustomImageView image = holder.getCustomImageView(R.id.one_iv);
        if (imgList.isEmpty() || imgList.isEmpty()) {
            nineGridlayout.setVisibility(View.GONE);
            image.setVisibility(View.GONE);
        } else if (imgList.size() == 1) {
            nineGridlayout.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            image.setClickable(true);
            image.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
            image.setImageUrl(imgList.get(0).getImgPath());
        } else {
            nineGridlayout.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);
            nineGridlayout.setImagesData(imgList);
        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewImgIntent = new Intent(context, ViewImgActivity.class);
                viewImgIntent.putExtra("imgUrl", moment.getImgPathsLimit());
                context.startActivity(viewImgIntent);
            }
        });

        holder.getImageButton(R.id.comment_moment_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MomentDetailActivity.class);
                intent.putExtra("momentId", moment.getId());
                context.startActivity(intent);
            }
        });
    }

    private String checkData(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }
}
