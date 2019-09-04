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

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.SystemMsgAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.MsgDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.utils.BadgeUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统消息
 */
public class SystemMsgActivity extends BaseActivity implements CustomItemClickListener {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SystemMsgAdapter adapter;
    private List<MsgDto> data;
    private SmartRefreshLayout srHasData;
    private SmartRefreshLayout srNoData;
    private MainViewModel viewModel;

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
    protected void onResume() {
        super.onResume();
        getLoadingDialog().showDialog();
        getMsgData();
    }
    private int pageNo = 1;//
    private int pageSize = 20;//
    private void getMsgData() {
        viewModel.SystemInformation(PinziApplication.getInstance().getLoginDto()==null?null:PinziApplication.getInstance().getLoginDto().getId(),pageNo,pageSize,2);
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
                        data.clear();
                    }
                    data.addAll(listBaseDto.getData());
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
    @Override
    public int intiLayout() {
        return R.layout.activity_systemmsg;
    }

    @Override
    public void initView() {
        setNormalTitle("系统消息", v -> finish());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        data = new ArrayList<>();
        adapter = new SystemMsgAdapter(this, data, this);
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
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, SystemMsgDetailActivity.class);
        intent.putExtra("msgDto", data.get(position));
        startActivity(intent);
    }
}
