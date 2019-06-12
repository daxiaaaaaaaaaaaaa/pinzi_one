package com.jilian.pinzi.ui.my;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.CarTapPagerAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.ui.my.fragment.HasUserFragment;
import com.jilian.pinzi.ui.my.fragment.NoUserFragment;
import com.jilian.pinzi.ui.my.fragment.OuttimeFragment;
import com.jilian.pinzi.views.CustomScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的优惠券
 */
public class MyCardActivity extends BaseActivity {
    private List<Fragment> mFragmentList;
    private NoUserFragment noUserFragment;
    private HasUserFragment hasUserFragment;
    private OuttimeFragment outtimeFragment;
    private CarTapPagerAdapter adapter;
    private CustomScrollViewPager viewPager;
    private View vOne;
    private View vTwo;
    private View vThree;
    private TextView tvOne;
    private TextView tvTwo;
    private TextView tvThree;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PinziApplication.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PinziApplication.removeActivity(this);
    }


    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_mycard;
    }

    @Override
    public void initView() {
        setCenterTitle("我的优惠券", "#FFFFFF");
        setleftImage(R.drawable.image_back, true, null);
        setTitleBg(R.color.color_black);
        viewPager = (CustomScrollViewPager) findViewById(R.id.viewPager);
        vOne = (View) findViewById(R.id.v_one);
        vTwo = (View) findViewById(R.id.v_two);
        vThree = (View) findViewById(R.id.v_three);
        tvOne = (TextView) findViewById(R.id.tv_one);
        tvTwo = (TextView) findViewById(R.id.tv_two);
        tvThree = (TextView) findViewById(R.id.tv_three);
    }

    @Override
    public void initData() {
        mFragmentList = new ArrayList<>();
        noUserFragment = new NoUserFragment();
        hasUserFragment = new HasUserFragment();
        outtimeFragment = new OuttimeFragment();
        mFragmentList.add(noUserFragment);
        mFragmentList.add(hasUserFragment);
        mFragmentList.add(outtimeFragment);
        adapter = new CarTapPagerAdapter(getSupportFragmentManager(), mFragmentList);
        viewPager.setAdapter(adapter);

    }

    @Override
    public void initListener() {
        tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
        tvTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
        tvThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        vOne.setVisibility(View.VISIBLE);
                        vTwo.setVisibility(View.INVISIBLE);
                        vThree.setVisibility(View.INVISIBLE);
                        tvOne.setTextColor(Color.parseColor("#c71233"));
                        tvTwo.setTextColor(Color.parseColor("#888888"));
                        tvThree.setTextColor(Color.parseColor("#888888"));
                        break;
                    case 1:
                        vOne.setVisibility(View.INVISIBLE);
                        vTwo.setVisibility(View.VISIBLE);
                        vThree.setVisibility(View.INVISIBLE);
                        tvOne.setTextColor(Color.parseColor("#888888"));
                        tvTwo.setTextColor(Color.parseColor("#c71233"));
                        tvThree.setTextColor(Color.parseColor("#888888"));
                        break;
                    case 2:
                        vOne.setVisibility(View.INVISIBLE);
                        vTwo.setVisibility(View.INVISIBLE);
                        vThree.setVisibility(View.VISIBLE);
                        tvOne.setTextColor(Color.parseColor("#888888"));
                        tvTwo.setTextColor(Color.parseColor("#888888"));
                        tvThree.setTextColor(Color.parseColor("#c71233"));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
