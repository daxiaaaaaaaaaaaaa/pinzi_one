package com.jilian.pinzi.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSONObject;
import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.common.SplashViewAdapter;
import com.jilian.pinzi.common.dto.StartPageDto;
import com.jilian.pinzi.common.msg.FriendMsg;
import com.jilian.pinzi.common.msg.RxBus;
import com.jilian.pinzi.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 闪屏页面
 *
 * @author weishixiong
 * @Time 2018-05-23
 */
public class SplashActivity extends AppCompatActivity  {
    private ViewPager viewPager;
    private SplashViewAdapter splashViewAdapter;
    private  List<StartPageDto> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PinziApplication.addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_splash);
        SPUtil.putData(Constant.SP_VALUE.CONSTANT, Constant.SP_VALUE.ISCOMIN, true);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        String str = getIntent().getStringExtra("list");
        list = JSONObject.parseArray(str,StartPageDto.class);
        splashViewAdapter = new SplashViewAdapter(SplashActivity.this,list);
        viewPager.setAdapter(splashViewAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PinziApplication.removeActivity(this);
    }
}
