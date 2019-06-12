package com.jilian.pinzi.ui.my.fragment;

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
import com.jilian.pinzi.adapter.MyApplyServiceAdapter;
import com.jilian.pinzi.adapter.MyApplyServiceGoodAdapter;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.MyOrderDto;
import com.jilian.pinzi.common.dto.MyOrderGoodsInfoDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.GoodsDetailActivity;
import com.jilian.pinzi.ui.my.ApplyServiceActivity;
import com.jilian.pinzi.ui.my.MyOrderFinishNoCommentDetailActivity;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.RxTimerUtil;
import com.jilian.pinzi.views.CustomerItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class AfterSaleServiceFragment extends BaseFragment implements CustomItemClickListener, MyApplyServiceGoodAdapter.GoodClickListener {
    private MyApplyServiceAdapter adapter;
    private SmartRefreshLayout srHasData;
    private RecyclerView recyclerView;
    private SmartRefreshLayout srNoData;
    public List<MyOrderDto> datas;
    private MyViewModel viewModel;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void loadData() {

    }

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_aftersaleservice;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        srHasData = (SmartRefreshLayout) view.findViewById(R.id.sr_has_data);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        srNoData = (SmartRefreshLayout) view.findViewById(R.id.sr_no_data);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new CustomerItemDecoration(DisplayUtil.dip2px(getActivity(), 10)));
        srNoData.setEnableLoadMore(false);
        datas = new ArrayList<>();
        adapter = new MyApplyServiceAdapter(getActivity(), datas, this,this);
        recyclerView.setAdapter(adapter);
        srNoData.setEnableLoadMore(false);

    }

    @Override
    public void onResume() {
        super.onResume();
        pageNo = 1;
        getOrderDatas();

    }

    private Integer pageNo = 1;//false number
    private Integer paegSize = 20;////false number

    @Override
    protected void initData() {

    }

    /**
     * 获取订单数据
     *
     * @return
     */
    private void getOrderDatas() {
        viewModel.getMyOrderList(PinziApplication.getInstance().getLoginDto().getId(), 3, pageNo, paegSize);
        viewModel.getOrderliveData().observe(this, new Observer<BaseDto<List<MyOrderDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<MyOrderDto>> listBaseDto) {
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

    //递归遍历  获取可以 申请退货的 订单
    private List<MyOrderDto> getData(List<MyOrderDto> data) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getIsReturn() == 1) {
                data.remove(i);
                getData(data);
            }
        }
        return data;

    }

    @Override
    protected void initListener() {
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getOrderDatas();
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getOrderDatas();
            }
        });
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getOrderDatas();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void clickGoods(MyOrderDto dto) {

        Intent intent = new Intent(getActivity(),MyOrderFinishNoCommentDetailActivity.class);
        intent.putExtra("orderId",dto.getId());
        if(dto.getPayStatus()==4){
            intent.putExtra("type",1);
        }
        if(dto.getPayStatus()==5){

        }
        getActivity().startActivity(intent);
    }

    @Override
    public void apply(MyOrderGoodsInfoDto dto) {
        //申请退货
        if(dto.getIsApply()==1){
            return;
        }
        Intent intent = new Intent(getActivity(),ApplyServiceActivity.class);
        intent.putExtra("dto",dto);
        startActivity(intent);

    }


}
