package com.drivingevaluate.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.drivingevaluate.R;
import com.drivingevaluate.ui.base.Yat3sFragment;
import com.drivingevaluate.ui.MomentActivity;

public class FindFragment extends Yat3sFragment implements OnClickListener{
    private View root;
    private ImageView imgMoment;
    private RelativeLayout rlKnowledge,rlSub1,rlSub4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_find, container, false);
        }
        initView();
        initEvent();
        return root;
    }

    private void initView() {
        imgMoment = (ImageView) root.findViewById(R.id.img_moment);

        rlKnowledge = (RelativeLayout) root.findViewById(R.id.btn_knowledge);
        rlSub1 = (RelativeLayout) root.findViewById(R.id.btn_sub1);
        rlSub4 = (RelativeLayout) root.findViewById(R.id.btn_sub4);
    }
    private void initEvent() {
        imgMoment.setOnClickListener(this);
        rlKnowledge.setOnClickListener(this);
        rlSub1.setOnClickListener(this);
        rlSub4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_moment:
                startActivity(MomentActivity.class);
                break;
            case R.id.btn_knowledge:
                showShortToast("开发中");
                break;
            case R.id.btn_sub1:
                showShortToast("开发中");
                break;
            case R.id.btn_sub4:
                showShortToast("开发中");
                break;
            default:
                break;
        }
    }




}
