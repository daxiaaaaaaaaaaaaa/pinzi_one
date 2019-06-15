package com.jilian.pinzi.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.AllWorkAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllWorksActivity extends BaseActivity implements CustomItemClickListener {
    private SmartRefreshLayout srHasData;
    private RecyclerView recyclerView;
    private SmartRefreshLayout srNoData;
    private LinearLayoutManager linearLayoutManager;
    private AllWorkAdapter allWorkAdapter;
    private List<String> datas;

    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_allworks;
    }

    @Override
    public void initView() {
        setNormalTitle("全部作品", v -> finish());
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        linearLayoutManager = new LinearLayoutManager(this);
        datas = new ArrayList<>();
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        allWorkAdapter = new AllWorkAdapter(this, datas, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(allWorkAdapter);
        srNoData.setEnableLoadMore(false);
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,  DisplayUtil.dip2px(this,10));//下间距
        recyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
