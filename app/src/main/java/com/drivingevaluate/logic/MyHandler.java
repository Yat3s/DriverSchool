package com.drivingevaluate.logic;

import java.io.Serializable;

import android.os.Handler;
import android.os.Message;

import com.drivingevaluate.ui.base.Yat3sActivity;

public class MyHandler extends Handler
        implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Yat3sActivity context;

    public MyHandler(Yat3sActivity paramBaseActivity)
    {
        this.context = paramBaseActivity;
    }

    public void handleMessage(Message paramMessage)
    {
        switch (paramMessage.what)
        {
            default:
            case 65536:
        }
        while (true)
        {
            super.handleMessage(paramMessage);
            return;
        }
    }
}