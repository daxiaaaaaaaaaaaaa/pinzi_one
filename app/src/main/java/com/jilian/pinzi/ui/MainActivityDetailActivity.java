package com.jilian.pinzi.ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;

public class MainActivityDetailActivity extends BaseActivity {
    private TextView tvGet;
    private TextView tvSee;



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
        tvGet = (TextView) findViewById(R.id.tv_get);
        tvSee = (TextView) findViewById(R.id.tv_see);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        tvSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivityDetailActivity.this,AllWorksActivity.class));
            }
        });
    }
}
