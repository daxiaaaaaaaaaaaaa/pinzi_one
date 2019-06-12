package com.jilian.pinzi.ui.shopcard;

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
import com.jilian.pinzi.adapter.SelectPersonAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.AddressDto;
import com.jilian.pinzi.common.dto.ShipperDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.utils.EmptyUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择发货人
 */
public class SelectPersonActivity extends BaseActivity implements CustomItemClickListener {
    private SelectPersonAdapter personAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<ShipperDto> datas;
    private MainViewModel viewModel;
    private SmartRefreshLayout srHasData;
    private SmartRefreshLayout srNoData;
    private String shipper;
    private TextView tvOk;


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
        return R.layout.activity_selectperson;
    }

    @Override
    public void initView() {
        setNormalTitle("选择发货人", v -> finish());
        tvOk = (TextView) findViewById(R.id.tv_ok);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        srNoData.setEnableLoadMore(false);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        datas = new ArrayList<>();
        ShipperDto dto = new ShipperDto();
        dto.setChecked(false);
        dto.setName("平台发货");
        dto.setuId("0");
        datas.add(dto);
        personAdapter = new SelectPersonAdapter(this, datas, this);
        recyclerView.setAdapter(personAdapter);
    }

    @Override
    public void initData() {
        shipper = getIntent().getStringExtra("shipper");
        getShipperList();
    }

    private int pageNo = 1;//
    private int pageSize = 20;//

    /**
     * 获取发货人
     */
    private void getShipperList() {
        viewModel.getShipperList(getLoginDto().getId(), pageNo, pageSize);
        viewModel.getShipperliveData().observe(this, new Observer<BaseDto<List<ShipperDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<ShipperDto>> dto) {
                getLoadingDialog().dismiss();
                srNoData.finishRefresh();
                srHasData.finishRefresh();
                srHasData.finishLoadMore();
                //有数据
                if (EmptyUtils.isNotEmpty(dto.getData())) {
                    srNoData.setVisibility(View.GONE);
                    srHasData.setVisibility(View.VISIBLE);
                    if (pageNo == 1) {
                        datas.clear();
                        ShipperDto shipperDto = new ShipperDto();
                        shipperDto.setuId("0");
                        shipperDto.setChecked(false);
                        shipperDto.setName("平台发货");
                        datas.add(shipperDto);
                    }

                    datas.addAll(dto.getData());
                    initCheck(datas);
                    personAdapter.notifyDataSetChanged();
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
     * 设置选择
     * @param datas
     */
    private void initCheck(List<ShipperDto> datas) {
        for (int i = 0; i <datas.size() ; i++) {
            if(datas.get(i).getuId().equals(shipper)){
                datas.get(i).setChecked(true);
            }
            else{
                datas.get(i).setChecked(false);
            }
        }
    }

    @Override
    public void initListener() {
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //数据是使用Intent返回
                Intent intent = new Intent();
                //把返回数据存入Intent
                if (EmptyUtils.isNotEmpty(datas)) {
                    for (int i = 0; i < datas.size(); i++) {
                        if (datas.get(i).isChecked()) {
                            intent.putExtra("data", datas.get(i));
                            break;
                        }
                    }
                }
                //设置返回数据
                setResult(RESULT_OK, intent);
                finish();

            }
        });
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getShipperList();
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getShipperList();
            }
        });
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getShipperList();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        for (int i = 0; i < datas.size(); i++) {
            if (i == position) {
                datas.get(i).setChecked(true);
            } else {
                datas.get(i).setChecked(false);
            }
        }
        personAdapter.notifyDataSetChanged();
    }
}
