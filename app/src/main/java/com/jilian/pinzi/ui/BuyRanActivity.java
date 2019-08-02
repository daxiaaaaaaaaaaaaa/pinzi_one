package com.jilian.pinzi.ui;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.ui.main.WebViewTitleActivity;

/**
 * 销量统计
 */
public class BuyRanActivity extends BaseActivity

{
    private RelativeLayout rlOne;
    private RelativeLayout rlTwo;
    private RelativeLayout rlThree;
    private RelativeLayout rlFour;
    private RelativeLayout rlFive;




    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_butran;
    }

    @Override
    public void initView() {
        setNormalTitle("销量统计", v -> finish());
        rlOne = (RelativeLayout) findViewById(R.id.rl_one);
        rlTwo = (RelativeLayout) findViewById(R.id.rl_two);
        rlThree = (RelativeLayout) findViewById(R.id.rl_three);
        rlFour = (RelativeLayout) findViewById(R.id.rl_four);
        rlFive = (RelativeLayout) findViewById(R.id.rl_five);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        rlOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyRanActivity.this, WebViewTitleActivity.class);
                intent.putExtra("linkUrl", Constant.Server.url_11);
                intent.putExtra("title","平台销量统计");
                startActivity(intent);
            }
        });
        rlTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyRanActivity.this, WebViewTitleActivity.class);
                intent.putExtra("linkUrl", Constant.Server.url_12);
                intent.putExtra("title","城市销量统计");
                startActivity(intent);
            }
        });
        rlThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyRanActivity.this, WebViewTitleActivity.class);
                intent.putExtra("linkUrl", Constant.Server.url_13);
                intent.putExtra("title","城市销量走势统计");
                startActivity(intent);
            }
        });
        rlFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyRanActivity.this, WebViewTitleActivity.class);
                intent.putExtra("linkUrl", Constant.Server.url_14);
                intent.putExtra("title","店铺销量统计");
                startActivity(intent);
            }
        });
        rlFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyRanActivity.this, WebViewTitleActivity.class);
                intent.putExtra("linkUrl", Constant.Server.url_15);
                intent.putExtra("title","店铺销量走势统计");
                startActivity(intent);
            }
        });

    }
}
