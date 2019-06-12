package com.jilian.pinzi.ui.main;

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
import com.jilian.pinzi.adapter.GetCardCenterAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.CouponCentreDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.ui.my.MyCarddetailActivity;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.RxTimerUtil;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.CustomerItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 領券中心
 */
public class GetCardCenterActivity extends BaseActivity implements CustomItemClickListener, GetCardCenterAdapter.ToReceiveListener {
    private GetCardCenterAdapter adapter;
    private List<CouponCentreDto> datas;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MainViewModel viewModel;
    private SmartRefreshLayout srNoData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PinziApplication.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PinziApplication.removeActivity(this);
    }

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_getcardcenter;
    }

    @Override
    public void initView() {
        setNormalTitle("领券中心", v -> finish());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new CustomerItemDecoration(DisplayUtil.dip2px(this, 10)));
        datas = new ArrayList<>();
        adapter = new GetCardCenterAdapter(this, datas, this,this);
        recyclerView.setAdapter(adapter);
        srNoData.setEnableLoadMore(false);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getCouponCentre();
    }

    /**
     * 获取优惠券 代金券数据
     */
    private void getCouponCentre() {
        viewModel.CouponCentre(PinziApplication.getInstance().getLoginDto().getId());
        viewModel.getCouponliveData().observe(this, new Observer<BaseDto<List<CouponCentreDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<CouponCentreDto>> listBaseDto) {
                srNoData.finishRefresh();
                if (EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                    srNoData.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    datas.clear();
                    datas.addAll(listBaseDto.getData());
                    adapter.notifyDataSetChanged();

                } else {
                    srNoData.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void initListener() {
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
        Intent intent = new Intent(this, MyCarddetailActivity.class);
        intent.putExtra("id", datas.get(position).getId());
        intent.putExtra(Constant.PARAM,"GetCardCenterActivity");
        startActivity(intent);
    }

    /**
     * 领券优惠券
     *
     * @param position
     */
    @Override
    public void toReceive(int position) {
        getLoadingDialog().showDialog();
        viewModel.GetCoupon(datas.get(position).getId(), PinziApplication.getInstance().getLoginDto().getId());
        viewModel.getStringliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                getLoadingDialog().dismiss();
                if (stringBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                    ToastUitl.showImageToastSuccess("领取成功");
                    getCouponCentre();

                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }
            }
        });
    }
}
