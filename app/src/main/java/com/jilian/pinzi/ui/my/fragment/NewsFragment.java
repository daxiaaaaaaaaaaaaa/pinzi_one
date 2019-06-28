package com.jilian.pinzi.ui.my.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.MainNewsAdapter;
import com.jilian.pinzi.adapter.MyNewsCollectAdapter;
import com.jilian.pinzi.adapter.MyShopsAdapter;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.CollectionFootDto;
import com.jilian.pinzi.common.dto.InformationtDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.my.MyCollectionActivity;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.views.CustomerLinearLayoutManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends BaseFragment implements CustomItemClickListener {


    private SmartRefreshLayout srHasData;
    private RecyclerView recyclerView;
    private SmartRefreshLayout srNoData;

    private MyNewsCollectAdapter mainNewsAdapter;
    private CustomerLinearLayoutManager linearLayoutManager;
    private List<CollectionFootDto> datas;
    private MyViewModel viewModel;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    protected void loadData() {

    }

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }
    private String classify;//1.收藏 2.足迹
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
        linearLayoutManager = new CustomerLinearLayoutManager(getActivity());
        linearLayoutManager.setCanScrollVertically(false);
        recyclerView .setLayoutManager(linearLayoutManager);
        srNoData.setEnableLoadMore(false);
        datas = new ArrayList<>();

        mainNewsAdapter = new MyNewsCollectAdapter(getActivity(), datas, this);
        recyclerView.setAdapter(mainNewsAdapter);
    }

    @Override
    protected void initData() {
        if (getActivity() instanceof MyCollectionActivity) {
            classify = ((MyCollectionActivity) getActivity()).getClassify();
        }
    }

    private int pageNo = 1;//
    private int pageSize = 20;//
    @Override
    public void onResume() {
        super.onResume();
        getLoadingDialog().showDialog();
        getFootPrintAndCollect();
    }

    /**
     * 我的收藏 我的咨询
     */
    public void getFootPrintAndCollect() {
        viewModel.FootPrintAndCollect(PinziApplication.getInstance().getLoginDto().getId(), "3", classify, pageNo, pageSize);
        viewModel.getCollectionFootListliveData().observe(this, new Observer<BaseDto<List<CollectionFootDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<CollectionFootDto>> listBaseDto) {
                getLoadingDialog().dismiss();
                srNoData.finishRefresh();
                srHasData.finishRefresh();
                srHasData.finishLoadMore();
                //  有数据
                if (EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                    srNoData.setVisibility(View.GONE);
                    srHasData.setVisibility(View.VISIBLE);
                    if (pageNo == 1) {
                        datas.clear();
                    }
                    datas.addAll(listBaseDto.getData());
                    mainNewsAdapter = new MyNewsCollectAdapter(getActivity(), datas, NewsFragment.this);
                    recyclerView.setAdapter(mainNewsAdapter);

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
                getFootPrintAndCollect();
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getFootPrintAndCollect();
            }
        });
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getFootPrintAndCollect();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }


}
