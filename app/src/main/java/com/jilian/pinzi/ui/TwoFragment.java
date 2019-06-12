package com.jilian.pinzi.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.AllGoodTypeAdapter;
import com.jilian.pinzi.adapter.GoodTypeAdapter;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.GoodsTypeDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.BuyCenterActivity;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TwoFragment extends BaseFragment implements CustomItemClickListener {
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerView;
    private List<GoodsTypeDto> datas;
    private AllGoodTypeAdapter allGoodTypeAdapter;
    private MainViewModel viewModel;
    private SmartRefreshLayout srHasData;
    private SmartRefreshLayout srNoData;


    @Override
    protected void loadData() {

    }

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_two;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setCenterTitle("全部分类", "#FFFFFF");
        srHasData = (SmartRefreshLayout)view. findViewById(R.id.sr_has_data);
        srNoData = (SmartRefreshLayout) view.findViewById(R.id.sr_no_data);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        datas = new ArrayList<>();
        allGoodTypeAdapter = new AllGoodTypeAdapter(getActivity(), datas, this);
        recyclerView.setLayoutManager(gridLayoutManager);
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION, DisplayUtil.dip2px(getActivity(), 15));//下间距
        recyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        recyclerView.setAdapter(allGoodTypeAdapter);
        srNoData.setEnableLoadMore(false);
        srHasData.setEnableLoadMore(false);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });
    }

    private void getData() {
        viewModel.getGoodsType();
        viewModel.getGoodsTypeliveData().observe(this, new Observer<BaseDto<List<GoodsTypeDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<GoodsTypeDto>> listBaseDto) {
                srNoData.finishRefresh();
                srHasData.finishRefresh();
                if(EmptyUtils.isNotEmpty(listBaseDto.getData())){
                    //第一次就没数据
                    srNoData.setVisibility(View.GONE);
                    srHasData.setVisibility(View.VISIBLE);
                    datas.clear();
                    datas.addAll(listBaseDto.getData());
                    allGoodTypeAdapter.notifyDataSetChanged();
                }
                else{
                    //第一次就没数据
                    srNoData.setVisibility(View.VISIBLE);
                    srHasData.setVisibility(View.GONE);
                }

            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), BuyCenterActivity.class);
        intent.putExtra("classes", 1);
        intent.putExtra("flag", true);
        intent.putExtra("position",position+1);
        startActivity(intent);
    }
}
