package com.jilian.pinzi.ui.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;

/**
 * 充值成功
 */
public class TopUpSuccessActivity extends BaseActivity {

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
        return R.layout.activity_topupsuccess;
    }

    @Override
    public void initView() {
        setNormalTitle("充值成功", v -> finish());
        setrightTitle("完成", "#FFFFFF", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
