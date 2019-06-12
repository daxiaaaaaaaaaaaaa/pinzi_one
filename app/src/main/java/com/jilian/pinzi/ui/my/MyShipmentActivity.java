package com.jilian.pinzi.ui.my;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.MyShipmentOrderAdapter;
import com.jilian.pinzi.adapter.common.CommonArrayWheelAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.DeliveryMethodDto;
import com.jilian.pinzi.common.dto.MsgDto;
import com.jilian.pinzi.common.dto.ShippmentDto;
import com.jilian.pinzi.dialog.BaseNiceDialog;
import com.jilian.pinzi.dialog.NiceDialog;
import com.jilian.pinzi.dialog.ViewConvertListener;
import com.jilian.pinzi.dialog.ViewHolder;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.MainActivity;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
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
 * 我的发货
 */
public class MyShipmentActivity extends BaseActivity implements CustomItemClickListener,MyShipmentOrderAdapter.SendListener {
    private RecyclerView rvOrder;
    private MyShipmentOrderAdapter orderAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<ShippmentDto> data;
    private SmartRefreshLayout srHasData;
    private SmartRefreshLayout srNoData;
    private MyViewModel viewModel;
    private List<DeliveryMethodDto> list;

    @Override
    protected void onResume() {
        super.onResume();
        //获取我的发货列表
        showLoadingDialog();
        getMyShippmentList();
    }

    private int pageNo = 1;//
    private int pageSize = 20;//

    /**
     * 获取发货列表
     */
    private void getMyShippmentList() {
        viewModel.getMyShippmentList(PinziApplication.getInstance().getLoginDto().getId(), pageNo, pageSize);
        viewModel.getShippmentListliveData().observe(this, new Observer<BaseDto<List<ShippmentDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<ShippmentDto>> listBaseDto) {
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
                    orderAdapter.notifyDataSetChanged();
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
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_myshipment;
    }

    @Override
    public void initView() {
        setNormalTitle("我的发货", v -> finish());
        rvOrder = (RecyclerView) findViewById(R.id.rv_order);
        linearLayoutManager = new LinearLayoutManager(this);
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        rvOrder.setLayoutManager(linearLayoutManager);
        rvOrder.addItemDecoration(new CustomerItemDecoration(DisplayUtil.dip2px(this, 10)));
        data = new ArrayList<>();
        orderAdapter = new MyShipmentOrderAdapter(this, data, this,this);
        rvOrder.setAdapter(orderAdapter);
        srNoData.setEnableLoadMore(false);
    }

    private void showShippmentDialog(ShippmentDto dto) {
        Dialog dialog = PinziDialogUtils.getDialogNotTouchOutside(this, R.layout.dialog_shipment);
        EditText ettCourierNumber = (EditText) dialog.findViewById(R.id.ett_courierNumber);
        TextView tvCourierCompany = (TextView) dialog.findViewById(R.id.tv_courierCompany);
        TextView tvNo = (TextView) dialog.findViewById(R.id.tv_no);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tv_ok);
        dialog.show();
        tvCourierCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!EmptyUtils.isNotEmpty(list)) {
                    ToastUitl.showImageToastFail("未获取到快递公司");
                    return;
                }
                showSelectCourier(tvCourierCompany);
            }
        });
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(ettCourierNumber.getText().toString())
                        ||TextUtils.isEmpty(conpaneyId)
                        ||TextUtils.isEmpty(dto.getOrderNo())
                        ){
                    ToastUitl.showImageToastFail("请填写数据完整");
                    return;

                }
                dialog.dismiss();
                //发货
                deliverGoods(dto.getId(),ettCourierNumber.getText().toString(),conpaneyId);

            }
        });
    }

    /**
     * 发货
     *
     * @param id 订单id
     * @param courierNumber  快递单号
     * @param courierCompany 快递公司ID
     */
    private void deliverGoods(String id, String courierNumber, String courierCompany) {
        showLoadingDialog();
        viewModel.deliverGoods(id,courierNumber,courierCompany);
        viewModel.getDeliverGoodsliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                hideLoadingDialog();
                if(stringBaseDto.isSuccess()){
                    getMyShippmentList();
                    ToastUitl.showImageToastSuccess("发货成功");
                }
                else{
                    ToastUitl.showImageToastSuccess(stringBaseDto.getMsg());
                }

            }
        });
    }

    private String selectName;
    private String conpaneyId;

    /**
     * 选择快递公司
     */
    private void showSelectCourier(TextView tvCourierCompany) {

        NiceDialog.init()
                .setLayoutId(R.layout.dialog_select_courier)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        dialog.setOutCancel(false);
                        WheelView wheelview = (WheelView) holder.getView(R.id.wheelview);
                        wheelview.setCyclic(false);
                        TextView tvCancel = (TextView) holder.getView(R.id.tv_cancel);
                        TextView tvFinish = (TextView) holder.getView(R.id.tv_finish);
                        wheelview.setAdapter(new CommonArrayWheelAdapter(list));

                        tvFinish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                tvCourierCompany.setText(list.get(wheelview.getCurrentItem()).getDeliveryMethodName());
                                conpaneyId = list.get(wheelview.getCurrentItem()).getId();
                                dialog.dismiss();
                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        wheelview.setCurrentItem(0);
                    }
                })
                .setShowBottom(true)
                .show(getSupportFragmentManager());
    }

    @Override
    public void initData() {
        getDeliveryMethodList();

    }

    /**
     * 获取快递公司
     */
    private void getDeliveryMethodList() {
        viewModel.getDeliveryMethodList();
        viewModel.getDeliveryMethodliveData().observe(this, new Observer<BaseDto<List<DeliveryMethodDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<DeliveryMethodDto>> listBaseDto) {
                list = listBaseDto.getData();
            }
        });
    }

    @Override
    public void initListener() {
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getMyShippmentList();
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getMyShippmentList();
            }
        });
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getMyShippmentList();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, MyShipmentDetailActivity.class);
        intent.putExtra("orderId",data.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void send(int position) {
        showShippmentDialog(data.get(position));
    }

    @Override
    public void mySend(int position) {
        //自己送
        deliverGoods(data.get(position).getId(),null,null);
    }
}
