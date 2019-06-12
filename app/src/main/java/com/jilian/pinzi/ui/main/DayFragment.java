package com.jilian.pinzi.ui.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseFragment;

/**
 * 日期
 */
public class DayFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void loadData() {

    }

    @Override
    protected void createViewModel() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_day;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
