package com.jilian.pinzi.ui;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.MainNewsAdapter;
import com.jilian.pinzi.adapter.NewsTypeAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.common.dto.NewsTypeDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class MainNewsActivity extends BaseActivity implements NewsTypeAdapter.TypeTopListener, CustomItemClickListener {
    private List<NewsTypeDto> typeDtos;
    private RecyclerView rvTop;
    private SmartRefreshLayout srHasData;
    private RecyclerView recyclerview;
    private SmartRefreshLayout srNoData;
    private LinearLayoutManager lm_top;
    private NewsTypeAdapter typeTopAdapter;
    private MainNewsAdapter mainNewsAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<String> list;

    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_mainnews;
    }

    @Override
    public void initView() {
        setNormalTitle("资讯列表", v -> finish());
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        srNoData.setEnableLoadMore(false);

        //分类
        rvTop = (RecyclerView) findViewById(R.id.rv_top);
        lm_top = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvTop.setLayoutManager(lm_top);
        typeDtos = new ArrayList<>();
        typeDtos.add(new NewsTypeDto("分类1", true));
        typeDtos.add(new NewsTypeDto("分类2", false));
        typeDtos.add(new NewsTypeDto("分类3", false));
        typeDtos.add(new NewsTypeDto("分类4", false));
        typeDtos.add(new NewsTypeDto("分类5", false));
        //
        typeTopAdapter = new NewsTypeAdapter(this, typeDtos, this);
        rvTop.setAdapter(typeTopAdapter);
        //咨询列表
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        mainNewsAdapter = new MainNewsAdapter(this, list, this);
        recyclerview.setAdapter(mainNewsAdapter);


    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void oneTypeClick(View view, int position) {
        for (int i = 0; i < typeDtos.size(); i++) {
            if (i == position) {
                typeDtos.get(i).setSelected(true);
            } else {
                typeDtos.get(i).setSelected(false);
            }
        }
        typeTopAdapter.notifyDataSetChanged();
        getNews();
    }

    /**
     * 获取咨询列表
     */
    private void getNews() {
    }

    @Override
    public void onItemClick(View view, int position) {
        startActivity(new Intent(this,MainNewsDetailActivity.class));
    }
}
