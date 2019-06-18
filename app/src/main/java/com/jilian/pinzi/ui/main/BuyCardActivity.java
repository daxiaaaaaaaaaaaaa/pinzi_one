package com.jilian.pinzi.ui.main;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;

/**
 * 购买优惠券
 */
public class BuyCardActivity extends BaseActivity {
    private LinearLayout llContainer;
    private LinearLayout llGoodsDetail;
    private RecyclerView recyclerView;
    private TextView tvTwo;
    private ImageView ivTwo;
    private TextView tvAllCount;
    private TextView tvCommisionNum;
    private TextView tvPayCount;
    private TextView tvOk;



    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_buycard;
    }

    @Override
    public void initView() {
        setNormalTitle("填写订单", v -> finish());
        llContainer = (LinearLayout) findViewById(R.id.ll_container);
        llGoodsDetail = (LinearLayout) findViewById(R.id.ll_goods_detail);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvTwo = (TextView) findViewById(R.id.tv_two);
        ivTwo = (ImageView) findViewById(R.id.iv_two);
        tvAllCount = (TextView) findViewById(R.id.tv_all_count);
        tvCommisionNum = (TextView) findViewById(R.id.tv_commisionNum);
        tvPayCount = (TextView) findViewById(R.id.tv_pay_count);
        tvOk = (TextView) findViewById(R.id.tv_ok);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
