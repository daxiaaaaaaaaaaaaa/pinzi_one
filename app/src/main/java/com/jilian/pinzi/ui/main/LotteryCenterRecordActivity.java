package com.jilian.pinzi.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.LotteryCenterRecordAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.common.dto.LotteryRecordDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.viewmodel.LotteryViewModel;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 中奖记录
 */
public class LotteryCenterRecordActivity extends BaseActivity implements CustomItemClickListener {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<LotteryRecordDto> datas;
    private LotteryCenterRecordAdapter adapter;
    private SmartRefreshLayout srHasData;
    private SmartRefreshLayout srNoData;
    private LotteryViewModel lotteryViewModel;

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
        lotteryViewModel = ViewModelProviders.of(this).get(LotteryViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_lotterycenterrecord;
    }

    @Override
    public void initView() {
        setNormalTitle("中奖记录", v -> finish());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        recyclerView.setLayoutManager(linearLayoutManager);
        datas = new ArrayList<>();
        adapter = new LotteryCenterRecordAdapter(this, datas, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                startNum = 1;
                getData();
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                startNum++;
                getData();
            }
        });
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                startNum = 1;
                getData();
            }
        });

    }
    private int startNum = 1;//
    private int pageSize = 20;//
    @Override
    public void doBusiness() {
        showLoadingDialog();
        getData();
    }

    /**
     * 加載數據
     */
    private void getData() {
        lotteryViewModel.getLotteryList(startNum,pageSize,PinziApplication.getInstance().getLoginDto().getId());
        if (lotteryViewModel.getListLiveData().hasObservers()) return;
        lotteryViewModel.getListLiveData().observe(this, listBaseDto -> {
            getLoadingDialog().dismiss();
            srNoData.finishRefresh();
            srHasData.finishRefresh();
            srHasData.finishLoadMore();
            //有数据
            if (EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                srNoData.setVisibility(View.GONE);
                srHasData.setVisibility(View.VISIBLE);
                if (startNum == 1) {
                    datas.clear();
                }
                datas.addAll(listBaseDto.getData());
                adapter.notifyDataSetChanged();
            } else {
                //说明是上拉加载
                if (startNum > 1) {
                    startNum--;
                } else {
                    //第一次就没数据
                    srNoData.setVisibility(View.VISIBLE);
                    srHasData.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {


    }
}
