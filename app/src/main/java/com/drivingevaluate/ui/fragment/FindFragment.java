package com.drivingevaluate.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.cocosw.bottomsheet.BottomSheet;
import com.drivingevaluate.R;
import com.drivingevaluate.ui.GetMoneyActivity;
import com.drivingevaluate.ui.MomentActivity;
import com.drivingevaluate.ui.base.Yat3sFragment;

public class FindFragment extends Yat3sFragment implements OnClickListener{
    private View root;
    private RelativeLayout rlKnowledge,rlSub1,rlSub4,consultRl,momentRl,moneyLayout;
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
        rlKnowledge = (RelativeLayout) root.findViewById(R.id.btn_knowledge);
        rlSub1 = (RelativeLayout) root.findViewById(R.id.btn_sub1);
        rlSub4 = (RelativeLayout) root.findViewById(R.id.btn_sub4);
        consultRl = (RelativeLayout) root.findViewById(R.id.consult_rl);
        momentRl = (RelativeLayout) root.findViewById(R.id.moment_rl);
        moneyLayout = (RelativeLayout) root.findViewById(R.id.luckyMoney_layout);
    }
    private void initEvent() {
        rlKnowledge.setOnClickListener(this);
        rlSub1.setOnClickListener(this);
        rlSub4.setOnClickListener(this);
        consultRl.setOnClickListener(this);
        momentRl.setOnClickListener(this);
        moneyLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.moment_rl:
                startActivity(MomentActivity.class);
                break;
            case R.id.btn_knowledge:
                showShortToast("开发中,敬请期待");
                break;
            case R.id.btn_sub1:
                showShortToast("开发中,敬请期待");
                break;
            case R.id.btn_sub4:
                showShortToast("开发中,敬请期待");
                break;
            case R.id.consult_rl:
                consult();
                break;
            case R.id.luckyMoney_layout:
                startActivity(GetMoneyActivity.class);
                break;
            default:
                break;
        }
    }

    private void consult() {
        new BottomSheet.Builder(getActivity()).title("选择咨询方式").sheet(R.menu.menu_consult).icon(R.mipmap.ic_servicer).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.call:
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+"15170777774")));
                        break;
                    case R.id.qq:
                        String url11 = "mqqwpa://im/chat?chat_type=wpa&uin=4645643&version=1";
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url11)));
                        break;
                }
            }
        }).show();
    }
}
