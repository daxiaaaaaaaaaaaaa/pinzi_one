package com.jilian.pinzi.ui.my;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.MyShipmentGoodAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.GoodsInfoDto;
import com.jilian.pinzi.common.dto.MyOrderDto;
import com.jilian.pinzi.common.dto.MyOrderGoodsInfoDto;
import com.jilian.pinzi.common.dto.OrderDetailDto;
import com.jilian.pinzi.dialog.BaseNiceDialog;
import com.jilian.pinzi.dialog.NiceDialog;
import com.jilian.pinzi.dialog.ViewConvertListener;
import com.jilian.pinzi.dialog.ViewHolder;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.GoodsDetailActivity;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.ui.shopcard.PayOrderActivity;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.MainRxTimerUtil;
import com.jilian.pinzi.utils.NumberUtils;
import com.jilian.pinzi.utils.ToastUitl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单详情 待付款
 */
public class MyOrderWaitePayDetailActivity extends BaseActivity implements MyShipmentGoodAdapter.GoodClickListener {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<GoodsInfoDto> datas;
    private MyShipmentGoodAdapter goodAdapter;
    private MyViewModel viewModel;
    private TextView tvTime;
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
    private TextView tvPayMoney;
    private TextView tvCancel;
    private TextView tvPay;
    private TextView tvOrderNo;
    private TextView tvCommiTime;
    private LinearLayout llInvoice;
    private long fifityMin = 15*60*1000;//15分钟
    private TextView tvShipperName;

    private TextView tvPreMoney;
   // private TextView tvActivityAccount;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PinziApplication.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PinziApplication.removeActivity(this);
        MainRxTimerUtil.cancel();
    }
    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_myorderwaypaydetail;
    }

    @Override
    public void initView() {
        setNormalTitle("订单详情", v -> finish());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        datas = new ArrayList<>();
        recyclerView.setLayoutManager(linearLayoutManager);
        goodAdapter = new MyShipmentGoodAdapter(this,datas,this);
        recyclerView.setAdapter(goodAdapter);
        tvTime = (TextView) findViewById(R.id.tv_time);
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
        tvPayMoney = (TextView) findViewById(R.id.tv_pay_money);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvPay = (TextView) findViewById(R.id.tv_pay);
        tvOrderNo = (TextView) findViewById(R.id.tv_order_no);
        tvCommiTime = (TextView) findViewById(R.id.tv_commi_time);
        llInvoice = (LinearLayout) findViewById(R.id.ll_invoice);
        tvShipperName = (TextView) findViewById(R.id.tv_ShipperName);
        tvPreMoney = (TextView) findViewById(R.id.tv_preMoney);
        //tvActivityAccount = (TextView) findViewById(R.id.tv_activity_account);
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
        long creatTime = data.getCreateDate();
        startTimeTask(creatTime+fifityMin);
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
        tvCardAcount.setText("¥"+NumberUtils.forMatNumber(data.getCouponRemission()));
        //8.积分抵扣
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
        //10.活动金额 ???
        //  tvActivityAccount.setText("¥"+NumberUtils.forMatNumber(data));
        //11.发票类型
        //11.发票类型
        if(data.getType()==1){
            tvInvoiceTye.setText("增值税专用发票");
            tvInvoiceContent.setText("商品");
            llInvoice.setVisibility(View.VISIBLE);
        }
        else  if(data.getType()==2){
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
        tvOrderNo.setText(data.getOrderNo());
        //15.提交时间
        tvCommiTime.setText(DateUtil.dateToString(DateUtil.DEFAULT_DATE_FORMATTER_MIN,new Date(data.getCreateDate())));
//        //16.支付方式
//        switch (data.getPayWay()){
//            case 1:
//                tvPayType.setText("微信");
//                break;
//            case 2:
//                tvPayType.setText("支付宝");
//                break;
//            case 3:
//                tvPayType.setText("积分");
//                break;
//            case 4:
//                tvPayType.setText("货到付款");
//                break;
//        }
        //17.实付金额
        if(data.getPayFirstMoney()>0){
            tvPayMoney.setText("¥"+NumberUtils.forMatNumber(data.getPayFirstMoney()));
        }
        else{
            tvPayMoney.setText("¥"+NumberUtils.forMatNumber(data.getPayMoney()));

        }


        //18.付款时间
        //tvPayTime.setText(DateUtil.dateToString(DateUtil.DEFAULT_DATE_FORMATTER_MIN,new Date(data.getPayDate())));
        //19发货人
        tvShipperName.setText(data.getShipperName());

    }

    /**
     *
     * 开启倒计时
     * @param endTime
     */
    private void startTimeTask(long endTime) {
        MainRxTimerUtil.interval(1000, new MainRxTimerUtil.IRxNext() {
            @Override
            public void doNext() {//获取现在的 时分秒 时间戳
                long nowTime = new Date().getTime();
                //单位 s
                long delTime = endTime - nowTime;
                if(delTime>0){
                    String str  = DateUtil.timeToHms(delTime);
                    tvTime.setText("剩余："+str.split(":")[0]+"时"+str.split(":")[1]+"分"+str.split(":")[2]+"秒");
                }
                else{
                    ToastUitl.showImageToastFail("订单已超时");
                    finish();
                }

            }
        });
    }

    @Override
    public void initData() {
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
        mData.setOrderId(getIntent().getStringExtra("orderId"));
        if(EmptyUtils.isNotEmpty(data.getGoodsInfo())){
            datas.addAll(data.getGoodsInfo());
            goodAdapter.notifyDataSetChanged();
        }
        initOrderDetailView(data);
    }

    @Override
    public void initListener() {

        tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyOrderWaitePayDetailActivity.this,PayOrderActivity.class);
                intent.putExtra("orderDetailDto",mData);
                startActivity(intent);
            }
        });

       tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelOrder();
            }
        });
    }

    /**
     * 取消订单
     */
    private void cancelOrder() {
        if(TextUtils.isEmpty(getIntent().getStringExtra("orderId"))){
            ToastUitl.showImageToastFail("订单不存在");
            return;
        }
        showCancelOrderDialog(getIntent().getStringExtra("orderId"));
    }

    /**
     * 取消订单
     */
    public void showCancelOrderDialog(String orderId) {
        final int[] reason = {1};
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_cancel_order)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        dialog.setOutCancel(false);
                        ImageView ivCancel = (ImageView) holder.getView(R.id.iv_cancel);
                        TextView tvOk = (TextView) holder.getView(R.id.tv_ok);
                        LinearLayout llOne = (LinearLayout) holder.getView(R.id.ll_one);
                        LinearLayout llTwo = (LinearLayout) holder.getView(R.id.ll_two);
                        LinearLayout llThree = (LinearLayout) holder.getView(R.id.ll_three);
                        LinearLayout llFour = (LinearLayout) holder.getView(R.id.ll_four);
                        LinearLayout llFive = (LinearLayout) holder.getView(R.id.ll_five);
                        ImageView ivOne = (ImageView) holder.getView(R.id.iv_one);
                        ImageView ivTwo = (ImageView) holder.getView(R.id.iv_two);
                        ImageView ivThree = (ImageView) holder.getView(R.id.iv_three);
                        ImageView ivFour = (ImageView) holder.getView(R.id.iv_four);
                        ImageView ivFive = (ImageView) holder.getView(R.id.iv_five);

                        llOne.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ivOne.setImageResource(R.drawable.image_checked);
                                ivTwo.setImageResource(R.drawable.image_uncheck);
                                ivThree.setImageResource(R.drawable.image_uncheck);
                                ivFour.setImageResource(R.drawable.image_uncheck);
                                ivFive.setImageResource(R.drawable.image_uncheck);
                                reason[0] = 1;

                            }
                        });
                        llTwo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ivOne.setImageResource(R.drawable.image_uncheck);
                                ivTwo.setImageResource(R.drawable.image_checked);
                                ivThree.setImageResource(R.drawable.image_uncheck);
                                ivFour.setImageResource(R.drawable.image_uncheck);
                                ivFive.setImageResource(R.drawable.image_uncheck);
                                reason[0] = 2;
                            }
                        });
                        llThree.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ivOne.setImageResource(R.drawable.image_uncheck);
                                ivTwo.setImageResource(R.drawable.image_uncheck);
                                ivThree.setImageResource(R.drawable.image_checked);
                                ivFour.setImageResource(R.drawable.image_uncheck);
                                ivFive.setImageResource(R.drawable.image_uncheck);
                                reason[0] = 3;
                            }
                        });

                        llFour.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ivOne.setImageResource(R.drawable.image_uncheck);
                                ivTwo.setImageResource(R.drawable.image_uncheck);
                                ivThree.setImageResource(R.drawable.image_uncheck);
                                ivFour.setImageResource(R.drawable.image_checked);
                                ivFive.setImageResource(R.drawable.image_uncheck);
                                reason[0] = 4;
                            }
                        });
                        llFive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ivOne.setImageResource(R.drawable.image_uncheck);
                                ivTwo.setImageResource(R.drawable.image_uncheck);
                                ivThree.setImageResource(R.drawable.image_uncheck);
                                ivFour.setImageResource(R.drawable.image_uncheck);
                                ivFive.setImageResource(R.drawable.image_checked);
                                reason[0] = 5;
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        tvOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                updateOrderStatus(1, orderId, reason[0]);
                            }
                        });
                    }
                })
                .setShowBottom(true)
                .show(getSupportFragmentManager());
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
    public void clickGoods(String goodId) {
        Intent intent  = new Intent(this,GoodsDetailActivity.class);
        intent.putExtra("goodsId",goodId);
        startActivity(intent);
    }
}
