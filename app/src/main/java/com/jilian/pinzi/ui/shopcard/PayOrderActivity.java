package com.jilian.pinzi.ui.shopcard;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.AddOrderDto;
import com.jilian.pinzi.common.dto.GoodByScoreDto;
import com.jilian.pinzi.common.dto.MyOrderDto;
import com.jilian.pinzi.common.dto.OrderDetailDto;
import com.jilian.pinzi.common.msg.MessageEvent;
import com.jilian.pinzi.ui.my.MyOrderWaitePayDetailActivity;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.AlipayUtil;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.MainRxTimerUtil;
import com.jilian.pinzi.utils.NumberUtils;
import com.jilian.pinzi.utils.PinziDialogUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.utils.WXPayUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;

import io.rong.eventbus.EventBus;

public class PayOrderActivity extends BaseActivity {
    private RelativeLayout rlAlipay;
    private RelativeLayout rlWechat;
    private RelativeLayout rlGoods;
    private AddOrderDto addOrderDto;
    private MyOrderDto orderDto;
    private TextView tvTime;
    private long fifityMin = 15 * 60 * 1000;//15分钟
    private TextView tvPayCount;
    private int shopType;
    private String orderNo;
    private String orderId;//订单ID
    private MyViewModel viewModel;
    private OrderDetailDto orderDetailDto;
    private GoodByScoreDto goodByScoreDto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PinziApplication.addActivity(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PinziApplication.removeActivity(this);
        MainRxTimerUtil.cancel();
        EventBus.getDefault().unregister(this);

    }

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_payorder;
    }

    @Override
    public void initView() {
        setNormalTitle("支付订单", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.v_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBackDialog();
            }
        });
        rlAlipay = (RelativeLayout) findViewById(R.id.rl_alipay);
        rlWechat = (RelativeLayout) findViewById(R.id.rl_wechat);
        rlGoods = (RelativeLayout) findViewById(R.id.rl_goods);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvPayCount = (TextView) findViewById(R.id.tv_pay_count);
    }

    @Override
    public void initData() {
        //1 普通商城 2 积分兑换
        shopType = getIntent().getIntExtra("shopType", 1);
        addOrderDto = (AddOrderDto) getIntent().getSerializableExtra("addOrderDto");
        orderDto = (MyOrderDto) getIntent().getSerializableExtra("orderDto");
        orderDetailDto = (OrderDetailDto) getIntent().getSerializableExtra("orderDetailDto");
        goodByScoreDto = (GoodByScoreDto) getIntent().getSerializableExtra("goodByScoreDto");

        if (EmptyUtils.isNotEmpty(goodByScoreDto)) {
            orderId = goodByScoreDto.getOrderId();
            orderNo = goodByScoreDto.getOrderNo();
            long creatTime = goodByScoreDto.getCreateDate();
            startTimeTask(creatTime + fifityMin);
            tvPayCount.setText("支付金额：" + NumberUtils.forMatNumber(Double.parseDouble(goodByScoreDto.getPayMoney())));
        }

        if (EmptyUtils.isNotEmpty(addOrderDto)) {
            orderId = addOrderDto.getOrderId();
            orderNo = addOrderDto.getOrderNo();
            long creatTime = addOrderDto.getCreateDate();
            startTimeTask(creatTime + fifityMin);

            if (addOrderDto.getPayFirstPrice() == 0) {
                tvPayCount.setText("支付金额：" + NumberUtils.forMatNumber(Double.parseDouble(addOrderDto.getPayMoney())));
            } else {
                tvPayCount.setText("支付金额：" + NumberUtils.forMatNumber(addOrderDto.getPayFirstPrice()));
            }


        }
        if (EmptyUtils.isNotEmpty(orderDto)) {
            orderNo = orderDto.getOrderNo();
            orderId = orderDto.getId();
            long creatTime = orderDto.getCreateDate();


            if (orderDto.getPayFirstMoney() == 0) {
                startTimeTask(creatTime + fifityMin);
                tvPayCount.setText("支付金额：" + NumberUtils.forMatNumber(Double.parseDouble(orderDto.getPayMoney())));
            } else {
                if (orderDto.getPayStatus() == 7) {
                    tvPayCount.setText("支付金额：" + NumberUtils.forMatNumber(Double.parseDouble(orderDto.getPayMoney())));
                } else {
                    startTimeTask(creatTime + fifityMin);
                    tvPayCount.setText("支付金额：" + NumberUtils.forMatNumber(orderDto.getPayFirstMoney()));
                }

            }


            if (orderDto.getPayWay() == 3) {
                shopType = 2;
            }
        }
        if (EmptyUtils.isNotEmpty(orderDetailDto)) {
            orderNo = orderDetailDto.getOrderNo();
            orderId = orderDetailDto.getOrderId();
            //积分兑换
            if (orderDetailDto.getPayWay() == 3) {
                shopType = 2;
            }

            long creatTime = orderDetailDto.getCreateDate();


            if (orderDetailDto.getPayFirstMoney() == 0) {
                startTimeTask(creatTime + fifityMin);
                tvPayCount.setText("支付金额：" + NumberUtils.forMatNumber(orderDetailDto.getPayMoney()));
            } else {
                if (orderDetailDto.getPayStatus() == 7) {
                    tvPayCount.setText("支付金额：" + NumberUtils.forMatNumber(orderDetailDto.getPayMoney()));
                } else {
                    startTimeTask(creatTime + fifityMin);
                    tvPayCount.setText("支付金额：" + NumberUtils.forMatNumber(orderDetailDto.getPayFirstMoney()));
                }

            }


        }

        if (shopType == 1) {
            rlGoods.setVisibility(View.VISIBLE);
        }
        if (shopType == 2) {
            rlGoods.setVisibility(View.GONE);
        }
    }

    /**
     * 开启倒计时
     *
     * @param endTime
     */
    private void startTimeTask(long endTime) {
        MainRxTimerUtil.interval(1000, new MainRxTimerUtil.IRxNext() {
            @Override
            public void doNext() {//获取现在的 时分秒 时间戳
                long nowTime = new Date().getTime();
                //单位 s
                long delTime = endTime - nowTime;
                if (delTime > 0) {
                    String str = DateUtil.timeToHms(delTime);
                    tvTime.setText("请在" + str.split(":")[0] + ":" + str.split(":")[1] + ":" + str.split(":")[2] + "内完成支付");
                } else {
                    finish();
                }

            }
        });
    }

    /**
     * //监听外来是否要去成功的界面
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        /* Do something */
        if (EmptyUtils.isNotEmpty(event)
                && EmptyUtils.isNotEmpty(event.getWxPayMessage())
                && event.getWxPayMessage().getPayCode() == 200
                && PinziApplication.getInstance().getWxPayType() == 1) {
            toPaySuccess();
        }
    }

    private int type;//支付方式 1.微信支付 2.支付宝支付 3.积分兑换 4.货到付款
    private int payfright;

    @Override
    public void initListener() {
        rlAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shopType == 1) {
                    type = 2;
                }
                if (shopType == 2) {
                    type = 4;
                    payfright = 2;
                }
                //去支付
                pay(orderNo, type, payfright);
            }
        });
        rlWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shopType == 1) {
                    type = 1;
                }
                if (shopType == 2) {
                    type = 4;
                    payfright = 1;
                }
                //去支付
                pay(orderNo, type, payfright);

            }
        });
        rlGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shopType == 1) {
                    type = 4;
                }
                //去支付
                pay(orderNo, type, payfright);
            }
        });
    }

    private Intent intent;

    /**
     * 去支付
     */
    private void pay(String orderNo, int type, int payfright) {
        showLoadingDialog();
        viewModel.payOrder(orderNo, type, payfright, "1");
        viewModel.getPay().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                hideLoadingDialog();
                if (stringBaseDto.isSuccess()) {
                    intent = new Intent(PayOrderActivity.this, PaySuccessActivity.class);
                    if (shopType == 1) {
                        //支付方式 1.微信支付 2.支付宝支付 3.积分兑换 4.货到付款
                        switch (type) {
                            case 1:
                                intent.putExtra("payType", "微信支付");
                                break;
                            case 2:
                                intent.putExtra("payType", "支付宝支付");
                                break;
                            case 4:
                                intent.putExtra("payType", "货到付款");
                                break;
                        }

                    }
                    if (shopType == 2) {
                        switch (payfright) {
                            case 1:
                                intent.putExtra("payType", "微信支付");
                                break;
                            case 2:
                                intent.putExtra("payType", "支付宝支付");
                                break;
                        }
                    }
                    //微信支付
                    if (type == 1) {
                        if (tvPayCount.getText().toString().substring(5).equals("0.00")) {
                            toPaySuccess();
                        } else {
                            wxPay(stringBaseDto.getData());
                        }

                    }
                    //支付宝支付
                    else if (type == 2) {
                        if (tvPayCount.getText().toString().substring(5).equals("0.00")) {
                            toPaySuccess();
                        } else {
                            aliPay(stringBaseDto.getData());
                        }

                    }
                    //钱包支付
                    else {
                        toPaySuccess();
                    }

                } else {
                    ToastUitl.showImageToastSuccess(stringBaseDto.getMsg());
                }
            }
        });
    }

    /**
     * 微信支付
     *
     * @param info
     */
    private void wxPay(String info) {
        PinziApplication.getInstance().setWxPayType(1);
        JSONObject jsonObject = JSONObject.parseObject(info);
        String packages = jsonObject.getString("package");
        String appid = jsonObject.getString("appid");
        String sign = jsonObject.getString("sign");
        String partnerid = jsonObject.getString("partnerid");
        String prepayid = jsonObject.getString("prepayid");
        String noncestr = jsonObject.getString("noncestr");
        String timestamp = jsonObject.getString("timestamp");
        //假装请求了服务器 获取到了所有的数据
        //假装请求了服务器 获取到了所有的数据,注意参数不能少
        WXPayUtils.WXPayBuilder builder = new WXPayUtils.WXPayBuilder();
        builder.setAppId(appid)
                .setPartnerId(partnerid)
                .setPrepayId(prepayid)
                .setPackageValue(packages)
                .setNonceStr(noncestr)
                .setTimeStamp(timestamp)
                .setSign(sign)
                .build().toWXPayNotSign(this);


    }

    /**
     * 支付宝支付
     *
     * @param info 订单信息
     */
    private void aliPay(String info) {
        AlipayUtil.getInstance().pay(this, info, true, new AlipayUtil.AlipayCallBack() {
            @Override
            public void callBack(String resultSet) {
                if (!TextUtils.isEmpty(resultSet) && resultSet.contains("9000")) {
                    toPaySuccess();
                    finish();
                } else {
                    ToastUitl.showImageToastFail(resultSet);
                }

            }
        });
    }

    /**
     * 跳转到支付成功的界面
     */
    private void toPaySuccess() {
        intent.putExtra("payCount", "¥ " + tvPayCount.getText().toString().substring(5));
        intent.putExtra("orderNo", orderNo);
        intent.putExtra("orderId", orderId);
        startActivity(intent);
        finish();
        PinziApplication.clearSpecifyActivities(new Class[]{MyOrderWaitePayDetailActivity.class});
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showBackDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 返回
     */
    public void showBackDialog() {
        Dialog dialog = PinziDialogUtils.getDialogNotTouchOutside(this, R.layout.dialog_delete_order_tips);
        //TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) dialog.findViewById(R.id.tv_content);
        tvContent.setText("确定要离开订单支付吗？");
        TextView tvNo = (TextView) dialog.findViewById(R.id.tv_no);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tv_ok);

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
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
}
