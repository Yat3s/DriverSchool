package com.drivingevaluate.net.component;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.drivingevaluate.ui.LoginActivity;
import com.drivingevaluate.util.SharedPreferencesUtils;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

/**
 * Created on:15/8/11
 * Email:brucewzp@gmail.com
 **/
public class RequestErrorHandler {
    private Context context;

    public RequestErrorHandler(Context context) {
        this.context = context;
    }

    public void handError(Response response) throws IOException, JSONException {
        int code = response.code();
        switch (code) {
            case 401:
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "请先登录", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
                break;
            case 422:
                String responseString = response.body().string();
                JSONObject responseObj = new JSONObject(responseString);
                String msg = responseObj.getString("message");
                showToast(msg);
                break;
            case 500:
                showToast("服务器内部错误");
                break;
            default:
                showToast("未知错误");
                break;
        }
    }

    public void handError(RetrofitError response) throws IOException, JSONException {
        Log.e("Yat3s","APIError"+response.getMessage());
        if (response.getKind() == RetrofitError.Kind.NETWORK) {
            showToast("网络连接异常");
            return;
        }
        int code = response.getResponse().getStatus();
        switch (code) {
            case 401:
                Toast.makeText(context, "请先登录", Toast.LENGTH_LONG).show();
                if (SharedPreferencesUtils.contains(context,"token")) {
                    SharedPreferencesUtils.remove(context, "token");
                    SharedPreferencesUtils.remove(context, "userId");
                }
                Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
                context.startActivity(intent);
                break;
            case 422:
                String responseString = new String(((TypedByteArray) response.getResponse().getBody()).getBytes());
                JSONObject responseObj = new JSONObject(responseString);
                String msg = responseObj.getString("message");
                showToast(msg);
                break;
            case 500:
                showToast("服务器内部错误");
                break;
            default:
                showToast("未知错误");
                break;
        }
    }


    public void showToast(final String str) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, str, Toast.LENGTH_LONG).show();
            }
        });
    }

}

