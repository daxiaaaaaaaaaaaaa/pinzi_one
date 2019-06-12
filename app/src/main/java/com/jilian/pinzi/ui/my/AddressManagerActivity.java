package com.jilian.pinzi.ui.my;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.AddressManagerAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.AddressDto;
import com.jilian.pinzi.common.dto.OrderDto;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.PinziDialogUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.CustomerItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * @author ningpan
 * @since 2018/11/6 10:05 <br>
 * description: 地址管理 Activity
 */
public class AddressManagerActivity extends BaseActivity implements AddressManagerAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private Button btnAddAddress;

    private AddressManagerAdapter mAdapter;
    private List<AddressDto> mDataList;
    private MyViewModel viewModel;
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
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_address_manager;
    }

    @Override
    public void initView() {
        setNormalTitle("地址管理", v -> finish());
        recyclerView = findViewById(R.id.rv_address_manager);
        recyclerView.addItemDecoration(new CustomerItemDecoration(DisplayUtil.dip2px(this, 10)));
        btnAddAddress = findViewById(R.id.btn_address_manager);
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        srNoData.setEnableLoadMore(false);
    }

    @Override
    public void initData() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        mDataList = new ArrayList<>();
        mAdapter = new AddressManagerAdapter(this, R.layout.item_address_manager, mDataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLoadingDialog().showDialog();
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
                getLoadingDialog().dismiss();
                srNoData.finishRefresh();
                srHasData.finishRefresh();
                srHasData.finishLoadMore();
                //有数据
                if (EmptyUtils.isNotEmpty(addressDtoBaseDto.getData())) {
                    srNoData.setVisibility(View.GONE);
                    srHasData.setVisibility(View.VISIBLE);
                    if (pageNo == 1) {
                        mDataList.clear();
                    }
                    mDataList.addAll(addressDtoBaseDto.getData());
                    mAdapter.notifyDataSetChanged();
                } else {
                    //说明是上拉加载
                    if (pageNo > 1) {
                        pageNo--;
                    } else {
                        //第一次就没数据
                        mAdapter.notifyDataSetChanged();
                        srNoData.setVisibility(View.VISIBLE);
                        srHasData.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    public void initListener() {
        btnAddAddress.setOnClickListener(v -> AddAddressActivity.startActivity(this, null));
        mAdapter.setOnItemClickListener(this);
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
    public void onEditClick(View view, int position) {
        Intent intent = new Intent(AddressManagerActivity.this, AddAddressActivity.class);
        intent.putExtra("address", mDataList.get(position));
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(View view, int position) {
        showDeleteOrderDialog(position);

    }

    /**
     * 删除订单
     */
    public void showDeleteOrderDialog(int position)

    {
        Dialog dialog = PinziDialogUtils.getDialogNotTouchOutside(this, R.layout.dialog_delete_tips);
        TextView tvNo = (TextView) dialog.findViewById(R.id.tv_no);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tv_ok);

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                deleteAdress(mDataList.get(position).getId());

            }
        });
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onCheckClick(View view, int position) {
        editAdress(mDataList.get(position).getId());
    }

    /**
     * 编辑收获地址 设置默认
     */
    private void editAdress(String id) {
        getLoadingDialog().showDialog();
        viewModel.editUserAddress(null, null, null, null, null, 1, getLoginDto().getId(), id);
        viewModel.getAddUserAddressliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                getLoadingDialog().dismiss();
                if (stringBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                    ToastUitl.showImageToastSuccess("设置默认地址成功");
                    pageNo = 1;
                    getAddressList();
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }

            }
        });
    }

    /**
     * 删除地址
     */
    private void deleteAdress(String id) {
        getLoadingDialog().showDialog();
        viewModel.deleteUserAddress(id);
        viewModel.getAddUserAddressliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                getLoadingDialog().dismiss();
                if (stringBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                    ToastUitl.showImageToastSuccess("删除成功");
                    getLoadingDialog().showDialog();
                    getAddressList();
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }

            }
        });
    }
}
