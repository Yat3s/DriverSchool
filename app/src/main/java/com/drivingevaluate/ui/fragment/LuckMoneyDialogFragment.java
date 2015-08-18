package com.drivingevaluate.ui.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.drivingevaluate.R;

/**
 * Created by Yat3s on 8/16/15.
 * Email:hawkoyates@gmail.com
 */
public class LuckMoneyDialogFragment extends DialogFragment {
    private View rootView;
    private AlertDialog.Builder builder;

    public static LuckMoneyDialogFragment newInstance() {
        LuckMoneyDialogFragment dialog = new LuckMoneyDialogFragment();
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.dialog_money, null);

        builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView);
        return builder.create();
    }
}
