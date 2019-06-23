package com.jilian.pinzi.ui;

import android.graphics.Bitmap;

import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;

import cn.jzvd.JzvdStd;

public class VideoPlayerActivity extends BaseActivity {
    private JzvdStd ivVideo;



    @Override
    protected void createViewModel() {

    }
    protected void onPause() {        super.onPause();   //     Jzvd.clearSavedProgress(this, null);
        //home back
        JzvdStd.goOnPlayOnPause();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_videoplayer;
    }

    @Override
    public void initView() {
        ivVideo = (JzvdStd) findViewById(R.id.iv_video);
    }

    @Override
    public void initData() {
        ivVideo.setUp(getIntent().getStringExtra("url"), "",JzvdStd.SCREEN_WINDOW_FULLSCREEN);
        Bitmap bitmap = getIntent().getParcelableExtra("bitmap");
        ivVideo.thumbImageView.setImageBitmap(bitmap);
        JzvdStd.setJzUserAction(null);
        ivVideo.startVideo();
    }

    @Override
    public void initListener() {

    }
}
