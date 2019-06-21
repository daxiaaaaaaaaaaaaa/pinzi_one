package com.jilian.pinzi.ui.user;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;

import java.util.HashMap;

import cn.jzvd.JzvdStd;


public class VideoPlayerDetailedActivity extends BaseActivity {
    private JzvdStd ivVideo;


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        JzvdStd.goOnPlayOnPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.video_layout;
    }

    @Override
    public void initView() {


        ivVideo = (JzvdStd) findViewById(R.id.iv_video);
        Bitmap bitmap = getIntent().getParcelableExtra("bitmap");
        ivVideo.thumbImageView.setImageBitmap(bitmap);
        ivVideo.setUp(getIntent().getStringExtra("url"), "", JzvdStd.SCREEN_WINDOW_NORMAL);
        JzvdStd.setJzUserAction(null);
        ivVideo.startVideo();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //  exoPlayerManager.onConfigurationChanged(newConfig);//横竖屏切换
        super.onConfigurationChanged(newConfig);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }

}
