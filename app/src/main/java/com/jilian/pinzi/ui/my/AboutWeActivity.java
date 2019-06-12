package com.jilian.pinzi.ui.my;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;

/**
 * 关于我们
 */
public class AboutWeActivity extends BaseActivity {
    @Override
    protected void createViewModel() {

    }
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
    public int intiLayout() {
        return R.layout.activity_aboutwe;
    }

    @Override
    public void initView() {
        setNormalTitle("关于我们", v -> finish());
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
