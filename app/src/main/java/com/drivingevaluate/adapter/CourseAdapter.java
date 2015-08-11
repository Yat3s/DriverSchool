package com.drivingevaluate.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.drivingevaluate.R;
import com.drivingevaluate.model.Course;
import com.drivingevaluate.ui.ApplyDSchoolActivity;

import java.util.List;

/**
 * Created by Yat3s on 8/10/15.
 * Email:hawkoyates@gmail.com
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{
    private List<Course> courses;
    private Context context;

    public CourseAdapter(List<Course> courses, Context context) {
        this.courses = courses;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_price_rv,null);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.typeTv.setText(courses.get(position).getType());
        holder.subjectTv.setText(courses.get(position).getSubject());
        holder.priceTv.setText("￥"+courses.get(position).getPrice()+"元");
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView typeTv;
        public TextView subjectTv;
        public TextView priceTv;
        public ViewHolder(View itemView) {
            super(itemView);
            typeTv = (TextView) itemView.findViewById(R.id.type_class_tv);
            subjectTv = (TextView) itemView.findViewById(R.id.subject_class_tv);
            priceTv = (TextView) itemView.findViewById(R.id.price_class_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    int i = getPosition();
//                    Intent signUpIntent = new Intent(context,ApplyDSchoolActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("sName",courses.get(i).getMerchantName());
//                    bundle.putInt("sid",courses.get(i).getMerchantId());
//                    signUpIntent.putExtras(bundle);
//                    context.startActivity(signUpIntent);
                    Toast.makeText(context,"请先选择教练后报名",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
