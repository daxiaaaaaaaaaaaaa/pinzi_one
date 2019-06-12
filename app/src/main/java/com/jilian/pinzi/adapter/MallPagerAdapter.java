package com.jilian.pinzi.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.common.dto.StartPageDto;
import com.jilian.pinzi.listener.ViewPagerItemClickListener;
import com.jilian.pinzi.views.RoundImageView;

import java.util.List;

/**
 * viewpager公共适配器
 *
 * @author weishixiong
 * @Time 2018-04-25
 */

public class MallPagerAdapter extends PagerAdapter {
    private List<StartPageDto> list;
    private ViewPagerItemClickListener listener;
    private Context context;
    public MallPagerAdapter(List<StartPageDto> list, ViewPagerItemClickListener listener, Context context) {
        this.list = list;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : Integer.MAX_VALUE;
    }


    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String url = list.get(position % list.size()).getImgUrl();
        RoundImageView imageView = new RoundImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(url).into(imageView);
        container.addView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onViewPageItemClick(view, position % list.size());
            }
        });
        return imageView;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
}
