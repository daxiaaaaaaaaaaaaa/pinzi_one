package com.jilian.pinzi.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.ActivityDto;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.views.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的活动列表
 */
public class MyActivityActivity extends BaseActivity   {
    private TextView tvOne;
    private TextView tvTwo;
    private View vOne;
    private View vTwo;
    private NoScrollViewPager viewPager;
    private List<Fragment> mFragmentList;
    private MyActivityFragment myActivityFragment;
    private MyProductFragment myProductFragment;





    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_my_activity;
    }

    private CommonViewPagerAdapter mainTapPagerAdapter;

    @Override
    public void initView() {
        setNormalTitle("我的活动", v -> finish());
        tvOne = (TextView) findViewById(R.id.tv_one);
        tvTwo = (TextView) findViewById(R.id.tv_two);
        vOne = (View) findViewById(R.id.v_one);
        vTwo = (View) findViewById(R.id.v_two);
        viewPager = (NoScrollViewPager) findViewById(R.id.viewPager);

    }

    @Override
    public void initData() {
        mFragmentList = new ArrayList<>();
        myActivityFragment = new MyActivityFragment();
        myProductFragment = new MyProductFragment();
        mFragmentList.add(myActivityFragment);
        mFragmentList.add(myProductFragment);
        //
        mainTapPagerAdapter = new CommonViewPagerAdapter(getSupportFragmentManager(), mFragmentList);
        viewPager.setAdapter(mainTapPagerAdapter);
    }


    @Override
    public void initListener() {
        tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vOne.setVisibility(View.VISIBLE);
                vTwo.setVisibility(View.INVISIBLE);
                tvOne.setTextColor(Color.parseColor("#c71233"));
                tvTwo.setTextColor(Color.parseColor("#888888"));
                viewPager.setCurrentItem(0);

            }
        });
        tvTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vOne.setVisibility(View.INVISIBLE);
                vTwo.setVisibility(View.VISIBLE);
                tvOne.setTextColor(Color.parseColor("#888888"));
                tvTwo.setTextColor(Color.parseColor("#c71233"));
                viewPager.setCurrentItem(1);

            }
        });

    }


}
