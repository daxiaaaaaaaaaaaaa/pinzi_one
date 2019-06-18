package com.jilian.pinzi.ui.main.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.GetCardCShopAdapter;
import com.jilian.pinzi.adapter.GetCardCenterAdapter;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.CouponCentreDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.BuyCardActivity;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.ui.my.MyCarddetailActivity;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.CustomerItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class ShopDetailRightFragment extends BaseFragment implements CustomItemClickListener, GetCardCShopAdapter.ToReceiveListener {

    private GetCardCShopAdapter adapter;
    private List<CouponCentreDto> datas;
    private RecyclerView recyclerView;
    private SmartRefreshLayout srNoData;


    private LinearLayoutManager linearLayoutManager;
    private MainViewModel viewModel;

    @Override
    protected void loadData() {

    }

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shopdetailright;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        srNoData = (SmartRefreshLayout) view.findViewById(R.id.sr_no_data);
        srNoData = (SmartRefreshLayout) view.findViewById(R.id.sr_no_data);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new CustomerItemDecoration(DisplayUtil.dip2px(getActivity(), 10)));
        datas = new ArrayList<>();
        adapter = new GetCardCShopAdapter(getActivity(), datas, this, this);
        recyclerView.setAdapter(adapter);
        srNoData.setEnableLoadMore(false);

    }

    /**
     * 获取优惠券 代金券数据
     */
    private void getCouponCentre() {
//        viewModel.CouponCentre(PinziApplication.getInstance().getLoginDto().getId());
//        viewModel.getCouponliveData().observe(this, new Observer<BaseDto<List<CouponCentreDto>>>() {
//            @Override
//            public void onChanged(@Nullable BaseDto<List<CouponCentreDto>> listBaseDto) {
//                srNoData.finishRefresh();
//                if (EmptyUtils.isNotEmpty(listBaseDto.getData())) {
//                    srNoData.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
//                    datas.clear();
//                    datas.addAll(listBaseDto.getData());
//                    adapter.notifyDataSetChanged();
//
//                } else {
//                    srNoData.setVisibility(View.VISIBLE);
//                    recyclerView.setVisibility(View.GONE);
//                }
//            }
//        });
    }

    @Override
    protected void initData() {
        getCouponCentre();
    }

    @Override
    protected void initListener() {
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getCouponCentre();
            }
        });


    }

    /**
     * 优惠券详情
     *
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), MyCarddetailActivity.class);
        intent.putExtra("id", datas.get(position).getId());
        intent.putExtra(Constant.PARAM, "GetCardCenterActivity");
        startActivity(intent);
    }

    /**
     * 领券优惠券
     *
     * @param position
     */
    @Override
    public void toReceive(int position) {
        startActivity(new Intent(getActivity(), BuyCardActivity.class));
//        getLoadingDialog().showDialog();
//        viewModel.GetCoupon(datas.get(position).getId(), PinziApplication.getInstance().getLoginDto().getId());
//        viewModel.getStringliveData().observe(this, new Observer<BaseDto<String>>() {
//            @Override
//            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
//                getLoadingDialog().dismiss();
//                if (stringBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
//                    ToastUitl.showImageToastSuccess("领取成功");
//                    getCouponCentre();
//
//                } else {
//                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
//                }
//            }
//        });
    }
}
