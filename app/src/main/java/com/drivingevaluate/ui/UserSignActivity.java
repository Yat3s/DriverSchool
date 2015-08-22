package com.drivingevaluate.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.drivingevaluate.R;
import com.drivingevaluate.ui.base.Yat3sActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yat3s on 8/22/15.
 * Email:hawkoyates@gmail.com
 */
public class UserSignActivity extends Yat3sActivity {
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.content_sign_et)
    EditText signEt;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_user_sign);
        ButterKnife.bind(this);
        setToolbarWithNavigation(toolbar,"我的签名");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_right, menu);
        MenuItem item = menu.findItem(R.id.right_toolbar_menu);
        item.setTitle("保存");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.right_toolbar_menu){
            Intent intent = getIntent();
            intent.putExtra("sign",signEt.getText().toString());
            setResult(100,intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
