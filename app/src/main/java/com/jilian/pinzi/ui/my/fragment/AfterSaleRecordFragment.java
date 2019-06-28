package com.jilian.pinzi.ui.my.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.SaleRecordAdapter;
import com.jilian.pinzi.adapter.common.BaseMultiItemAdapter;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.SaleRecordDto;
import com.jilian.pinzi.ui.SaleRecordDetailActivity;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.CustomerItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 申请记录
 */
public class AfterSaleRecordFragment extends BaseFragment {

    private SmartRefreshLayout srHasData;
    private RecyclerView recyclerView;
    private SmartRefreshLayout srNoData;
    private MyViewModel viewModel;
    private LinearLayoutManager linearLayoutManager;

    private SaleRecordAdapter mAdapter;
    private List<SaleRecordDto> mDataList = new ArrayList<>();

    private Integer pageNo = 1;//false number
    private Integer paegSize = 20;////false number

    @Override
    protected void loadData() {

    }

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_aftersalerecord;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        srHasData = view.findViewById(R.id.sr_has_data);
        recyclerView = view.findViewById(R.id.recyclerView);
        srNoData = view.findViewById(R.id.sr_no_data);

        srNoData.setEnableLoadMore(false);
        srNoData.setEnableLoadMore(false);

        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new SaleRecordAdapter(getmActivity(), R.layout.item_sale_record, mDataList);
        linearLayoutManager = new LinearLayoutManager(getmActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new CustomerItemDecoration(DisplayUtil.dip2px(getmActivity(), 10)));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseMultiItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                SaleRecordDetailActivity.startActivity(getContext(), mDataList.get(position).getId() + "");
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getSaleRecords();
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getSaleRecords();
            }
        });
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getSaleRecords();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        pageNo = 1;
        getSaleRecords();

    }

    /**
     * 获取申请记录数据
     *
     * @return
     */
    private void getSaleRecords() {
        viewModel.getApplyRefundList(PinziApplication.getInstance().getLoginDto().getId(), pageNo, paegSize);
        viewModel.getSaleRecordLiveData().observe(this, saleRecordDtoBaseDto -> {
            getLoadingDialog().dismiss();
            srNoData.finishRefresh();
            srHasData.finishRefresh();
            srHasData.finishLoadMore();
            if (saleRecordDtoBaseDto == null) return;
            if (saleRecordDtoBaseDto.isSuccess()) {
                //有数据
                if (EmptyUtils.isNotEmpty(saleRecordDtoBaseDto.getData())) {
                    srNoData.setVisibility(View.GONE);
                    srHasData.setVisibility(View.VISIBLE);
                    if (pageNo == 1) {
                        mDataList.clear();
                    }
                    mDataList.addAll(saleRecordDtoBaseDto.getData());
                    mAdapter.notifyDataSetChanged();
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
            } else {
                ToastUitl.showImageToastFail(saleRecordDtoBaseDto.getMsg());
            }
        });
    }
}
