package com.jilian.pinzi.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.utils.EmptyUtils;

import androidx.annotation.RequiresApi;
import cn.jzvd.JzvdStd;

public class VideoPlayerActivity extends BaseActivity {
    private JzvdStd ivVideo;
    //private ImageView ivBigImage;





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
       // ivBigImage = (ImageView) findViewById(R.id.iv_big_image);
    }

    @Override
    public void initData() {
        ivVideo.setUp(getIntent().getStringExtra("url"), "",JzvdStd.SCREEN_WINDOW_FULLSCREEN);
        byte [] bis=getIntent().getByteArrayExtra("bitmap");
        if(EmptyUtils.isNotEmpty(bis)){
            Bitmap bitmap= BitmapFactory.decodeByteArray(bis, 0, bis.length);
            ivVideo.thumbImageView.setImageBitmap(bitmap);
        }

        JzvdStd.setJzUserAction(null);
        ivVideo.startVideo();
    }

    @Override
    public void initListener() {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finishAfterTransition();
    }
}
