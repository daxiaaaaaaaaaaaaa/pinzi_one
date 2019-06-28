package com.jilian.pinzi.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.MainActivityAdapter;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.ActivityDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.utils.EmptyUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class MyActivityFragment extends BaseFragment implements CustomItemClickListener {
    private SmartRefreshLayout srHasData;
    private RecyclerView recyclerview;
    private SmartRefreshLayout srNoData;
    private List<ActivityDto> datas;
    private LinearLayoutManager linearLayoutManager;
    private MainActivityAdapter adapter;

    @Override
    protected void loadData() {

    }

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_myactivity;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        srHasData = (SmartRefreshLayout) view.findViewById(R.id.sr_has_data);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        srNoData = (SmartRefreshLayout) view.findViewById(R.id.sr_no_data);
        linearLayoutManager = new LinearLayoutManager(getmActivity());
        recyclerview.setLayoutManager(linearLayoutManager);
        datas = new ArrayList<>();
        adapter = new MainActivityAdapter(getmActivity(), datas, this);
        recyclerview.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        getMyActivityList();
    }

    private int pageNo = 1;//
    private int pageSize = 20;//
    private MainViewModel viewModel;

    private void getMyActivityList() {
        viewModel.getMyActivityList(PinziApplication.getInstance().getLoginDto().getId(), pageNo, pageSize);
        viewModel.getMyActivityListData().observe(this, new Observer<BaseDto<List<ActivityDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<ActivityDto>> listBaseDto) {
                getLoadingDialog().dismiss();
                srNoData.finishRefresh();
                srHasData.finishRefresh();
                srHasData.finishLoadMore();
                //有数据
                if (EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                    srNoData.setVisibility(View.GONE);
                    srHasData.setVisibility(View.VISIBLE);
                    if (pageNo == 1) {
                        datas.clear();
                    }
                    datas.addAll(listBaseDto.getData());
                    adapter.notifyDataSetChanged();
                } else {
                    //说明是上拉加载
                    if (pageNo > 1) {
                        pageNo--;
                    } else {
                        //第一次就没数据
                        srNoData.setVisibility(View.VISIBLE);
                        srHasData.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    protected void initListener() {
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getMyActivityList();
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getMyActivityList();
            }
        });
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getMyActivityList();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getmActivity(), MainActivityDetailActivity.class);
        //我的活动详情
        intent.putExtra("index", 2);
        intent.putExtra("id", datas.get(position).getAcitvityId());
        startActivity(intent);
    }
}
