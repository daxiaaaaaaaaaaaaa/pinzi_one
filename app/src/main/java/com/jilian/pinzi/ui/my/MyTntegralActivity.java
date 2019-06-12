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
import com.jilian.pinzi.adapter.MyTntegralAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.MyRecordDto;
import com.jilian.pinzi.common.dto.ScoreOrCommissionDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.LotteryCenterActivity;
import com.jilian.pinzi.ui.main.viewmodel.LotteryViewModel;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.NumberUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class MyTntegralActivity extends BaseActivity implements CustomItemClickListener {
    private RecyclerView recyclerView;
    private MyTntegralAdapter pointAdapter;
    private List<MyRecordDto> datas;
    private LinearLayoutManager linearLayoutManager;
    private TextView tvMore;
    private LotteryViewModel lotteryViewModel;
    private int pageNo = 1;//
    private int pageSize = 20;//
    private TextView tvCount;
    private SmartRefreshLayout srHasData;
    private SmartRefreshLayout srNoData;
    private MyViewModel viewModel;

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
                //积分
                tvCount.setText(NumberUtils.forMatZeroNumber(Double.parseDouble(scoreOrCommissionDtoBaseDto.getData().getScoreNum())));
            }
        });
    }

    /**
     * 获取我的积分
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
                    pointAdapter.notifyDataSetChanged();
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
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_point;
    }

    @Override
    public void initView() {
        setCenterTitle("我的积分", "#FFFFFF");
        setleftImage(R.drawable.image_back, true, null);
        setTitleBg(R.color.color_black);
        setrightTitle("积分说明", "#FFFFFF", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MyTntegralActivity.this, MyTntegralDetailActivity.class);
//                intent.putExtra("title","积分说明");
//                intent.putExtra("type",0);
//                startActivity(intent);
                Intent intent = new Intent(MyTntegralActivity.this, OneActivity.class);
                startActivity(intent);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvCount = (TextView) findViewById(R.id.tv_count);
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        tvMore = (TextView) findViewById(R.id.tv_more);
        datas = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        pointAdapter = new MyTntegralAdapter(this, datas, this);
        recyclerView.setAdapter(pointAdapter);
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
        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyTntegralActivity.this, MyTntegralRecordActivity.class));
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
