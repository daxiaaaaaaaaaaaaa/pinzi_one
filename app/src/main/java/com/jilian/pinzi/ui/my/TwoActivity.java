package com.jilian.pinzi.ui.my;

import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;

public class TwoActivity extends BaseActivity {
    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_two;
    }

    @Override
    public void initView() {
        setNormalTitle("优惠券使用", v -> finish());
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
