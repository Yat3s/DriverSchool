package com.drivingevaluate.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drivingevaluate.R;
import com.drivingevaluate.ui.MainActivity;
import com.drivingevaluate.ui.base.Yat3sFragment;

/**
 * Created by Yat3s on 8/31/15.
 * Email:hawkoyates@gmail.com
 */
public class Welcome4 extends Yat3sFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.welcome4, container, false);
        root.findViewById(R.id.enter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.class);
                getActivity().finish();
            }
        });
        return root;
    }
}
