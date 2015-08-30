package com.drivingevaluate.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.ui.base.Yat3sFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ExaminationFragment extends Yat3sFragment {
    private View root;
    @Bind(R.id.sub1_tv)
    TextView sub1Tv;
    @Bind(R.id.random_sub1_layout)
    LinearLayout randomSub1Layout;
    @Bind(R.id.hard_sub1_layout)
    LinearLayout hardSub1Layout;
    @Bind(R.id.special_sub1_layout)
    LinearLayout specialSub1Layout;
    @Bind(R.id.mistake_sub1_layout)
    LinearLayout mistakeSub1Layout;

    @Bind(R.id.sub4_tv)
    TextView sub4Tv;
    @Bind(R.id.random_sub4_layout)
    LinearLayout randomSub4Layout;
    @Bind(R.id.hard_sub4_layout)
    LinearLayout hardSub4Layout;
    @Bind(R.id.special_sub4_layout)
    LinearLayout specialSub4Layout;
    @Bind(R.id.mistake_sub4_layout)
    LinearLayout mistakeSub4Layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_examination, container, false);
        ButterKnife.bind(this, root);
        initView();
        initEvent();
        return root;
    }

    private void initView() {

    }
    private void initEvent() {

    }

    @OnClick({R.id.sub1_tv, R.id.random_sub1_layout, R.id.hard_sub1_layout, R.id.special_sub1_layout, R.id.mistake_sub1_layout, R.id.sub4_tv, R.id.random_sub4_layout,
            R.id.hard_sub4_layout, R.id.special_sub4_layout, R.id.mistake_sub4_layout})
    void click() {
        showShortToast("即将开放,敬请期待");
    }
}
