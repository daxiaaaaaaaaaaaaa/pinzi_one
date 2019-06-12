package com.jilian.pinzi.ui.shopcard;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.SelectAdressAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.AddressDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.my.AddAddressActivity;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.views.CustomerItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * 选中收货地址
 */
public class SelectAdressActivity extends BaseActivity implements CustomItemClickListener, SelectAdressAdapter.EditClickListener {
    private RecyclerView recyclerView;
    private SelectAdressAdapter adressAdapter;
    private List<AddressDto> datas;
    private LinearLayoutManager linearLayoutManager;
    private RelativeLayout rlAdd;
    private SmartRefreshLayout srHasData;
    private SmartRefreshLayout srNoData;
    private MyViewModel viewModel;


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
    protected void onResume() {
        super.onResume();
        showLoadingDialog();
        getAddressList();
    }

    private int pageNo = 1;//
    private int pageSize = 20;//

    /**
     * 获取地址列表
     */
    private void getAddressList() {
        viewModel.getUserAddressList(pageNo, pageSize, getLoginDto().getId());
        viewModel.getAddressListliveData().observe(this, new Observer<BaseDto<List<AddressDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<AddressDto>> addressDtoBaseDto) {
                hideLoadingDialog();
                srNoData.finishRefresh();
                srHasData.finishRefresh();
                srHasData.finishLoadMore();
                //有数据
                if (EmptyUtils.isNotEmpty(addressDtoBaseDto.getData())) {
                    srNoData.setVisibility(View.GONE);
                    srHasData.setVisibility(View.VISIBLE);
                    if (pageNo == 1) {
                        datas.clear();
                    }
                    datas.addAll(addressDtoBaseDto.getData());
                    for (int i = 0; i < datas.size(); i++) {
                        if (datas.get(i).getId() .equals( address.getId())) {
                            datas.get(i).setChecked(true);
                        } else {
                            datas.get(i).setChecked(false);
                        }
                    }
                    adressAdapter.notifyDataSetChanged();
                } else {
                    //说明是上拉加载
                    if (pageNo > 1) {
                        pageNo--;
                    } else {
                        datas.clear();
                        //第一次就没数据
                        srNoData.setVisibility(View.VISIBLE);
                        srHasData.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_selectadress;
    }

    @Override
    public void initView() {
        setNormalTitle("选择收货地址", v -> finish());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        rlAdd = (RelativeLayout) findViewById(R.id.rl_add);
        linearLayoutManager = new LinearLayoutManager(this);
        datas = new ArrayList<>();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new CustomerItemDecoration(DisplayUtil.dip2px(this, 10)));
        adressAdapter = new SelectAdressAdapter(this, datas, this, this);
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        recyclerView.setAdapter(adressAdapter);
        srNoData.setEnableRefresh(false);

    }

    private AddressDto address;

    @Override
    public void initData() {
        address = (AddressDto) getIntent().getSerializableExtra("address");
    }

    @Override
    public void initListener() {
        rlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectAdressActivity.this, AddAddressActivity.class));
            }
        });
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getAddressList();
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getAddressList();
            }
        });
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getAddressList();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

        //数据是使用Intent返回
        Intent intent = new Intent();
        //把返回数据存入Intent
        intent.putExtra("address", datas.get(position));

        for (int i = 0; i < datas.size(); i++) {
            if (position == i) {
                datas.get(i).setChecked(true);
            } else {
                datas.get(i).setChecked(false);
            }
        }
        adressAdapter.notifyDataSetChanged();
        //设置返回数据
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void editAdress(int position) {
        Intent intent = new Intent(this, AddAddressActivity.class);
        intent.putExtra("address", datas.get(position));
        startActivity(intent);
    }
    private AddressDto dto;
        @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //数据是使用Intent返回
            Intent intent = new Intent();
            if(EmptyUtils.isNotEmpty(datas)){
                for (int i = 0; i <datas.size() ; i++) {
                    if(datas.get(i).isChecked()){
                        dto =  datas.get(i);
                        break;
                    }
                }
                if(!EmptyUtils.isNotEmpty(dto)){
                    dto = datas.get(0);
                }
            }
            intent.putExtra("address",dto);
            //设置返回数据
            setResult(RESULT_OK, intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
