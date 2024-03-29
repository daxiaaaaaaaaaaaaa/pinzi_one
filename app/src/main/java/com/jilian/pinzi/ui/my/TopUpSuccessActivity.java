package com.jilian.pinzi.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;

/**
 * 充值成功
 */
public class TopUpSuccessActivity extends BaseActivity {
    private TextView tvType;
    private TextView tvMoney;



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
        tvType = (TextView) findViewById(R.id.tv_type);
        tvMoney = (TextView) findViewById(R.id.tv_money);
    }

    @Override
    public void initData() {
        String type = getIntent().getStringExtra("type");
        if("1".equals(type)){
            tvType.setText("微信支付");
        }
        if("2".equals(type)){
            tvType.setText("支付宝支付");
        }
        tvMoney.setText("¥"+getIntent().getStringExtra("money"));

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    @Override
    public void initListener() {

    }
}
