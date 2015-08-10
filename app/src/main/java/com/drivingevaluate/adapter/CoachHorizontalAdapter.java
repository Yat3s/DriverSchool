package com.drivingevaluate.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.model.Coach;
import com.drivingevaluate.ui.CoachInfoActivity;
import com.drivingevaluate.util.MyUtil;

import java.util.List;

/**
 * Created by Yat3s on 8/9/15.
 * Email:hawkoyates@gmail.com
 */
public class CoachHorizontalAdapter extends RecyclerView.Adapter<CoachHorizontalAdapter.ViewHolder> {

    private List<Coach> coaches;
    private Context context;

    public CoachHorizontalAdapter(List<Coach> coaches, Context context) {
        this.coaches = coaches;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_coach_horizontal_rv, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.coachNameTv.setText(coaches.get(position).getSellerName());
        holder.subjectTv.setText(coaches.get(position).getGoodsTitle());
        MyUtil.loadImg(holder.coachAvatarImg,coaches.get(position).getPhotoPath());
    }

    @Override
    public int getItemCount() {
        return coaches.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView coachNameTv;
        public ImageView coachAvatarImg;
        public TextView subjectTv;

        public ViewHolder(View itemView) {
            super(itemView);
            coachNameTv = (TextView) itemView.findViewById(R.id.name_coach_tv);
            subjectTv = (TextView) itemView.findViewById(R.id.subject_coach_tv);
            coachAvatarImg = (ImageView) itemView.findViewById(R.id.coach_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getPosition();
                    Intent coachInfoIntent = new Intent(context, CoachInfoActivity.class);
                    coachInfoIntent.putExtra("coachName",coaches.get(position).getSellerName());
                    coachInfoIntent.putExtra("coachAvatarUrl",coaches.get(position).getPhotoPath());
                    context.startActivity(coachInfoIntent);
                }
            });
        }
    }
}
