package com.drivingevaluate.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.drivingevaluate.R;
import com.drivingevaluate.ui.base.Yat3sActivity;
import com.drivingevaluate.ui.fragment.Welcome1;
import com.drivingevaluate.ui.fragment.Welcome2;
import com.drivingevaluate.ui.fragment.Welcome3;
import com.drivingevaluate.ui.fragment.Welcome4;
import com.drivingevaluate.view.ColorAnimationView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Yat3s on 8/31/15.
 * Email:hawkoyates@gmail.com
 */
public class WelcomeActivity extends Yat3sActivity {
    private static final int[] resource = new int[]{R.mipmap.welcome1, R.mipmap.welcome2,
            R.mipmap.welcome3, R.mipmap.welcome4};
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_welcome);
        fragments.add(new Welcome1());
        fragments.add(new Welcome2());
        fragments.add(new Welcome3());
        fragments.add(new Welcome4());
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        MyFragmentStatePager adpter = new MyFragmentStatePager(getSupportFragmentManager());
        ColorAnimationView colorAnimationView = (ColorAnimationView) findViewById(R.id.colorAnimationView);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        /**
         *  首先，你必须在 设置 Viewpager的 adapter 之后在调用这个方法
         *  第二点，setmViewPager(ViewPager mViewPager,Object obj, int count, int... colors)
         *         第一个参数 是 你需要传人的 viewpager
         *         第二个参数 是 一个实现了ColorAnimationView.OnPageChangeListener接口的Object,用来实现回调
         *         第三个参数 是 viewpager 的 孩子数量
         *         第四个参数 int... colors ，你需要设置的颜色变化值~~ 如何你传人 空，那么触发默认设置的颜色动画
         * */
        /**
         *  Frist: You need call this method after you set the Viewpager adpter;
         * Second: setmViewPager(ViewPager mViewPager,Object obj， int count, int... colors)
         *          so,you can set any length colors to make the animation more cool!
         * Third: If you call this method like below, make the colors no data, it will create
         *          a change color by default.
         * */
        colorAnimationView.setmViewPager(viewPager, fragments.size());
        colorAnimationView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        // Four : Also ,you can call this method like this:
        // colorAnimationView.setmViewPager(viewPager,this,resource.length,0xffFF8080,0xff8080FF,0xffffffff,0xff80ff80);
    }


    public class MyFragmentStatePager
            extends FragmentStatePagerAdapter {

        public MyFragmentStatePager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new MyFragment(position);
        }

        @Override
        public int getCount() {
            return resource.length;
        }
    }

    @SuppressLint("ValidFragment")
    public class MyFragment
            extends Fragment {
        private int position;

        public MyFragment(int position) {
            this.position = position;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(resource[position]);
            return imageView;
        }
    }
}
