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
import com.jilian.pinzi.common.msg.FriendMsg;
import com.jilian.pinzi.common.msg.MessageEvent;
import com.jilian.pinzi.common.msg.RxBus;
import com.jilian.pinzi.common.vo.BuyCouponVo;
import com.jilian.pinzi.ui.main.BuyCardActivity;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
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

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.rong.eventbus.EventBus;

/**
 * 支付优惠券
 */
public class PayCardActivity extends BaseActivity {
    private RelativeLayout rlAlipay;
    private RelativeLayout rlWechat;

    private TextView tvPayCount;

    private MainViewModel viewModel;

    private BuyCouponVo data;

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
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_paycard;
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
        tvPayCount = (TextView) findViewById(R.id.tv_pay_count);
    }

    @Override
    public void initData() {
        data = (BuyCouponVo) getIntent().getSerializableExtra("data");
        tvPayCount.setText("支付金额：" + data.getMoney());


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
                && PinziApplication.getInstance().getWxPayType() == 2) {
            finish();
            PinziApplication.clearSpecifyActivities(new Class[]{BuyCardActivity.class});
        }
    }


    private int type;//支付方式 1.微信支付 2.支付宝支付 3.积分兑换 4.货到付款


    @Override
    public void initListener() {
        rlAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 2;
                //去支付
                pay();
            }
        });
        rlWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 1;
                pay();

            }
        });

    }


    /**
     * 去支付
     */
    private void pay() {
        showLoadingDialog();
        data.setType(String.valueOf(type));
        viewModel.buyCoupon(data);
        viewModel.getPayCardliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                hideLoadingDialog();
                if (stringBaseDto.isSuccess()) {
                    //微信支付
                    if (type == 1) {
                        if (tvPayCount.getText().toString().substring(5).equals("0.00")) {
                            finish();
                            PinziApplication.clearSpecifyActivities(new Class[]{BuyCardActivity.class});
                        } else {
                            wxPay(stringBaseDto.getData());
                        }

                    }
                    //支付宝支付
                    else if (type == 2) {
                        if (tvPayCount.getText().toString().substring(5).equals("0.00")) {
                            finish();
                            PinziApplication.clearSpecifyActivities(new Class[]{BuyCardActivity.class});
                        } else {
                            aliPay(stringBaseDto.getData());
                        }
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
        PinziApplication.getInstance().setWxPayType(2);
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
                    ToastUitl.showImageToastSuccess("支付成功");
                    finish();
                    PinziApplication.clearSpecifyActivities(new Class[]{BuyCardActivity.class});
                } else {
                    ToastUitl.showImageToastFail(resultSet);
                }

            }
        });
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
