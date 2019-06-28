package com.jilian.pinzi.ui.my.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.MainNewsAdapter;
import com.jilian.pinzi.adapter.MyNewsCollectAdapter;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.InformationtDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.views.CustomerLinearLayoutManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends BaseFragment implements CustomItemClickListener {


    private SmartRefreshLayout srHasData;
    private RecyclerView recyclerView;
    private SmartRefreshLayout srNoData;

    private MyNewsCollectAdapter mainNewsAdapter;
    private CustomerLinearLayoutManager linearLayoutManager;
    private List<InformationtDto> list;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    protected void loadData() {

    }

    @Override
    protected void createViewModel() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_goods;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        srHasData = (SmartRefreshLayout) view.findViewById(R.id.sr_has_data);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        srNoData = (SmartRefreshLayout) view.findViewById(R.id.sr_no_data);
        //咨询列表
        linearLayoutManager = new CustomerLinearLayoutManager(getmActivity());
        linearLayoutManager.setCanScrollVertically(false);
        recyclerView .setLayoutManager(linearLayoutManager);
        srNoData.setEnableLoadMore(false);
        list = new ArrayList<>();

        mainNewsAdapter = new MyNewsCollectAdapter(getmActivity(), list, this);
        recyclerView.setAdapter(mainNewsAdapter);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    protected void initListener() {

    }

    @Override
    public void onItemClick(View view, int position) {

    }


}
