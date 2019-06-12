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
import com.jilian.pinzi.ui.my.fragment.GoodsFragment;
import com.jilian.pinzi.ui.my.fragment.HasUserFragment;
import com.jilian.pinzi.ui.my.fragment.NoUserFragment;
import com.jilian.pinzi.ui.my.fragment.OuttimeFragment;
import com.jilian.pinzi.ui.my.fragment.ShopFragment;
import com.jilian.pinzi.views.CustomScrollViewPager;
import com.jilian.pinzi.views.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的收藏
 */
public class MyCollectionActivity extends BaseActivity {
    private List<Fragment> mFragmentList;
    private GoodsFragment goodsFragment;
    private ShopFragment shopFragment;

    private CarTapPagerAdapter adapter;
    private NoScrollViewPager viewPager;
    private View vOne;
    private View vTwo;
    private TextView tvOne;
    private TextView tvTwo;
    private String classify;//1.收藏 2.足迹
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
    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_mycollection;
    }

    @Override
    public void initView() {
        setCenterTitle("我的收藏", "#FFFFFF");
        setleftImage(R.drawable.image_back, true, null);
        setTitleBg(R.color.color_black);
        viewPager = (NoScrollViewPager) findViewById(R.id.viewPager);
        vOne = (View) findViewById(R.id.v_one);
        vTwo = (View) findViewById(R.id.v_two);
        tvOne = (TextView) findViewById(R.id.tv_one);
        tvTwo = (TextView) findViewById(R.id.tv_two);
    }

    @Override
    public void initData() {
        setClassify("1");
        mFragmentList = new ArrayList<>();
        goodsFragment = new GoodsFragment();
        shopFragment = new ShopFragment();
        mFragmentList.add(goodsFragment);
        mFragmentList.add(shopFragment);
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
                        tvOne.setTextColor(Color.parseColor("#c71233"));
                        tvTwo.setTextColor(Color.parseColor("#888888"));
                        break;
                    case 1:
                        vOne.setVisibility(View.INVISIBLE);
                        vTwo.setVisibility(View.VISIBLE);
                        tvOne.setTextColor(Color.parseColor("#888888"));
                        tvTwo.setTextColor(Color.parseColor("#c71233"));
                        break;
                    case 2:
                        vOne.setVisibility(View.INVISIBLE);
                        vTwo.setVisibility(View.INVISIBLE);
                        tvOne.setTextColor(Color.parseColor("#888888"));
                        tvTwo.setTextColor(Color.parseColor("#888888"));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
