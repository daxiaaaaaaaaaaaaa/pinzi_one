package com.jilian.pinzi.adapter.common;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.ui.main.ViewPhotosActivity;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JzvdStd;
import io.rong.photoview.PhotoView;
import io.rong.photoview.PhotoViewAttacher;

public class CommonStringAdapter extends PagerAdapter {
    private ViewPhotosActivity mContext;
    private List<String> imgUrls;

    public CommonStringAdapter(ViewPhotosActivity context) {
        this.mContext = context;
    }

    public void update(List<String> resIds) {
        if (this.imgUrls != null)
            this.imgUrls.clear();
        if (resIds != null)
            this.imgUrls = resIds;
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

    private List<JzvdStd> list = new ArrayList<>();

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
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
        String url = imgUrls.get(position);
        PhotoView imageView = new PhotoView(mContext);

        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                mContext.finish();
            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });

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
