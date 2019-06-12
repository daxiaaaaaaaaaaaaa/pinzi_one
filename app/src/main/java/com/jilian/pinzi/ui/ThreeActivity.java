package com.jilian.pinzi.ui;

import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;

public class ThreeActivity extends BaseActivity {
    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_three;
    }

    @Override
    public void initView() {
        setNormalTitle("抽奖说明", v -> finish());
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
