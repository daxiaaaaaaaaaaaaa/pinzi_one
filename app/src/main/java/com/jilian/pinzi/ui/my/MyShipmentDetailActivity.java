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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.contrarywind.view.WheelView;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.MyShipmentGoodAdapter;
import com.jilian.pinzi.adapter.common.CommonArrayWheelAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.DeliveryMethodDto;
import com.jilian.pinzi.common.dto.GoodsInfoDto;
import com.jilian.pinzi.common.dto.OrderDetailDto;
import com.jilian.pinzi.dialog.BaseNiceDialog;
import com.jilian.pinzi.dialog.NiceDialog;
import com.jilian.pinzi.dialog.ViewConvertListener;
import com.jilian.pinzi.dialog.ViewHolder;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.GoodsDetailActivity;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.MainRxTimerUtil;
import com.jilian.pinzi.utils.NumberUtils;
import com.jilian.pinzi.utils.PinziDialogUtils;
import com.jilian.pinzi.utils.ToastUitl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单详情 待发货
 */
public class MyShipmentDetailActivity extends BaseActivity implements MyShipmentGoodAdapter.GoodClickListener {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<GoodsInfoDto> datas;
    private MyShipmentGoodAdapter goodAdapter;
    private MyViewModel viewModel;
    private TextView tvSend;
    private TextView tvStatus;
    private TextView tvTime;
    private TextView tvName;
    private TextView tvPhone;
    private TextView tvGoodAllAcount;
    private TextView tvFright;
    private TextView tvCardAcount;
    private TextView tvPoinsAcount;
    private TextView tvConponseAccount;
    private TextView tvInvoiceHead;
    private TextView tvInvoiceTye;
    private TextView tvInvoiceContent;
    private TextView tvOderNo;
    private TextView tvCommiTime;
    private TextView tvPayType;
    private TextView tvPayMoney;
    private TextView tvPayTime;
    private TextView tvCopy;
    private TextView tvAdrress;
    private TextView tvMySend;
    private LinearLayout llInvoice;
    private TextView tvShipperName;














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
        return R.layout.activity_myshipmentdetail;
    }

    @Override
    public void initView() {
        setNormalTitle("订单详情", v -> finish());
        tvSend = (TextView) findViewById(R.id.tv_send);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        datas = new ArrayList<>();
        recyclerView.setLayoutManager(linearLayoutManager);
        goodAdapter = new MyShipmentGoodAdapter(this,datas,this);
        recyclerView.setAdapter(goodAdapter);
        tvStatus = (TextView) findViewById(R.id.tv_status);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvPhone = (TextView) findViewById(R.id.tv_phone);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvGoodAllAcount = (TextView) findViewById(R.id.tv_good_all_acount);
        tvFright = (TextView) findViewById(R.id.tv_fright);
        tvCardAcount = (TextView) findViewById(R.id.tv_card_acount);
        tvPoinsAcount = (TextView) findViewById(R.id.tv_poins_acount);
        tvConponseAccount = (TextView) findViewById(R.id.tv_conponse_account);
        tvInvoiceHead = (TextView) findViewById(R.id.tv_invoice_head);
        tvInvoiceTye = (TextView) findViewById(R.id.tv_invoice_tye);
        tvInvoiceContent = (TextView) findViewById(R.id.tv_invoice_content);
        tvOderNo = (TextView) findViewById(R.id.tv_oder_no);
        tvCommiTime = (TextView) findViewById(R.id.tv_commi_time);
        tvPayType = (TextView) findViewById(R.id.tv_pay_type);
        tvPayMoney = (TextView) findViewById(R.id.tv_pay_money);
        tvPayTime = (TextView) findViewById(R.id.tv_pay_time);
        tvCopy = (TextView) findViewById(R.id.tv_copy);
        tvSend = (TextView) findViewById(R.id.tv_send);
        tvAdrress = (TextView) findViewById(R.id.tv_adrress);
        tvMySend = (TextView) findViewById(R.id.tv_my_send);
        llInvoice = (LinearLayout) findViewById(R.id.ll_invoice);
        tvShipperName = (TextView) findViewById(R.id.tv_ShipperName);
    }
    /**
     * 显示 各个界面的数据
     * @param data
     */
    private void initOrderDetailView(OrderDetailDto data) {
        //1.倒计时
        //tvTime.setText();
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
        //10.活动金额 ???
         //  tvActivityAccount.setText("¥"+NumberUtils.forMatNumber(data));
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
        tvOderNo.setText(data.getOrderNo());
        //15.提交时间
        tvCommiTime.setText(DateUtil.dateToString(DateUtil.DEFAULT_DATE_FORMATTER_MIN,new Date(data.getCreateDate())));
        //16.支付方式
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
        tvPayMoney.setText("¥"+NumberUtils.forMatNumber(data.getPayMoney()));
        //18.付款时间
        tvPayTime.setText(DateUtil.dateToString(DateUtil.DEFAULT_DATE_FORMATTER_MIN,new Date(data.getPayDate())));
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
                    tvTime.setText("剩余"+str.split(":")[0]+"时"+str.split(":")[1]+"分"+str.split(":")[2]+"秒");
                }
                else{
                    finish();
                }

            }
        });
    }
    @Override
    public void initData() {
        getDeliveryMethodList();
        if(!TextUtils.isEmpty(getIntent().getStringExtra("orderId"))){
            showLoadingDialog();
            getOrderDetail();
        }
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
    private OrderDetailDto mData;
    /**
     * 初始化订单详情数据
     * @param data
     */
    private void initDataView(OrderDetailDto data) {
        this.mData =data;
        if(EmptyUtils.isNotEmpty(data.getGoodsInfo())){
            datas.addAll(data.getGoodsInfo());
            goodAdapter.notifyDataSetChanged();
        }
        initOrderDetailView(data);
    }


    @Override
    public void initListener() {
        tvMySend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mData==null){
                    ToastUitl.showImageToastFail("未获取到订单信息");
                    return;
                }
                //自己送
                deliverGoods(getIntent().getStringExtra("orderId"),null,null);
            }
        });
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
    tvSend.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mData==null){
                ToastUitl.showImageToastFail("未获取到订单信息");
                return;
            }
            showShippmentDialog(mData);
        }
    });
    }
    private String conpaneyId;
    private void showShippmentDialog(OrderDetailDto mData) {
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
                        ){
                    ToastUitl.showImageToastFail("请填写数据完整");
                    return;
                }
                if(TextUtils.isEmpty(mData.getOrderNo())){
                    ToastUitl.showImageToastFail("未获取到订单号");
                    return;
                }
                dialog.dismiss();
                //发货
                deliverGoods(getIntent().getStringExtra("orderId"),ettCourierNumber.getText().toString(),conpaneyId);

            }
        });
    }
    /**
     * 发货
     *
     * @param orderId
     * @param courierNumber  快递订单ID
     * @param courierCompany 快递公司ID
     */
    private void deliverGoods(String orderId, String courierNumber, String courierCompany) {
        showLoadingDialog();
        viewModel.deliverGoods(orderId,courierNumber,courierCompany);
        viewModel.getDeliverGoodsliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                hideLoadingDialog();
                if(stringBaseDto.isSuccess()){
                    finish();
                    ToastUitl.showImageToastSuccess("发货成功");
                }
                else{
                    ToastUitl.showImageToastSuccess(stringBaseDto.getMsg());
                }

            }
        });
    }

    private List<DeliveryMethodDto> list;
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
    public void clickGoods(String goodId,GoodsInfoDto dto) {
        Intent intent  = new Intent(this,GoodsDetailActivity.class);
        if (dto.getScoreBuy() > 0) {
            intent.putExtra("shopType", 2);//积分商城
        }
        intent.putExtra("goodsId",goodId);
        startActivity(intent);
    }
}
