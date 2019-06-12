package com.jilian.pinzi.ui.my;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.WalletAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.MyRecordDto;
import com.jilian.pinzi.common.dto.ScoreOrCommissionDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.viewmodel.LotteryViewModel;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.EmptyUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class WalletActivity extends BaseActivity implements CustomItemClickListener {
    private RecyclerView recyclerView;
    private WalletAdapter walletAdapter;
    private List<MyRecordDto> datas;
    private LinearLayoutManager linearLayoutManager;
    private TextView tvWithdrawal;

    private LotteryViewModel lotteryViewModel;
    private int pageNo = 1;//
    private int pageSize = 20;//
    private TextView tvCount;
    private SmartRefreshLayout srHasData;
    private SmartRefreshLayout srNoData;
    private MyViewModel viewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PinziApplication.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyRecord();
        getMyScoreOrCommission();
    }
    //结算
    private void getMyScoreOrCommission() {
        viewModel.getMyScoreOrCommission(getUserId());
        viewModel.getAcountLiveData().observe(this, new Observer<BaseDto<ScoreOrCommissionDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<ScoreOrCommissionDto> scoreOrCommissionDtoBaseDto) {
                //余额
                tvCount.setText(String.valueOf(scoreOrCommissionDtoBaseDto.getData().getBalanceNum()));
            }
        });
    }
    /**
     * 获取我的积分
     */
    private void getMyRecord() {
        //1.积分 2.钱包 3.佣金
        lotteryViewModel.getMyRecord(PinziApplication.getInstance().getLoginDto().getId(), 2, pageNo, pageSize);
        lotteryViewModel.getMyRecordLiveData().observe(this, new Observer<BaseDto<List<MyRecordDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<MyRecordDto>> listBaseDto) {
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
                    walletAdapter.notifyDataSetChanged();
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
    protected void onDestroy() {
        super.onDestroy();
        PinziApplication.removeActivity(this);
    }
    @Override
    protected void createViewModel() {
        lotteryViewModel = ViewModelProviders.of(this).get(LotteryViewModel.class);
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_wallet;
    }

    @Override
    public void initView() {
        setCenterTitle("我的钱包", "#FFFFFF");
        setleftImage(R.drawable.image_back, true, null);
        setTitleBg(R.color.color_black);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvWithdrawal = (TextView) findViewById(R.id.tv_withdrawal);

        tvCount = (TextView) findViewById(R.id.tv_count);
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        datas = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        walletAdapter = new WalletAdapter(this, datas, this);
        recyclerView.setAdapter(walletAdapter);
        srNoData.setEnableLoadMore(false);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        //提现
        tvWithdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalletActivity.this, WithdrawalActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });

        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getMyRecord();
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getMyRecord();
            }
        });
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getMyRecord();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
