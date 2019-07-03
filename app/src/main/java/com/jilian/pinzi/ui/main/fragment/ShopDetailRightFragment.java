package com.jilian.pinzi.ui.main.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.ShopCardCShopAdapter;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.StoreCouponDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.LoginActivity;
import com.jilian.pinzi.ui.MainNewsDetailActivity;
import com.jilian.pinzi.ui.main.BuyCardActivity;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.ui.my.MyCarddetailActivity;
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

public class ShopDetailRightFragment extends BaseFragment implements CustomItemClickListener, ShopCardCShopAdapter.ToReceiveListener {

    private ShopCardCShopAdapter adapter;
    private List<StoreCouponDto> datas;
    private LinearLayoutManager linearLayoutManager;
    private MainViewModel viewModel;


    private SmartRefreshLayout srHasData;
    private RecyclerView recyclerView;
    private SmartRefreshLayout srNoData;


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
        srHasData = (SmartRefreshLayout) view.findViewById(R.id.sr_has_data);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        srNoData = (SmartRefreshLayout) view.findViewById(R.id.sr_no_data);
        linearLayoutManager = new LinearLayoutManager(getmActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new CustomerItemDecoration(DisplayUtil.dip2px(getmActivity(), 10)));
        datas = new ArrayList<>();
        adapter = new ShopCardCShopAdapter(getmActivity(), datas, this, this);
        recyclerView.setAdapter(adapter);
        srNoData.setEnableLoadMore(false);

    }

    @Override
    public void onResume() {
        super.onResume();
        pageNo = 1;//
        getStoreCoupon();
    }

    @Override
    protected void initData() {

    }

    private int pageNo = 1;//
    private int pageSize = 20;//

    private void getStoreCoupon() {
        viewModel.getStoreCoupon(getStoreId(), PinziApplication.getInstance().getLoginDto()==null?null:PinziApplication.getInstance().getLoginDto().getId(), pageNo, pageSize);
        viewModel.getStoreCouponData().observe(this, new Observer<BaseDto<List<StoreCouponDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<StoreCouponDto>> listBaseDto) {
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

    /**
     * 获取店铺ID
     *
     * @return
     */
    private String getStoreId() {
        return getmActivity().getIntent().getStringExtra("shopId");
    }

    @Override
    protected void initListener() {
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getStoreCoupon();
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getStoreCoupon();
            }
        });
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getStoreCoupon();
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
        Intent intent = new Intent(getmActivity(), MyCarddetailActivity.class);
        intent.putExtra("id", datas.get(position).getId());
        intent.putExtra(Constant.PARAM, "ShopDetailRightFragment");
        intent.putExtra("data", datas.get(position));
        startActivity(intent);
    }

    /**
     * 领取优惠券
     *
     * @param position
     */
    @Override
    public void toReceive(int position) {
        if (PinziApplication.getInstance().getLoginDto() == null) {
            Intent intent = new Intent(getmActivity(), LoginActivity.class);
            startActivity(intent);
            return;
        }



        getLoadingDialog().showDialog();
        viewModel.GetCoupon(datas.get(position).getId(), PinziApplication.getInstance().getLoginDto().getId());
        viewModel.getStringliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                getLoadingDialog().dismiss();
                if (stringBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                    ToastUitl.showImageToastSuccess("领取成功");
                    pageNo = 1;
                    getStoreCoupon();

                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }
            }
        });
    }

    /**
     * 购买优惠券
     *
     * @param position
     */
    @Override
    public void toBuy(int position) {
        if (PinziApplication.getInstance().getLoginDto() == null) {
            Intent intent = new Intent(getmActivity(), LoginActivity.class);
            startActivity(intent);
            return;
        }

        Intent intent = new Intent(getmActivity(), BuyCardActivity.class);
        intent.putExtra("data", datas.get(position));
        startActivity(intent);
    }
}
