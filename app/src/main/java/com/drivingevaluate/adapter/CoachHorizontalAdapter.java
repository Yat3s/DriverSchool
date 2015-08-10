package com.drivingevaluate.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.model.Coach;
import com.drivingevaluate.ui.CoachInfoActivity;

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
    }

    @Override
    public int getItemCount() {
        return coaches.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView coachNameTv;

        public ViewHolder(View itemView) {
            super(itemView);
            coachNameTv = (TextView) itemView.findViewById(R.id.name_coach_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getPosition();
                    Intent coachInfoIntent = new Intent(context, CoachInfoActivity.class);
                    coachInfoIntent.putExtra("coachName",coaches.get(position).getSellerName());
                    context.startActivity(coachInfoIntent);
                }
            });
        }
    }
}
