package com.jilian.pinzi.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;

/**
 * @author ningpan
 * @since 2018/10/30 17:06 <br>
 * description: 用户协议 Activity
 */
public class UserServiceActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PinziApplication.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PinziApplication.removeActivity(this);
    }

    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_user_service;
    }

    @Override
    public void initView() {
        setCenterTitle("用户协议", "#FFFFFF");
        setleftImage(R.drawable.image_back, true, null);
        setTitleBg(R.color.color_black);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
