package com.jilian.pinzi.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.SecondsKillZoneAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.MsgDto;
import com.jilian.pinzi.common.dto.SeckillDto;
import com.jilian.pinzi.common.dto.SeckillPrefectureDto;
import com.jilian.pinzi.common.dto.TimeKillGoodsDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.KillRxTimerUtil;
import com.jilian.pinzi.utils.MainRxTimerUtil;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.CustomerItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * 秒杀
 */
public class SecondsKillZoneActivity extends BaseActivity implements CustomItemClickListener {
    private SecondsKillZoneAdapter adapter;
    private List<SeckillPrefectureDto> datas;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private MainViewModel viewModel;
    private SmartRefreshLayout srHasData;
    private SmartRefreshLayout srNoData;
    private RelativeLayout rlTop;
    private TextView tvTime;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PinziApplication.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PinziApplication.removeActivity(this);
        KillRxTimerUtil.cancel();
    }

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_secondskillzone;
    }

    @Override
    public void initView() {
        setNormalTitle("秒杀专区", v -> finish());
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        rlTop = (RelativeLayout) findViewById(R.id.rl_top);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvTime = (TextView) findViewById(R.id.tv_time);
        datas = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new CustomerItemDecoration(1));
        adapter = new SecondsKillZoneAdapter(this, datas, this, getClasses());
        recyclerView.setAdapter(adapter);
        srNoData.setEnableLoadMore(false);


    }

    @Override
    public void initData() {
        //获取秒杀专区的数据
        getSeckillPrefectureData();
    }

    //获取秒杀专区的数据
    private void getSeckillPrefectureData() {
        viewModel.SeckillPrefecture(pageNo, pageSize);
        viewModel.getSeckillPrefectureliveData().observe(this, new Observer<BaseDto<SeckillDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<SeckillDto> seckillPrefectureDtoBaseDto) {
                try {
                    getLoadingDialog().dismiss();
                    srNoData.finishRefresh();
                    srHasData.finishRefresh();
                    srHasData.finishLoadMore();
                    //有数据
                    if (EmptyUtils.isNotEmpty(seckillPrefectureDtoBaseDto.getData())
                            && EmptyUtils.isNotEmpty(seckillPrefectureDtoBaseDto.getData().getTimeKillGoods())) {
                        srNoData.setVisibility(View.GONE);
                        srHasData.setVisibility(View.VISIBLE);
                        rlTop.setVisibility(View.VISIBLE);
                        if (pageNo == 1) {
                            datas.clear();
                        }
                        datas.addAll(seckillPrefectureDtoBaseDto.getData().getTimeKillGoods());
                        adapter.notifyDataSetChanged();
                        //看是否是点击
                        if (isClick)
                        {
                            if (datas.size() == preSize) {
                                Intent intent = new Intent(SecondsKillZoneActivity.this, GoodsDetailActivity.class);
                                intent.putExtra("goodsId", datas.get(position).getId());
                                intent.putExtra("type", 2);
                                intent.putExtra("classes", getClasses());
                                startActivity(intent);
                            } else {
                                ToastUitl.showImageToastFail("商品已经下架");
                            }
                        }
                        isClick = false;
                        SecondsKillZoneActivity.this.position = 0;

                    } else {
                        //说明是上拉加载
                        if (pageNo > 1) {
                            pageNo--;
                        } else {
                            //第一次就没数据
                            srNoData.setVisibility(View.VISIBLE);
                            srHasData.setVisibility(View.GONE);
                            rlTop.setVisibility(View.GONE);
                        }
                    }
                    if (EmptyUtils.isNotEmpty(seckillPrefectureDtoBaseDto.getData().getEndTime())) {
                        //开启倒计时 单位S
                        long endTime = seckillPrefectureDtoBaseDto.getData().getEndTime();
                        initTimeTask(endTime);
                    }


                } catch (Exception e) {
                    Log.e(TAG, "getSeckillPrefectureData{}" + e);
                }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    /**
     * //开启倒计时
     *
     * @param endTime
     */
    private void initTimeTask(long endTime) {
        KillRxTimerUtil.interval(1000, new KillRxTimerUtil.IRxNext() {
            @Override
            public void doNext() {
                long nowTime = 0;
                try {
                    nowTime = DateUtil.stringToDate(DateUtil.DATE_FORMAT_TIME, DateUtil.dateToString(DateUtil.DATE_FORMAT_TIME, new Date())).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long delTime = endTime - nowTime;
                String str = null;
                if (delTime <= 0) {
                    str = "00:00:00";
                    finish();
                } else {
                    str = DateUtil.timeToHms(delTime);
                }
                tvTime.setText("距离本场结束剩余" + str);
            }
        });
    }

    private int pageNo = 1;//
    private int pageSize = 20;//

    @Override
    public void initListener() {
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getSeckillPrefectureData();
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getSeckillPrefectureData();
            }
        });
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getSeckillPrefectureData();
            }
        });

    }

    public int getClasses() {
        return getIntent().getIntExtra("classes", 1);
    }

    private int preSize;
    private boolean isClick;
    private int position;

    @Override
    public void onItemClick(View view, int position) {
        //先刷新一下接口
        //先前的数据长度
        preSize = datas.size();
        //刷新接口
        pageNo = 1;
        this.position = position;
        isClick = true;
        showLoadingDialog();
        getSeckillPrefectureData();


    }
}
