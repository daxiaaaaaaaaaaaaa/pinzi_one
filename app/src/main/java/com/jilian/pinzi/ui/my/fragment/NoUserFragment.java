package com.jilian.pinzi.ui.my.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.NoUserAdapter;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.MyCouponDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.my.MyCarddetailActivity;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.views.CustomerItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * 我的优惠券 未使用
 */
public class NoUserFragment extends BaseFragment implements CustomItemClickListener {
    private RecyclerView recyclerView;
    private NoUserAdapter noUserAdapter;
    private List<MyCouponDto> datas;
    private LinearLayoutManager linearLayoutManager;
    private SmartRefreshLayout srHasData;
    private SmartRefreshLayout srNoData;
    private MyViewModel viewModel;
    /** 未使用 */
    private static final int STATUS = 0;

    @Override
    protected void loadData() {

    }

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoadingDialog().showDialog();
        getCouponCentre();
    }

    /**
     * 获取优惠券
     */
    private void getCouponCentre() {
        viewModel.getMyCouponList(PinziApplication.getInstance().getLoginDto().getId(),STATUS,pageNo,pageSize);
        viewModel.getMyCouponListliveData().observe(this, new Observer<BaseDto<List<MyCouponDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<MyCouponDto>> listBaseDto) {
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
                    noUserAdapter.notifyDataSetChanged();
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
    protected int getLayoutId() {
        return R.layout.fragment_nouser;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        srHasData = (SmartRefreshLayout) view.findViewById(R.id.sr_has_data);
        srNoData = (SmartRefreshLayout) view.findViewById(R.id.sr_no_data);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new CustomerItemDecoration(DisplayUtil.dip2px(getActivity(), 10)));
        datas = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        noUserAdapter = new NoUserAdapter(getActivity(), datas, this, STATUS);
        recyclerView.setAdapter(noUserAdapter);
        srNoData.setEnableAutoLoadMore(false);
    }

    @Override
    protected void initData() {

    }

    private int pageNo = 1;//
    private int pageSize = 20;//

    @Override
    protected void initListener() {
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getCouponCentre();
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getCouponCentre();
            }
        });
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getCouponCentre();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), MyCarddetailActivity.class);
        intent.putExtra("id", datas.get(position).getId());
        intent.putExtra(Constant.PARAM,"NoUserFragment");
        startActivity(intent);
    }
}
