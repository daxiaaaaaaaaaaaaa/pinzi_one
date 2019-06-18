package com.jilian.pinzi.ui.my;

import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;

public class AboutActivity extends BaseActivity {
    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_about;
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
