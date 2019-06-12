package com.jilian.pinzi.ui.my;

import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;

public class OneActivity extends BaseActivity {
    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_one;
    }

    @Override
    public void initView() {
        setNormalTitle("积分说明", v -> finish());
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
