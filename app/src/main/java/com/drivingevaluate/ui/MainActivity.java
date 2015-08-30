package com.drivingevaluate.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drivingevaluate.R;
import com.drivingevaluate.app.App;
import com.drivingevaluate.config.VersionManager;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.ui.fragment.ExaminationFragment;
import com.drivingevaluate.ui.fragment.HomeFragment;
import com.drivingevaluate.ui.fragment.MerchantFragment;
import com.drivingevaluate.ui.fragment.UserFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Yat3sActivity {
	@Bind(R.id.home_tab_layout)
	LinearLayout homeLayout;
	@Bind(R.id.merchant_tab_layout)
	LinearLayout merchantLayout;
	@Bind(R.id.examination_tab_layout)
	LinearLayout examinationLayout;
	@Bind(R.id.user_tab_layout)
	LinearLayout userLayout;

	@Bind(R.id.home_tab_btn)
	ImageButton homeBtn;
	@Bind(R.id.merchant_tab_btn)
	ImageButton merchantBtn;
	@Bind(R.id.examination_tab_btn)
	ImageButton examinationBtn;
	@Bind(R.id.user_tab_btn)
	ImageButton userBtn;

	@Bind(R.id.home_tab_tv)
	TextView homeTv;
	@Bind(R.id.merchant_tab_tv)
	TextView merchantTv;
	@Bind(R.id.examination_tab_tv)
	TextView examinationTv;
	@Bind(R.id.user_tab_tv)
	TextView userTv;

	Fragment homeFragment;
	Fragment merchantFragment;
	Fragment examinationFragment;
	Fragment userFragment;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		VersionManager versionManager = new VersionManager(this, 0);
		versionManager.checkUpdate();

		initView();
		initEvent();
		Loc();
		setSelect(0);
	}

	private void initEvent() {
		//开启定位
		App.getInstance().initBaiduLocClient();
		
	}

	private void initView() {

	}


	@OnClick({R.id.home_tab_layout, R.id.merchant_tab_layout, R.id.examination_tab_layout, R.id.user_tab_layout})
	void tabOnClick(View v) {
		switch (v.getId()) {
			case R.id.home_tab_layout:
			setSelect(0);
			break;
			case R.id.merchant_tab_layout:
			setSelect(1);
			break;
			case R.id.examination_tab_layout:
			setSelect(2);
			break;
			case R.id.user_tab_layout:
				setSelect(3);
				break;

		default:
			break;
		}
	}

	public void setSelect(int i)
	{
		resetImgs();
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		hideFragment(transaction);
		// 把图片设置为亮的
		// 设置内容区域
		switch (i)
		{
		case 0:
			if (homeFragment == null)
			{
				homeFragment = new HomeFragment();
				transaction.add(R.id.fragment_content, homeFragment);
			} else
			{
				transaction.show(homeFragment);
			}
			homeBtn.setImageResource(R.mipmap.ic_tab_home_pressed);
			homeLayout.setBackgroundColor(getResources().getColor(R.color.theme_blue));
			homeTv.setTextColor(getResources().getColor(R.color.md_white_1000));
			break;
		case 1:
			if (merchantFragment == null)
			{
				merchantFragment = new MerchantFragment();
				transaction.add(R.id.fragment_content, merchantFragment);
			} else
			{
				transaction.show(merchantFragment);
				
			}
			merchantBtn.setImageResource(R.mipmap.ic_tab_merchant_pressed);
			merchantLayout.setBackgroundColor(getResources().getColor(R.color.theme_blue));
			merchantTv.setTextColor(getResources().getColor(R.color.md_white_1000));
			break;
		case 2:
			if (examinationFragment == null) {
				examinationFragment = new ExaminationFragment();
				transaction.add(R.id.fragment_content, examinationFragment);
			} else {
				transaction.show(examinationFragment);
			}
			examinationBtn.setImageResource(R.mipmap.ic_tab_examination_pressed);
			examinationLayout.setBackgroundColor(getResources().getColor(R.color.theme_blue));
			examinationTv.setTextColor(getResources().getColor(R.color.md_white_1000));
			break;
			case 3:
			if (userFragment == null)
			{
				userFragment = new UserFragment();
				transaction.add(R.id.fragment_content, userFragment);
			} else
			{
				transaction.show(userFragment);
			}
				userBtn.setImageResource(R.mipmap.ic_tab_user_pressed);
				userLayout.setBackgroundColor(getResources().getColor(R.color.theme_blue));
				userTv.setTextColor(getResources().getColor(R.color.md_white_1000));
			break;
		default:
			break;
		}

		transaction.commit();
	}

	private void hideFragment(FragmentTransaction transaction)
	{
		if (homeFragment != null) {
			transaction.hide(homeFragment);
		}
		if (merchantFragment != null) {
			transaction.hide(merchantFragment);
		}
		if (examinationFragment != null) {
			transaction.hide(examinationFragment);
		}
		if (userFragment != null) {
			transaction.hide(userFragment);
		}
	}


	/**
	 * switch img to normal
	 */
	private void resetImgs() {
		homeBtn.setImageResource(R.mipmap.ic_tab_home_normal);
		merchantBtn.setImageResource(R.mipmap.ic_tab_merchant_normal);
		examinationBtn.setImageResource(R.mipmap.ic_tab_examination_normal);
		userBtn.setImageResource(R.mipmap.ic_tab_user_normal);

		homeLayout.setBackgroundColor(getResources().getColor(R.color.md_white_1000));
		merchantLayout.setBackgroundColor(getResources().getColor(R.color.md_white_1000));
		examinationLayout.setBackgroundColor(getResources().getColor(R.color.md_white_1000));
		userLayout.setBackgroundColor(getResources().getColor(R.color.md_white_1000));

		homeTv.setTextColor(getResources().getColor(R.color.md_grey_700));
		merchantTv.setTextColor(getResources().getColor(R.color.md_grey_700));
		examinationTv.setTextColor(getResources().getColor(R.color.md_grey_700));
		userTv.setTextColor(getResources().getColor(R.color.md_grey_700));
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
	 * 解决UI重叠问题
	 */
	@Override  
    public void onAttachFragment(Fragment fragment) {  
        super.onAttachFragment(fragment);

		if (homeFragment == null && fragment instanceof HomeFragment) {
			homeFragment = fragment;
		} else if (merchantFragment == null && fragment instanceof MerchantFragment) {
			merchantFragment = fragment;
		} else if (examinationFragment == null && fragment instanceof ExaminationFragment) {
			examinationFragment = fragment;
		} else if (userFragment == null && fragment instanceof UserFragment) {
			userFragment = fragment;
		}
    }  
}

