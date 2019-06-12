package com.jilian.pinzi.common.dto;

import android.graphics.Bitmap;




import java.io.Serializable;

import cn.jzvd.JzvdStd;

public class BannerDto implements Serializable {
    private static final long serialVersionUID = -6327919680380952385L;
    private String path;
    private Bitmap bitmap;
    private JzvdStd jzVideoPlayerStandard;

    public JzvdStd getJzVideoPlayerStandard() {
        return jzVideoPlayerStandard;
    }

    public void setJzVideoPlayerStandard(JzvdStd jzVideoPlayerStandard) {
        this.jzVideoPlayerStandard = jzVideoPlayerStandard;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
