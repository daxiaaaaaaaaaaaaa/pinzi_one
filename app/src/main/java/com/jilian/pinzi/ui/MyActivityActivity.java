package com.jilian.pinzi.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.AllWorkAdapter;
import com.jilian.pinzi.adapter.MainActivityAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.common.dto.ActivityDto;
import com.jilian.pinzi.common.dto.ActivityProductDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 活动列表
 */
public class MyActivityActivity extends BaseActivity implements CustomItemClickListener, AllWorkAdapter.ClickListener {
    private TextView tvOne;
    private TextView tvTwo;
    private View vOne;
    private View vTwo;
    private RecyclerView rvOne;
    private RecyclerView rvTwo;
    private MainActivityAdapter adapter;
    private LinearLayoutManager ll_one;
    private LinearLayoutManager ll_two;
    private List<ActivityProductDto> list;

    private List<ActivityDto> data;

    private AllWorkAdapter allWorkAdapter;

    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_my_activity;
    }

    @Override
    public void initView() {
        setNormalTitle("我的活动", v -> finish());
        tvOne = (TextView) findViewById(R.id.tv_one);
        tvTwo = (TextView) findViewById(R.id.tv_two);
        vOne = (View) findViewById(R.id.v_one);
        vTwo = (View) findViewById(R.id.v_two);

        rvOne = (RecyclerView) findViewById(R.id.rv_one);
        rvTwo = (RecyclerView) findViewById(R.id.rv_two);
        ll_one = new LinearLayoutManager(this);
        ll_two = new LinearLayoutManager(this);
        rvOne.setLayoutManager(ll_one);
        rvTwo.setLayoutManager(ll_two);

        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION, DisplayUtil.dip2px(this, 10));//下间距
        rvTwo.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));

        list = new ArrayList<>();
        data = new ArrayList<>();
        //我的活动
        adapter = new MainActivityAdapter(this, data, this);
        rvOne.setAdapter(adapter);

        //我的作品
        allWorkAdapter = new AllWorkAdapter(this, list, this,this);
        rvTwo.setAdapter(allWorkAdapter);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vOne.setVisibility(View.VISIBLE);
                vTwo.setVisibility(View.INVISIBLE);
                tvOne.setTextColor(Color.parseColor("#c71233"));
                tvTwo.setTextColor(Color.parseColor("#888888"));
                rvOne.setVisibility(View.VISIBLE);
                rvTwo.setVisibility(View.GONE);
            }
        });
        tvTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vOne.setVisibility(View.INVISIBLE);
                vTwo.setVisibility(View.VISIBLE);
                tvOne.setTextColor(Color.parseColor("#888888"));
                tvTwo.setTextColor(Color.parseColor("#c71233"));
                rvOne.setVisibility(View.GONE);
                rvTwo.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, MainActivityDetailActivity.class);
        //我的活动详情
        intent.putExtra("index", 2);
        startActivity(intent);

    }

    @Override
    public void clickVideo(int position) {

    }

    @Override
    public void vote(int position) {

    }

}
