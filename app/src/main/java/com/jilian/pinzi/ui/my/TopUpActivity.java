package com.jilian.pinzi.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;

/**
 * 充值
 */
public class TopUpActivity extends BaseActivity {
    private RelativeLayout rlAlipay;
    private RelativeLayout rlWechat;

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
        return R.layout.activity_topup;
    }

    @Override
    public void initView() {
        setNormalTitle("支付订单", v -> finish());
        rlAlipay = (RelativeLayout) findViewById(R.id.rl_alipay);
        rlWechat = (RelativeLayout) findViewById(R.id.rl_wechat);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        rlAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TopUpActivity.this, TopUpSuccessActivity.class));
            }
        });
        rlWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TopUpActivity.this, TopUpSuccessActivity.class));
            }
        });
    }
}
