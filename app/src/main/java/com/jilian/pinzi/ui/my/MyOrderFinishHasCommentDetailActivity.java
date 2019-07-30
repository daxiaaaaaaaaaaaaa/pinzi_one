package com.jilian.pinzi.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.MyShipmentGoodAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.common.dto.GoodsInfoDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.GoodsDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单详情 已评价
 *
 */
public class MyOrderFinishHasCommentDetailActivity extends BaseActivity implements MyShipmentGoodAdapter.GoodClickListener {
      private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<GoodsInfoDto> datas;
    private MyShipmentGoodAdapter goodAdapter;

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
        return R.layout.activity_myorderfinishhascommentdetail;
    }

    @Override
    public void initView() {
        setNormalTitle("订单详情", v -> finish());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        datas = new ArrayList<>();
        recyclerView.setLayoutManager(linearLayoutManager);
        goodAdapter = new MyShipmentGoodAdapter(this, datas, this);
        recyclerView.setAdapter(goodAdapter);



    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }




    @Override
    public void clickGoods(String goodId,GoodsInfoDto dto) {
        Intent intent  = new Intent(this, GoodsDetailActivity.class);
        if (dto.getScoreBuy() > 0) {
            intent.putExtra("shopType", 2);//积分商城
        }

        intent.putExtra("goodsId",goodId);
        startActivity(intent);
    }
}
