package com.drivingevaluate.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.drivingevaluate.R;
import com.drivingevaluate.app.DEApplication;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.ui.fragment.FindFragment;
import com.drivingevaluate.ui.fragment.MerchantFragment;
import com.drivingevaluate.ui.fragment.UserFragment;

public class MainActivity extends Yat3sActivity implements OnClickListener {
	private LinearLayout mTabFind;
	private LinearLayout mTabUser;
	private ImageView mTabMarket;

	private ImageButton mImgFind;
	private ImageButton mImgUser;
	
	Fragment findFragment;
	Fragment dSchoolFragment;
	Fragment userFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initEvent();
		Loc();
		setSelect(0);
	}

	private void initEvent() {
		//开启定位
		DEApplication.getInstance().initBaiduLocClient();
		
		mTabFind.setOnClickListener(this);
		mTabUser.setOnClickListener(this);
		
		mTabMarket.setOnClickListener(this);
	}

	private void initView() {
		mTabFind = (LinearLayout) findViewById(R.id.tab_find);
		mTabUser = (LinearLayout) findViewById(R.id.tab_user);
		
		mTabMarket = (ImageView) findViewById(R.id.img_tab_market);

		mImgFind = (ImageButton) findViewById(R.id.btnTabMsg);
		mImgUser = (ImageButton) findViewById(R.id.btnTabUser);
	}

	@Override
	public void onClick(View v) {
		resetImgs();
		switch (v.getId()) {
		case R.id.tab_find:
			setSelect(0);
			break;
		case R.id.img_tab_market:
			setSelect(1);
			rotateCButton(v, 0f, 360f, 300);
			break;
		case R.id.tab_user:
			setSelect(2);
			break;

		default:
			break;
		}
	}

	private void setSelect(int i)
	{
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		hideFragment(transaction);
		// 把图片设置为亮的
		// 设置内容区域
		switch (i)
		{
		case 0:
			if (findFragment == null)
			{
				findFragment = new FindFragment();
				transaction.add(R.id.fragment_content, findFragment);
			} else
			{
				transaction.show(findFragment);
			}
			mImgFind.setImageResource(R.mipmap.tab_find_pressed);
			break;
		case 1:
			if (dSchoolFragment == null)
			{
				dSchoolFragment = new MerchantFragment();transaction.add(R.id.fragment_content, dSchoolFragment);
			} else
			{
				transaction.show(dSchoolFragment);
				
			}
			mTabMarket.setImageResource(R.mipmap.ic_tab_market_pressed);
			break;
		case 2:
			if (userFragment == null)
			{
				userFragment = new UserFragment();
				transaction.add(R.id.fragment_content, userFragment);
			} else
			{
				transaction.show(userFragment);
			}
			mImgUser.setImageResource(R.mipmap.tab_user_pressed);
			break;
		default:
			break;
		}

		transaction.commit();
	}

	private void hideFragment(FragmentTransaction transaction)
	{
		if (findFragment != null)
		{
			transaction.hide(findFragment);
		}
		if (dSchoolFragment != null)
		{
			transaction.hide(dSchoolFragment);
		}
		if (userFragment != null)
		{
			transaction.hide(userFragment);
		}
	}


	/**
	 * switch img to normal
	 */
	private void resetImgs() {
		mImgFind.setImageResource(R.mipmap.tab_find_normal);
		mImgUser.setImageResource(R.mipmap.tab_user_normal);
		mTabMarket.setImageResource(R.mipmap.ic_tab_market_normal);
	}

	/**
	 * double press to exit
	 */
	
	private long firstTime = 0;  
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {  
	       switch(keyCode)  
	       {  
	       case KeyEvent.KEYCODE_BACK:  
	            long secondTime = System.currentTimeMillis();   
	             if (secondTime - firstTime > 2000) {                                         //如果两次按键时间间隔大于2秒，则不退出  
	                  showShortToast("再按一次退出程序");
	                 firstTime = secondTime;//更新firstTime  
	                 return true;   
	             } else {                                                    //两次按键小于2秒时，退出应用  
	            	 System.exit(0);
	             }   
	           break;  
	       }  
	     return super.onKeyUp(keyCode, event);  
	 }  
	/**
	 * 中间菜单旋转
	 * @param v
	 * @param start
	 * @param end
	 * @param duration
	 */
	private void rotateCButton(View v, float start, float end, int duration) {
		RotateAnimation anim = new RotateAnimation(start, end, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		anim.setDuration(duration);
		anim.setFillAfter(true);
		v.startAnimation(anim);
	}
	
	/**
	 * 解决UI重叠问题
	 */
	@Override  
    public void onAttachFragment(Fragment fragment) {  
        super.onAttachFragment(fragment);  
          
        if (findFragment == null && fragment instanceof FindFragment) {  
        	findFragment = (FindFragment)fragment;  
        }else if ( dSchoolFragment == null && fragment instanceof MerchantFragment) {
        	dSchoolFragment = (MerchantFragment)fragment;
        }else if (userFragment == null && fragment instanceof UserFragment) {  
        	userFragment = (UserFragment)fragment;  
        }  
    }  
}

