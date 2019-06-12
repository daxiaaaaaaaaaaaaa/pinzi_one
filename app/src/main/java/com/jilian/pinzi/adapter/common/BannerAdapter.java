package com.jilian.pinzi.adapter.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.common.dto.BannerDto;
import com.jilian.pinzi.views.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JzvdStd;

public class BannerAdapter extends PagerAdapter {
    private Context mContext;
    private List<BannerDto> imgUrls;
//    private BannerListener bannerListener;

    public BannerAdapter(Context context) {
        this.mContext = context;
    }

    public void update(List<BannerDto> resIds) {
        if (this.imgUrls != null)
            this.imgUrls.clear();
        if (resIds != null)
            this.imgUrls = resIds;
    }

    @Override
    public int getCount() {
        return imgUrls == null ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    private List<JzvdStd> list = new ArrayList<>();

    public List<BannerDto> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<BannerDto> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public List<JzvdStd> getList() {
        return list;
    }

    public void setList(List<JzvdStd> list) {
        this.list = list;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        String url = imgUrls.get(position % imgUrls.size()).getPath();
        Bitmap bitmap = imgUrls.get(position % imgUrls.size()).getBitmap();
        if (!url.contains(".mp4")) {
            RoundImageView imageView = new RoundImageView(mContext);
            //imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(mContext).load(url).into(imageView);
            container.addView(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            return imageView;
        } else {
            JzvdStd jzVideoPlayerStandard = new JzvdStd(mContext);
            jzVideoPlayerStandard.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            jzVideoPlayerStandard.setUp(url, "",JzvdStd.SCREEN_WINDOW_NORMAL);
            //支持全屏
            //JzvdStd.startFullscreen(mContext, JzvdStd.class, url, "");

            list.add(jzVideoPlayerStandard);


            jzVideoPlayerStandard.thumbImageView.setImageBitmap(bitmap);
            JzvdStd.setJzUserAction(null);
            container.addView(jzVideoPlayerStandard);
            return jzVideoPlayerStandard;
        }
    }

}
