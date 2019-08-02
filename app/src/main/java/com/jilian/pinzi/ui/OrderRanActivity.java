package com.jilian.pinzi.ui;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.ui.main.WebViewTitleActivity;

public class OrderRanActivity extends BaseActivity {

    private RelativeLayout rlOne;
    private RelativeLayout rlTwo;
    private RelativeLayout rlThree;
    private RelativeLayout rlFour;



    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_orderran;
    }

    @Override
    public void initView() {
        setNormalTitle("订单统计", v -> finish());
        rlOne = (RelativeLayout) findViewById(R.id.rl_one);
        rlTwo = (RelativeLayout) findViewById(R.id.rl_two);
        rlThree = (RelativeLayout) findViewById(R.id.rl_three);
        rlFour = (RelativeLayout) findViewById(R.id.rl_four);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        rlOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderRanActivity.this, WebViewTitleActivity.class);
                intent.putExtra("linkUrl", Constant.Server.url_04);
                intent.putExtra("title","区域统计");
                startActivity(intent);
            }
        });
        rlTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderRanActivity.this, WebViewTitleActivity.class);
                intent.putExtra("linkUrl", Constant.Server.url_05);
                intent.putExtra("title","店铺统计");
                startActivity(intent);
            }
        });
        rlThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderRanActivity.this, WebViewTitleActivity.class);
                intent.putExtra("linkUrl", Constant.Server.url_06);
                intent.putExtra("title","支付方式统计");
                startActivity(intent);
            }
        });
        rlFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderRanActivity.this, WebViewTitleActivity.class);
                intent.putExtra("linkUrl", Constant.Server.url_07);
                intent.putExtra("title","订单状态统计");
                startActivity(intent);
            }
        });

    }
}
