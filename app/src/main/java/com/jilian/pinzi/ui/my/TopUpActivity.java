package com.jilian.pinzi.ui.my;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONObject;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.msg.FriendMsg;
import com.jilian.pinzi.common.msg.MessageEvent;
import com.jilian.pinzi.common.msg.RxBus;
import com.jilian.pinzi.ui.main.BuyCardActivity;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.utils.AlipayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.utils.WXPayUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.rong.eventbus.EventBus;

/**
 * 充值
 */
public class TopUpActivity extends BaseActivity {
    private RelativeLayout rlAlipay;
    private RelativeLayout rlWechat;
    private EditText etMoney;
    private MainViewModel viewModel;


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
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_topup;
    }

    @Override
    public void initView() {
        setNormalTitle("支付订单", v -> finish());
        rlAlipay = (RelativeLayout) findViewById(R.id.rl_alipay);
        rlWechat = (RelativeLayout) findViewById(R.id.rl_wechat);
        etMoney = (EditText) findViewById(R.id.et_money);
    }

    @Override
    public void initData() {

    }
    private String type;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        /* Do something */
        if(EmptyUtils.isNotEmpty(event)
                &&EmptyUtils.isNotEmpty(event.getWxPayMessage())
                &&event.getWxPayMessage().getPayCode()==200
                && PinziApplication.getInstance().getWxPayType() == 3){
            Log.e(TAG, "accept: ........" );
            PinziApplication.clearSpecifyActivities(new Class[]{TopUpSuccessActivity.class});
            Intent intent = new Intent(TopUpActivity.this, TopUpSuccessActivity.class);
            intent.putExtra("type",type);
            intent.putExtra("money",etMoney.getText().toString());
            startActivity(intent);
            finish();
        }
    }


    @Override
    public void initListener() {

        rlAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type ="2";
                topUp();

            }
        });
        rlWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type ="1";
                topUp();

            }
        });
    }

    /**
     * 充值
     */
    private void topUp() {
        if (TextUtils.isEmpty(etMoney.getText().toString())) {
            ToastUitl.showImageToastFail("请输入充值金额");
            return;
        }
        try {
            Double money = Double.parseDouble(etMoney.getText().toString());
            if (money== 0) {
                ToastUitl.showImageToastFail("金额必须大于0");
                return;
            }
        }
        catch (Exception e){
            ToastUitl.showImageToastFail("请输入合法的金额");
        }

        viewModel.rechargeCommsion(getUserId(), etMoney.getText().toString(), type, "1");
        viewModel.getRechargeCommsionData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                hideLoadingDialog();
                if (stringBaseDto.isSuccess()) {
                    //微信支付
                    if (type.equals("1")) {
                        wxPay(stringBaseDto.getData());

                    }
                    //支付宝支付
                    else if (type.equals("2")) {

                        aliPay(stringBaseDto.getData());
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
        PinziApplication.getInstance().setWxPayType(3);
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
                    Intent intent = new Intent(TopUpActivity.this, TopUpSuccessActivity.class);
                    intent.putExtra("type",type);
                    intent.putExtra("money",etMoney.getText().toString());
                    startActivity(intent);
                    finish();
                } else {
                    ToastUitl.showImageToastFail(resultSet);
                }

            }
        });
    }
}
