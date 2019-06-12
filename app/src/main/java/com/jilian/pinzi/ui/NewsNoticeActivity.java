package com.jilian.pinzi.ui;

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
import com.jilian.pinzi.adapter.NewsNoticeAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.AddressDto;
import com.jilian.pinzi.common.dto.MsgDto;
import com.jilian.pinzi.common.dto.NewsDto;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.utils.EmptyUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ningpan
 * @since 2018/10/30 17:34 <br>
 * description: 新闻公告 Activity
 */
public class NewsNoticeActivity extends BaseActivity implements NewsNoticeAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private MainViewModel viewModel;
    private NewsNoticeAdapter mAdapter;
    private List<MsgDto> mDataList = new ArrayList<>();
    private SmartRefreshLayout srHasData;
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
        return R.layout.activity_news_notice;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLoadingDialog().showDialog();
        getMsgData();
    }
    private int pageNo = 1;//
    private int pageSize = 20;//
    private void getMsgData() {
        viewModel.SystemInformation(PinziApplication.getInstance().getLoginDto()==null?null:PinziApplication.getInstance().getLoginDto().getId(),pageNo,pageSize,1);
        viewModel.getMsgliveData().observe(this, new Observer<BaseDto<List<MsgDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<MsgDto>> listBaseDto) {
                getLoadingDialog().dismiss();
                srNoData.finishRefresh();
                srHasData.finishRefresh();
                srHasData.finishLoadMore();
                //有数据
                if (EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                    srNoData.setVisibility(View.GONE);
                    srHasData.setVisibility(View.VISIBLE);
                    if (pageNo == 1) {
                        mDataList.clear();
                    }
                    mDataList.addAll(listBaseDto.getData());
                    mAdapter.notifyDataSetChanged();
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
    public void initView() {
        setCenterTitle("新闻公告", "#FFFFFF");
        setleftImage(R.drawable.image_back, true, null);
        setTitleBg(R.color.color_black);
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        recyclerView = findViewById(R.id.rv_news_notice);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new NewsNoticeAdapter(this, R.layout.item_news_notice, mDataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
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
                getMsgData();
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getMsgData();
            }
        });
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getMsgData();
            }
        });
    }

    @Override
    public void onEditClick(View view, int position) {
        Intent intent = new Intent(this, NewsNoticeDetailActivity.class);
        intent.putExtra("msgDto", mDataList.get(position));
        startActivity(intent);
    }
}
