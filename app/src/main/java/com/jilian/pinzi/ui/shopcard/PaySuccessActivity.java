package com.jilian.pinzi.ui.shopcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.ui.MainActivity;
import com.jilian.pinzi.ui.main.GoodsDetailActivity;
import com.jilian.pinzi.ui.my.MyOrderWaiteGetGoodDetailActivity;

public class PaySuccessActivity extends BaseActivity {
    private TextView tvPayType;
    private TextView tvPayCount;
    private TextView tvSeeOrder;
    private TextView tvBack;


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
        return R.layout.activity_paysuccess;
    }

    @Override
    public void initView() {
        setNormalTitle("支付成功", v -> finish());
        setrightTitle("完成", "#FFFFFF", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvPayType = (TextView) findViewById(R.id.tv_pay_type);
        tvPayCount = (TextView) findViewById(R.id.tv_pay_count);
        tvSeeOrder = (TextView) findViewById(R.id.tv_see_order);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvPayType.setText(getIntent().getStringExtra("payType"));
        tvPayCount.setText(getIntent().getStringExtra("payCount"));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(PaySuccessActivity.this, MainActivity.class));
                PinziApplication.clearAllActivitys();
            }
        });
        tvSeeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaySuccessActivity.this, MyOrderWaiteGetGoodDetailActivity.class);
                intent.putExtra("type",1);
                intent.putExtra("orderId",getIntent().getStringExtra("orderId"));
                startActivity(intent);
                finish();
            }
        });
    }
}
