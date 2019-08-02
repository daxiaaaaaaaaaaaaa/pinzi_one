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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.MyShipmentGoodAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.GoodsInfoDto;
import com.jilian.pinzi.common.dto.OrderDetailDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.MainActivity;
import com.jilian.pinzi.ui.main.GoodsDetailActivity;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.NumberUtils;
import com.jilian.pinzi.utils.PinziDialogUtils;
import com.jilian.pinzi.utils.ToastUitl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单详情 已取消
 */
public class MyOrderCancelDetailActivity extends BaseActivity implements MyShipmentGoodAdapter.GoodClickListener {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<GoodsInfoDto> datas;
    private MyShipmentGoodAdapter goodAdapter;
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
    private TextView tvDelete;
    private TextView tvReBuy;
    private TextView tvOrdeNo;
    private TextView tvCommiTime;
    private TextView tvPayType;
    private MyViewModel viewModel;
    private LinearLayout llInvoice;
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
    }

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_myordercanceldetail;
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
        tvDelete = (TextView) findViewById(R.id.tv_delete);
        tvReBuy = (TextView) findViewById(R.id.tv_re_buy);
        tvOrdeNo = (TextView) findViewById(R.id.tv_ordeNo);
        tvCommiTime = (TextView) findViewById(R.id.tv_commi_time);
        tvPayType = (TextView) findViewById(R.id.tv_pay_type);
        llInvoice = (LinearLayout) findViewById(R.id.ll_invoice);
        tvShipperName = (TextView) findViewById(R.id.tv_ShipperName);
        tvPreMoney = (TextView) findViewById(R.id.tv_preMoney);
        //tvActivityAccount = (TextView) findViewById(R.id.tv_activity_account);

    }

    @Override
    public void initData() {
        getOrderDetail();
    }

    private OrderDetailDto mData;

    /**
     * 获取订单详情
     * public void initData() {
     * getOrderDetail();
     */
    private void getOrderDetail() {
        viewModel.getOrderDetail(getIntent().getStringExtra("orderId"));
        viewModel.getOrderDetail().observe(this, new Observer<BaseDto<OrderDetailDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<OrderDetailDto> dto) {
                hideLoadingDialog();
                if (dto.isSuccess()) {
                    initDataView(dto.getData());
                } else {
                    ToastUitl.showImageToastFail(dto.getMsg());
                }
            }
        });
    }

    /**
     * 显示 各个界面的数据
     *
     * @param data
     */
    private void initOrderDetailView(OrderDetailDto data) {
        if (data.getOrderType() == 2) {
            List<GoodsInfoDto> goodsInfo = new ArrayList<>();
            GoodsInfoDto dto = new GoodsInfoDto();
            dto.setName(data.getAwardName());
            dto.setQuantity("1");
            dto.setGoodsPrice(0);
            goodsInfo.add(dto);
            data.setGoodsInfo(goodsInfo);
        }
        //1.倒计时
        //2.姓名
        tvName.setText(data.getName());
        //3.电话
        try {
            String phone = data.getPhone().substring(0, 3) + "****" + data.getPhone().substring(7, 11);
            tvPhone.setText(phone);

        } catch (Exception e) {
            tvPhone.setText(data.getPhone());
        }
        //4.地址
        tvAdrress.setText(data.getAddress());
        //5.商品合计
        tvGoodAllAcount.setText("¥" + NumberUtils.forMatNumber(data.getGoodsTotalPrice()));
        //6.运费
        tvFright.setText("¥" + NumberUtils.forMatNumber(data.getFreightPrice()));
        //7.优惠券
        tvCardAcount.setText("¥" + NumberUtils.forMatNumber(data.getCouponRemission()));
        //8.积分抵扣
        if (data.getPayScore() > 0) {
            tvPoinsAcount.setText("¥" + NumberUtils.forMatNumber(data.getGoodsTotalPrice()));
        } else {
            tvPoinsAcount.setText("¥" + NumberUtils.forMatNumber(data.getScoreDeduction()));
        }

        //9.佣金抵扣
        tvConponseAccount.setText("¥" + NumberUtils.forMatNumber(data.getCommissionDeduction()));
        //10.定金
        tvPreMoney.setText("¥" + NumberUtils.forMatNumber(data.getPayFirstMoney()));


        //10.活动金额 ???
        //  tvActivityAccount.setText("¥"+NumberUtils.forMatNumber(data));
        //11.发票类型
        if (data.getType() == 1) {
            tvInvoiceTye.setText("增值税专用发票");
            tvInvoiceContent.setText("商品");
            llInvoice.setVisibility(View.VISIBLE);
        } else if (data.getType() == 2) {
            tvInvoiceTye.setText("增值税普通发票");
            tvInvoiceContent.setText("商品");
            llInvoice.setVisibility(View.VISIBLE);
        } else {
            llInvoice.setVisibility(View.GONE);
        }
        //12.发票抬头
        tvInvoiceHead.setText(data.getInvoiceTitle());
        //13.发票内容
        //tvInvoiceContent.setText(data.get);
        //14.订单编号
        tvOrdeNo.setText(data.getOrderNo());
        //15.提交时间
        tvCommiTime.setText(DateUtil.dateToString(DateUtil.DEFAULT_DATE_FORMATTER_MIN, new Date(data.getCreateDate())));
        //16.支付方式
        switch (data.getPayWay()) {
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
        //19发货人
        tvShipperName.setText(data.getShipperName());

    }

    /**
     * 初始化订单详情数据
     *
     * @param data
     */
    private void initDataView(OrderDetailDto data) {
        this.mData = data;
        if (EmptyUtils.isNotEmpty(data.getGoodsInfo())) {
            datas.addAll(data.getGoodsInfo());
            goodAdapter.notifyDataSetChanged();
        }
        initOrderDetailView(data);
    }

    @Override
    public void initListener() {
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteOrderDialog(getIntent().getStringExtra("orderId"));
            }
        });
        tvReBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重新购买
                //重新购买的时候  判断商品数量
                //1个商品 跳到商品详情
                //大于1个商品 跳到 首页
                Intent intent;
                if (datas.size() == 1) {
                    intent = new Intent(MyOrderCancelDetailActivity.this, GoodsDetailActivity.class);
                    intent.putExtra("goodsId", String.valueOf(datas.get(0).getGoodsId()));
                } else {
                    intent = new Intent(MyOrderCancelDetailActivity.this, MainActivity.class);
                }
                startActivity(intent);
            }
        });
    }

    /**
     * 删除订单
     */
    public void showDeleteOrderDialog(String orderId) {
        Dialog dialog = PinziDialogUtils.getDialogNotTouchOutside(this, R.layout.dialog_delete_order_tips);
      /*  TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        TextView tvContent = (TextView)dialog. findViewById(R.id.tv_content);*/
        TextView tvNo = (TextView) dialog.findViewById(R.id.tv_no);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tv_ok);

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                updateOrderStatus(3, orderId, 0);
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
    public void clickGoods(String goodId, GoodsInfoDto dto)
    {
        Intent intent = new Intent(this, GoodsDetailActivity.class);
        if (dto.getIsShow() == 1) {
            ToastUitl.showImageToastFail("商品已经下架");
            return;
        } else {
            //秒杀商品
            if (dto.getIsSeckill() == 1) {
                if (dto.getIsClose() == 0) {
                    ToastUitl.showImageToastFail("限时秒杀活动已经结束");
                    return;
                } else {
                    intent.putExtra("goodsId", goodId);
                    intent.putExtra("type", 2);
                    startActivity(intent);
                }
            }
            //普通商品
            else {

                if (dto.getScoreBuy() > 0) {
                    intent.putExtra("shopType", 2);//积分商城
                }
                intent.putExtra("goodsId", goodId);
                startActivity(intent);
            }

        }


    }
}
