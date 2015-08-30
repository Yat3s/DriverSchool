package com.drivingevaluate.ui.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;

/**
 * Created by Yat3s on 8/16/15.
 * Email:hawkoyates@gmail.com
 */
public class LuckMoneyDialogFragment extends DialogFragment {
    private View rootView;
    private AlertDialog.Builder builder;
    private int money;
    private TextView moneyTv;
    private Button shareBtn;
    public static LuckMoneyDialogFragment newInstance(int money) {
        LuckMoneyDialogFragment dialog = new LuckMoneyDialogFragment();
        dialog.money = money;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.dialog_money, null);
        builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView);
        moneyTv = (TextView) rootView.findViewById(R.id.sum_of_money_tv);
        moneyTv.setText(money);
        shareBtn = (Button) rootView.findViewById(R.id.share);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
                UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(getActivity(), "100424468",
                        "c7394704798a158208a74ab60104f0ba");
                qqSsoHandler.addToSocialSDK();
                QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(getActivity(), "100424468",
                        "c7394704798a158208a74ab60104f0ba");
                qZoneSsoHandler.addToSocialSDK();

                QQShareContent qqShareContent = new QQShareContent();
                qqShareContent.setTargetUrl("http://www.51jkdp.com");
                qqShareContent.setShareContent("我抽到" + money + "元的红包,你也快来抢吧");

                QZoneShareContent qZoneShareContent = new QZoneShareContent();
                qZoneShareContent.setTargetUrl("http://www.51jkdp.com");
                qZoneShareContent.setShareContent("我抽到" + money + "元的红包,你也快来抢吧");
                mController.setShareMedia(qqShareContent);
                mController.setShareMedia(qZoneShareContent);
                mController.openShare(getActivity(), false);
            }
        });
        return builder.create();
    }
}
