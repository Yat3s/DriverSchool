package com.drivingevaluate.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.drivingevaluate.R;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * WebView控件
 *
 * @author 402-9
 */
public class WebView extends Yat3sActivity {
    private android.webkit.WebView webView;
    private String url, title;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub  
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        setToolbarWithNavigation(toolbar, title);
        webView = (android.webkit.WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);//设置使用够执行JS脚本
        webView.getSettings().setBuiltInZoomControls(true);//设置使支持缩放  
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                // TODO Auto-generated method stub  
                view.loadUrl(url);// 使用当前WebView处理跳转  
                return true;//true表示此事件在此处被处理，不需要再广播  
            }

            @Override   //转向错误时的处理  
            public void onReceivedError(android.webkit.WebView view, int errorCode,
                                        String description, String failingUrl) {
                // TODO Auto-generated method stub  
                Toast.makeText(WebView.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override   //默认点回退键，会退出Activity，需监听按键操作，使回退在WebView内发生  
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub  
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {
            final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
            UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "100424468",
                    "c7394704798a158208a74ab60104f0ba");
            qqSsoHandler.addToSocialSDK();
            QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, "100424468",
                    "c7394704798a158208a74ab60104f0ba");
            qZoneSsoHandler.addToSocialSDK();

            QQShareContent qqShareContent = new QQShareContent();
            qqShareContent.setTargetUrl(url);
            qqShareContent.setShareContent(title);

            QZoneShareContent qZoneShareContent = new QZoneShareContent();
            qZoneShareContent.setTargetUrl(url);
            qZoneShareContent.setShareContent(title);
            mController.setShareMedia(qqShareContent);
            mController.setShareMedia(qZoneShareContent);
            mController.openShare(this, false);
        }
        return super.onOptionsItemSelected(item);
    }
}  