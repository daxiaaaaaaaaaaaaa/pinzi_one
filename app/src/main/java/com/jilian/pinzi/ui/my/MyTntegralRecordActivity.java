package com.jilian.pinzi.ui.my;

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
import com.jilian.pinzi.adapter.MyTntegralRecordAdapter;
import com.jilian.pinzi.adapter.MycouponsRecordAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.MyRecordDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.viewmodel.LotteryViewModel;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MyTntegralRecordActivity extends BaseActivity implements CustomItemClickListener {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<MyRecordDto> datas;
    private MyTntegralRecordAdapter adapter;
    private LotteryViewModel lotteryViewModel;
    private int pageNo = 1;//
    private int pageSize = 2000;//
    private SmartRefreshLayout srHasData;
    private SmartRefreshLayout srNoData;

    @Override
    protected void onResume() {
        super.onResume();
        getLoadingDialog().showDialog();
        getMyRecord();
    }

    /**
     * 获取我的积分记录  全部
     */
    private void getMyRecord() {
        //1.积分 2.钱包 3.佣金
        lotteryViewModel.getMyRecord(PinziApplication.getInstance().getLoginDto().getId(), 1, pageNo, pageSize);
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
                    resetDatas();
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
    private List<String> days;
    private LinkedHashMap<String,List<Double>> maps;
    private void resetDatas() {
        days = new ArrayList<>();
        maps = new LinkedHashMap<>();
        for (int i = 0; i <datas.size() ; i++) {
            String day = DateUtil.dateToString("yyyy年MM月", new Date(datas.get(i).getCreateDate()));
            if (!days.contains(day)) {
                days.add(day);
                datas.get(i).setShowDay(true);
                datas.get(i).setDay(day);

            } else {
                datas.get(i).setShowDay(false);
            }
            Double count = datas.get(i).getStatus()==1?datas.get(i).getSource():-datas.get(i).getSource();
            if(maps.containsKey(day)){
                maps.get(day).add(count);
            }
            else{
                List<Double> list = new ArrayList<>();
                list.add(count);
                maps.put(day,list);
            }
        }
        int count = 0;
        for (Map.Entry<String, List<Double>> entry : maps.entrySet()) {
            datas.get(count).setGetCount(getGetCount(entry.getValue()));
            datas.get(count).setUseCount(getUseCount(entry.getValue()));
            count+=entry.getValue().size();
        }
    }

    /**
     * 求和
     * @param value
     * @return
     */
    private Double getGetCount(List<Double> value) {
        Double getGetCount = 0.00;
        for (int i = 0; i <value.size() ; i++) {
            if(value.get(i)>=0){
                getGetCount+=value.get(i);
            }
        }
        return getGetCount;
    }
    /**
     * 求和
     * @param value
     * @return
     */
    private Double getUseCount(List<Double> value) {
        Double useCount = 0.00;
        for (int i = 0; i <value.size() ; i++) {
            if(value.get(i)<0){
                useCount+=value.get(i);
            }

        }
        return -useCount;
    }

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
        return R.layout.activity_mytntegralrecord;
    }

    @Override
    public void initView() {
        setNormalTitle("积分记录", v -> finish());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        datas = new ArrayList<>();
        adapter = new MyTntegralRecordAdapter(this, datas, this);
        recyclerView.setAdapter(adapter);
        srNoData.setEnableLoadMore(false);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
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
