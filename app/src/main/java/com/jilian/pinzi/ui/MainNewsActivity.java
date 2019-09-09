package com.jilian.pinzi.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.MainNewsAdapter;
import com.jilian.pinzi.adapter.NewsTypeAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.InformationtDto;
import com.jilian.pinzi.common.dto.InformationtTypeDto;
import com.jilian.pinzi.common.dto.MsgDto;
import com.jilian.pinzi.common.dto.NewsTypeDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class MainNewsActivity extends BaseActivity implements NewsTypeAdapter.TypeTopListener, MainNewsAdapter.ClickNewsListener {
    private List<InformationtTypeDto> typeDtos;
    private RecyclerView rvTop;
    private SmartRefreshLayout srHasData;
    private RecyclerView recyclerview;
    private SmartRefreshLayout srNoData;
    private LinearLayoutManager lm_top;
    private NewsTypeAdapter typeTopAdapter;
    private MainNewsAdapter mainNewsAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<InformationtDto> list;
    private MainViewModel viewModel;

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
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
        //
        typeTopAdapter = new NewsTypeAdapter(this, typeDtos, this);
        rvTop.setAdapter(typeTopAdapter);
        //咨询列表
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        mainNewsAdapter = new MainNewsAdapter(this, list, this);
        recyclerview.setAdapter(mainNewsAdapter);


    }

    @Override
    public void initData() {
        showLoadingDialog();
        //获取咨询列表
        getInformationTypeList();
    }

    /**
     * 获取咨询列表
     */
    private void getInformationTypeList() {
        viewModel.getInformationTypeList();
        viewModel.getInformationtTypeData().observe(this, new Observer<BaseDto<List<InformationtTypeDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<InformationtTypeDto>> listBaseDto) {
                hideLoadingDialog();
                if (listBaseDto.isSuccess()) {
                    if (EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                        rvTop.setVisibility(View.VISIBLE);
                        typeDtos.clear();
                        typeDtos.addAll(listBaseDto.getData());
                        initSelect(0);
                        typeTopAdapter.notifyDataSetChanged();
                        getInformationList(typeDtos.get(0).getId());
                    }
                    else{
                        rvTop.setVisibility(View.GONE);
                    }
                } else {
                    ToastUitl.showImageToastFail(listBaseDto.getMsg());
                }
            }
        });
    }

    /**
     * 设置选择的位置
     *
     * @param position
     */
    private void initSelect(int position) {
        if (EmptyUtils.isNotEmpty(typeDtos)) {
            this.selectPosition = position;
            for (int i = 0; i < typeDtos.size(); i++) {
                if (position == i) {
                    typeDtos.get(i).setSelected(true);
                } else {
                    typeDtos.get(i).setSelected(false);
                }
            }
        }


    }

    @Override
    public void initListener() {
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getInformationList(typeDtos.get(selectPosition).getId());
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getInformationList(typeDtos.get(selectPosition).getId());
            }
        });
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if(!EmptyUtils.isNotEmpty(typeDtos)){
                    srNoData.finishRefresh();
                    return;
                }
                pageNo = 1;
                getInformationList(typeDtos.get(selectPosition).getId());
            }
        });
    }


    private int selectPosition;
    @Override
    public void oneTypeClick(View view, int position) {
        this.selectPosition = position;
        for (int i = 0; i < typeDtos.size(); i++) {
            if (i == position) {
                typeDtos.get(i).setSelected(true);
            } else {
                typeDtos.get(i).setSelected(false);
            }
        }
        typeTopAdapter.notifyDataSetChanged();
        //获取咨询列表
        getInformationList(typeDtos.get(position).getId());
    }
    private int pageNo = 1;//
    private int pageSize = 20;//
    /**
     * 获取咨询列表
     */
    private void getInformationList(String typeId) {
        viewModel.getInformationList(typeId,pageNo,pageSize);
        viewModel.getInformationtData().observe(this, new Observer<BaseDto<List<InformationtDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<InformationtDto>> listBaseDto) {
                getLoadingDialog().dismiss();
                srNoData.finishRefresh();
                srHasData.finishRefresh();
                srHasData.finishLoadMore();
                //有数据
                if (EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                    srNoData.setVisibility(View.GONE);
                    srHasData.setVisibility(View.VISIBLE);
                    if (pageNo == 1) {
                        list.clear();
                    }
                    list.addAll(listBaseDto.getData());
                    mainNewsAdapter.notifyDataSetChanged();
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
    public void clickNews(InformationtDto dto) {
        Intent intent = new Intent(this, MainNewsDetailActivity.class);
        intent.putExtra("id",dto.getId());
        startActivity(intent);
    }
}
