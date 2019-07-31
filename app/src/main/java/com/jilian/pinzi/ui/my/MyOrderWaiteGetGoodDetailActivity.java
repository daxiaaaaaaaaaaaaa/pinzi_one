package com.jilian.pinzi.ui.my;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.MyShipmentGoodAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.GoodsInfoDto;
import com.jilian.pinzi.common.dto.OrderDetailDto;
import com.jilian.pinzi.common.dto.OrderTrackDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.OrderTrackActivity;
import com.jilian.pinzi.ui.main.GoodsDetailActivity;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.ui.viewmodel.OrderViewModel;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.NumberUtils;
import com.jilian.pinzi.utils.PinziDialogUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.utils.UrlUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单详情 待收货
 */
public class MyOrderWaiteGetGoodDetailActivity extends BaseActivity implements MyShipmentGoodAdapter.GoodClickListener {
      private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<GoodsInfoDto> datas;
    private MyShipmentGoodAdapter goodAdapter;
    private TextView tvOk;
    private int type;
    private TextView tvOrderStatus;
    private TextView tvSenType;
    private LinearLayout llTrack;
    private TextView tvAcceptStation;
    private TextView tvAcceptTime;
    private TextView tvName;
    private TextView tvPhone;
    private TextView tvAdrress;
    private TextView tvGoodAllAcount;
    private TextView tvFright;
    private TextView tvCardAcount;
    private TextView tvPoinsAcount;
    private TextView tvConponseAccount;
    private TextView tvInvoiceTye;
    private TextView tvInvoiceHead;
    private TextView tvInvoiceContent;
    private TextView tvOderNo;
    private TextView tvCommiTime;
    private TextView tvPayType;
    private TextView tvPayMoney;
    private TextView tvPayTime;
    private TextView tvCopy;
    private TextView tvSeeTrack;
    private MyViewModel viewModel;
    private OrderViewModel orderViewModel;
    private LinearLayout llInvoice;
    private TextView tvShipperName;

    private TextView tvPreMoney;
  //  private TextView tvActivityAccount;

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
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_myorderwaitegetgooddetail;
    }

    @Override
    public void initView() {
        setNormalTitle("订单详情", v -> finish());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        datas = new ArrayList<>();
        recyclerView.setLayoutManager(linearLayoutManager);
        goodAdapter = new MyShipmentGoodAdapter(this, datas, this);
        recyclerView.setAdapter(goodAdapter);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        tvOrderStatus = (TextView) findViewById(R.id.tv_order_status);
        tvSenType = (TextView) findViewById(R.id.tv_sen_type);
        llTrack = (LinearLayout) findViewById(R.id.ll_track);
        tvAcceptStation = (TextView) findViewById(R.id.tv_AcceptStation);
        tvAcceptTime = (TextView) findViewById(R.id.tv_AcceptTime);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvPhone = (TextView) findViewById(R.id.tv_phone);
        tvAdrress = (TextView) findViewById(R.id.tv_adrress);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvGoodAllAcount = (TextView) findViewById(R.id.tv_good_all_acount);
        tvFright = (TextView) findViewById(R.id.tv_fright);
        tvCardAcount = (TextView) findViewById(R.id.tv_card_acount);
        tvPoinsAcount = (TextView) findViewById(R.id.tv_poins_acount);
        tvConponseAccount = (TextView) findViewById(R.id.tv_conponse_account);
        tvInvoiceTye = (TextView) findViewById(R.id.tv_invoice_tye);
        tvInvoiceHead = (TextView) findViewById(R.id.tv_invoice_head);
        tvInvoiceContent = (TextView) findViewById(R.id.tv_invoice_content);
        tvOderNo = (TextView) findViewById(R.id.tv_oder_no);
        tvCommiTime = (TextView) findViewById(R.id.tv_commi_time);
        tvPayType = (TextView) findViewById(R.id.tv_pay_type);
        tvPayMoney = (TextView) findViewById(R.id.tv_pay_money);
        tvPayTime = (TextView) findViewById(R.id.tv_pay_time);
        tvCopy = (TextView) findViewById(R.id.tv_copy);
        tvSeeTrack = (TextView) findViewById(R.id.tv_see_track);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        llInvoice = (LinearLayout) findViewById(R.id.ll_invoice);
        tvShipperName = (TextView) findViewById(R.id.tv_ShipperName);
        tvPreMoney = (TextView) findViewById(R.id.tv_preMoney);
      //  tvActivityAccount = (TextView) findViewById(R.id.tv_activity_account);
    }
    /**
     * 显示 各个界面的数据
     * @param data
     */
    private void initOrderDetailView(OrderDetailDto data) {
        if(data.getOrderType()==2){
            List<GoodsInfoDto>  goodsInfo = new ArrayList<>();
            GoodsInfoDto dto = new GoodsInfoDto();
            dto.setName(data.getAwardName());
            dto.setQuantity("1");
            dto.setGoodsPrice(0);
            goodsInfo.add(dto);
            data.setGoodsInfo(goodsInfo);
        }
        //1.倒计时
//        long creatTime = data.getCreateDate();
//        startTimeTask(creatTime+fifityMin);
        //2.姓名
        tvName.setText(data.getName());
        //3.电话
        try {
            String phone = data.getPhone().substring(0, 3) + "****" + data.getPhone().substring(7, 11);
            tvPhone.setText(phone);

        }
        catch (Exception e){
            tvPhone.setText(data.getPhone());
        }

        //4.地址
        tvAdrress.setText( data.getAddress());
        //5.商品合计
        tvGoodAllAcount.setText("¥"+NumberUtils.forMatNumber(data.getGoodsTotalPrice()));
        //6.运费
        tvFright.setText("¥"+NumberUtils.forMatNumber(data.getFreightPrice()));
        //7.优惠券
        tvCardAcount.setText("¥"+NumberUtils.forMatNumber(data.getCouponRemission()));    //8.积分抵扣
        if(data.getPayScore()>0){
            tvPoinsAcount.setText("¥"+NumberUtils.forMatNumber(data.getGoodsTotalPrice()));
        }
        else{
            tvPoinsAcount.setText("¥"+NumberUtils.forMatNumber(data.getScoreDeduction()));
        }
        //9.佣金抵扣
        tvConponseAccount.setText("¥"+NumberUtils.forMatNumber(data.getCommissionDeduction()));
        //10.定金
        tvPreMoney.setText("¥"+NumberUtils.forMatNumber(data.getPayFirstMoney()));
        //11.发票类型
        if(data.getType()==1){
            tvInvoiceTye.setText("增值税专用发票");
            tvInvoiceContent.setText("商品");
            llInvoice.setVisibility(View.VISIBLE);
        }
        else if(data.getType()==2){
            tvInvoiceTye.setText("增值税普通发票");
            tvInvoiceContent.setText("商品");
            llInvoice.setVisibility(View.VISIBLE);
        }
        else{
            llInvoice.setVisibility(View.GONE);
        }
        //12.发票抬头
        tvInvoiceHead.setText(data.getInvoiceTitle());
        //13.发票内容
        //tvInvoiceContent.setText(data.get);
        //14.订单编号
        tvOderNo.setText(data.getOrderNo());
        //15.提交时间
        tvCommiTime.setText(DateUtil.dateToString(DateUtil.DEFAULT_DATE_FORMATTER_MIN,new Date(data.getCreateDate())));
//        //16.支付方式
        switch (data.getPayWay()){
            case 1:
                tvPayType.setText("微信");
                break;
            case 2:
                tvPayType.setText("支付宝");
                break;
            case 3:
                tvPayType.setText("积分");
                break;
            case 4:
                tvPayType.setText("货到付款");
                break;
        }
        //17.实付金额
//        if(data.getPayFirstMoney()>0){
//            tvPayMoney.setText("¥"+NumberUtils.forMatNumber(data.getPayFirstMoney()));
//        }
//        else{
//            tvPayMoney.setText("¥"+NumberUtils.forMatNumber(data.getPayMoney()));
//
//        }
        tvPayMoney.setText("¥"+NumberUtils.forMatNumber(data.getPayMoney()));

        //18.付款时间
        tvPayTime.setText(DateUtil.dateToString(DateUtil.DEFAULT_DATE_FORMATTER_MIN,new Date(data.getPayDate())));

        //19发货人
        tvShipperName.setText(data.getShipperName());
    }
    @Override
    public void initData() {
        type = getIntent().getIntExtra("type",0);
        if(type==1){
            tvOrderStatus.setText("等待发货");
            tvSeeTrack.setVisibility(View.GONE);
            llTrack.setVisibility(View.GONE);
        }
        if(type==2){
            tvOrderStatus.setText("等待收货");
            llTrack.setVisibility(View.VISIBLE);
        }
        getOrderDetail();
    }
    private OrderDetailDto mData;
    /**
     * 获取订单详情
     */
    private void getOrderDetail() {
        viewModel.getOrderDetail(getIntent().getStringExtra("orderId"));
        viewModel.getOrderDetail().observe(this, new Observer<BaseDto<OrderDetailDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<OrderDetailDto> dto) {
                hideLoadingDialog();
                if(dto.isSuccess()){
                    initDataView(dto.getData());
                }
                else{
                    ToastUitl.showImageToastFail(dto.getMsg());
                }
            }
        });
    }
    /**
     * 初始化订单详情数据
     * @param data
     */
    private void initDataView(OrderDetailDto data) {
        this.mData =data;
        if(EmptyUtils.isNotEmpty(this.mData)){
            initOrderDetailView(data);
            showDataview();
        }
        if(EmptyUtils.isNotEmpty(data.getGoodsInfo())){
            datas.addAll(data.getGoodsInfo());
            goodAdapter.notifyDataSetChanged();
        }

    }
    @Override
    public void initListener() {
        tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 从API11开始android推荐使用android.content.ClipboardManager
                // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(tvOderNo.getText());
                ToastUitl.showImageToastSuccess("复制订单号成功");
            }
        });
        tvSeeTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!EmptyUtils.isNotEmpty(mDataList)){
                    ToastUitl.showImageToastFail("暂无物流信息");
                    return;
                }
                Intent intent = new Intent(MyOrderWaiteGetGoodDetailActivity.this,OrderTrackActivity.class);
                intent.putExtra("orderId",getIntent().getStringExtra("orderId"));
                startActivity(intent);
            }
        });
        llTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!EmptyUtils.isNotEmpty(mDataList)){
                    ToastUitl.showImageToastFail("暂无物流信息");
                    return;
                }
                Intent intent = new Intent(MyOrderWaiteGetGoodDetailActivity.this,OrderTrackActivity.class);
                intent.putExtra("orderId",getIntent().getStringExtra("orderId"));
                startActivity(intent);
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmGoodsTipsDialog();
            }
        });
    }

    /**
     * 确认收货
     */
    private void showConfirmGoodsTipsDialog() {
        Dialog dialog = PinziDialogUtils.getDialogNotTouchOutside(this, R.layout.dialog_confirm_order_tips);
        TextView tvNo = (TextView) dialog.findViewById(R.id.tv_no);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tv_ok);

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                updateOrderStatus(2, getIntent().getStringExtra("orderId"), 0);
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
    /**
     * 更新订单状态
     *
     * @param status
     * @param orderId
     * @param reason
     * @return
     */
    private void updateOrderStatus(Integer status, String orderId, int reason) {
        getLoadingDialog().showDialog();
        viewModel.updateOrderStatus(status, orderId, reason);
        viewModel.getOrderStatusliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                if (stringBaseDto.isSuccess()) {
                    ToastUitl.showImageToastSuccess("操作成功");
                    finish();
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                    getLoadingDialog().dismiss();
                }
            }
        });
    }

    @Override
    public void clickGoods(String goodId,GoodsInfoDto dto) {
        if(mData.getOrderType()==2){
            return;
        }
        Intent intent  = new Intent(this,GoodsDetailActivity.class);
        intent.putExtra("goodsId",goodId);
        if (dto.getScoreBuy() > 0) {
            intent.putExtra("shopType", 2);//积分商城
        }

        startActivity(intent);
    }
    private List<OrderTrackDto.LogisticsBean> mDataList = new ArrayList<>();

    private OrderTrackDto mOrderTrack;

    public void showDataview() {
        showLoadingDialog();
        orderViewModel.getOrderOfLogistics(getIntent().getStringExtra("orderId"));
        if (orderViewModel.getOrderTrackLiveData().hasObservers()) return;
        orderViewModel.getOrderTrackLiveData().observe(this, orderTrackDtoBaseDto -> {
            hideLoadingDialog();
            if (orderTrackDtoBaseDto == null) return;
            if (orderTrackDtoBaseDto.isSuccess()&&EmptyUtils.isNotEmpty( orderTrackDtoBaseDto.getData())) {
                mOrderTrack = orderTrackDtoBaseDto.getData();
                //19.快递
                tvSenType.setText(mOrderTrack.getDeliveryMethodName());

                if (EmptyUtils.isNotEmpty(mOrderTrack.getLogistics())) {
                    mDataList.addAll(mOrderTrack.getLogistics());
                    if(!TextUtils.isEmpty(mOrderTrack.getLogistics().get(0).getAcceptStation())){
                        tvAcceptStation.setText(mOrderTrack.getLogistics().get(0).getAcceptStation());
                    }
                    else{
                        tvAcceptStation.setText("您的订单已进入库房，准备出库");
                    }
                    if(!TextUtils.isEmpty(mOrderTrack.getLogistics().get(0).getAcceptTime())){
                        tvAcceptTime.setText(mOrderTrack.getLogistics().get(0).getAcceptTime());
                    }
                    else{
                        tvAcceptTime.setText(DateUtil.dateToString(DateUtil.DEFAULT_DATE_FORMATTER_MIN,new Date(mData.getPayDate())));
                    }

                }
                else{
                    tvAcceptStation.setText("您的订单已进入库房，准备出库");
                    tvAcceptTime.setText(DateUtil.dateToString(DateUtil.DEFAULT_DATE_FORMATTER_MIN,new Date(mData.getPayDate())));
                }
            } else {
                ToastUitl.showImageToastFail(orderTrackDtoBaseDto.getMsg());
            }
        });
    }
}
