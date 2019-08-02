package com.jilian.pinzi.ui;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.ui.main.WebViewTitleActivity;

public class CustomRanActivity extends BaseActivity {

    private RelativeLayout rlOne;
    private RelativeLayout rlTwo;
    private RelativeLayout rlThree;



    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_customran;
    }

    @Override
    public void initView() {
        setNormalTitle("消费排行", v -> finish());
        rlOne = (RelativeLayout) findViewById(R.id.rl_one);
        rlTwo = (RelativeLayout) findViewById(R.id.rl_two);
        rlThree = (RelativeLayout) findViewById(R.id.rl_three);
    }

    @Override
    public void initData() {


    }

    @Override
    public void initListener() {
        rlOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomRanActivity.this, WebViewTitleActivity.class);
                intent.putExtra("linkUrl", Constant.Server.url_01);
                intent.putExtra("title","用户消费排行榜");
                startActivity(intent);
            }
        });
        rlTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomRanActivity.this, WebViewTitleActivity.class);
                intent.putExtra("linkUrl", Constant.Server.url_02);
                intent.putExtra("title","单商品销量排行榜");
                startActivity(intent);
            }
        });
        rlThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomRanActivity.this, WebViewTitleActivity.class);
                intent.putExtra("linkUrl", Constant.Server.url_03);
                intent.putExtra("title","区域消费排行榜");
                startActivity(intent);
            }
        });

    }
}
