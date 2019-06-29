package com.jilian.pinzi.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.common.msg.FriendMsg;
import com.jilian.pinzi.common.msg.MessageEvent;
import com.jilian.pinzi.common.msg.RxBus;
import com.jilian.pinzi.common.msg.WxPayMessage;
import com.jilian.pinzi.utils.ToastUitl;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import io.rong.eventbus.EventBus;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


    private IWXAPI api;
    private static final String TAG ="WXEntryActivity" ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID);
        api.handleIntent(getIntent(), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }


    @Override
    public void onResp(BaseResp resp) {
        finish();
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                Log.e(TAG, "accept:++++++++++" );
                ToastUitl.showImageToastSuccess("支付成功");

                MessageEvent messageEvent = new MessageEvent();
                WxPayMessage payMessage = new WxPayMessage();
                payMessage.setPayCode(200);
                messageEvent.setWxPayMessage(payMessage);
                EventBus.getDefault().post(messageEvent);


            } else {
                Log.e("java", "onResp: " + resp.errCode);
                ToastUitl.showImageToastSuccess("支付失败");
            }

        }
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }
}