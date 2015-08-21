package com.drivingevaluate.ui.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.drivingevaluate.R;

/**
 * Created by Yat3s on 8/16/15.
 * Email:hawkoyates@gmail.com
 */
public class LuckMoneyDialogFragment extends DialogFragment {
    private View rootView;
    private AlertDialog.Builder builder;
    private int money;
    private TextView moneyTv;
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
        return builder.create();
    }
}
