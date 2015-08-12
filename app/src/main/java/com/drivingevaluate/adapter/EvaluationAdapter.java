package com.drivingevaluate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.model.Evaluation;
import com.drivingevaluate.util.DateUtils;

import java.util.List;

/**
 * Created by Yat3s on 8/11/15.
 * Email:hawkoyates@gmail.com
 */
public class EvaluationAdapter extends RecyclerView.Adapter<EvaluationAdapter.ViewHolder>{

    private List<Evaluation> evaluations;
    private Context context;

    public EvaluationAdapter(List<Evaluation> evaluations, Context context) {
        this.evaluations = evaluations;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evaluation_rv,null);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameTv.setText(evaluations.get(position).getUser().getUserName());
        holder.pubTimeTv.setText(DateUtils.getStandardDate(evaluations.get(position).getCreateTime()));
        holder.contentTv.setText(evaluations.get(position).getJudgeWord());

        holder.gradeRb.setRating(evaluations.get(position).getAvgGrade());
    }

    @Override
    public int getItemCount() {
        return evaluations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTv;
        private TextView pubTimeTv;
        private TextView contentTv;

        private RatingBar gradeRb;
        public ViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.name_tv);
            pubTimeTv = (TextView) itemView.findViewById(R.id.pubTime_tv);
            contentTv = (TextView) itemView.findViewById(R.id.content_tv);

            gradeRb = (RatingBar) itemView.findViewById(R.id.grade_rb);
        }
    }
}
