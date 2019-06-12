package com.jilian.pinzi.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.listener.ViewPagerItemClickListener;

import java.util.List;

/**
 * viewpager公共适配器
 *
 * @author weishixiong
 * @Time 2018-04-25
 */

public class CommonPagerAdapter extends PagerAdapter {
    private List<View> views;
    private ViewPagerItemClickListener listener;

    public CommonPagerAdapter(List<View> views, ViewPagerItemClickListener listener) {
        this.views = views;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return views == null ? 0 : views.size();
    }


    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onViewPageItemClick(view, position);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
}
