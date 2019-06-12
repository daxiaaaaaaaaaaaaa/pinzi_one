package com.jilian.pinzi.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.CommonPagerAdapter;
import com.jilian.pinzi.adapter.ViewPagerIndicator;
import com.jilian.pinzi.adapter.common.BannerAdapter;
import com.jilian.pinzi.adapter.common.CommonStringAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.listener.ViewPagerItemClickListener;
import com.jilian.pinzi.ui.OneFragment;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.views.RoundImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * 浏览照片
 *
 * @author weishixiong
 * @Time 2018-06-28
 */

public class ViewPhotosActivity extends BaseActivity implements ViewPagerItemClickListener {
    private TextView tvText;
    private ViewPager viewpager;
    private CommonStringAdapter bannerAdapter;



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
    private List<String> list;
    @Override
    public int intiLayout() {
        return R.layout.activity_viewphotos;
    }

    @Override
    public void initView() {
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        tvText = (TextView) findViewById(R.id.tv_text);
        String url = getIntent().getStringExtra("url");
        if(url!=null)
        {
            if(url.contains(","))
            {
               list = Arrays.asList(url.split(","));
            }
            else{
                list = new ArrayList<>();
                list.add(url);
            }
            viewpager.setOffscreenPageLimit(list.size());
            bannerAdapter = new CommonStringAdapter(this);
            bannerAdapter.update(list);
            viewpager.setAdapter(bannerAdapter);

        }
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        if(!EmptyUtils.isNotEmpty(list)){
            return;
        }
        tvText.setText((getIntent().getIntExtra("position",0)+1)+"/"+list.size());
        viewpager.setCurrentItem(getIntent().getIntExtra("position",0));
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvText.setText(position+1+"/"+list.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onViewPageItemClick(View view, int position) {
            finish();
    }
}
