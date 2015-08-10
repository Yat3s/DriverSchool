package com.drivingevaluate.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.model.Course;

import java.util.List;

/**
 * Created by Yat3s on 8/10/15.
 * Email:hawkoyates@gmail.com
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{
    private List<Course> courses;

    public CourseAdapter(List<Course> courses) {
        this.courses = courses;
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
        holder.priceTv.setText(courses.get(position).getPrice()+"");
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
        }
    }
}
