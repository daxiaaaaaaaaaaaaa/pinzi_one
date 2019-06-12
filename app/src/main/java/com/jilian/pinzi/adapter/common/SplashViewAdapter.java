package com.jilian.pinzi.adapter.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.common.dto.BannerDto;
import com.jilian.pinzi.common.dto.StartPageDto;
import com.jilian.pinzi.views.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JzvdStd;

public class SplashViewAdapter extends PagerAdapter {
    private Context mContext;
    private List<StartPageDto> imgUrls;

    public SplashViewAdapter(Context mContext, List<StartPageDto> list) {
        this.mContext = mContext;
        this.imgUrls = list;
    }

    @Override
    public int getCount() {
        return imgUrls == null ? 0 : imgUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        String url = imgUrls.get(position).getImgUrl();
        RoundImageView imageView = new RoundImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(mContext).load(url).into(imageView);
        container.addView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return imageView;

    }

}
