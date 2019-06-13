package com.jilian.pinzi.ui;

import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;

public class MainActivityDetailActivity extends BaseActivity {
    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_mainactivitydetail;
    }

    @Override
    public void initView() {
        setNormalTitle("活动详情", v -> finish());
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
