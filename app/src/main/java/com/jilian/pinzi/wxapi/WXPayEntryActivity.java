package com.jilian.pinzi.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.jilian.pinzi.common.msg.FriendMsg;
import com.jilian.pinzi.common.msg.RxBus;
import com.jilian.pinzi.utils.ToastUitl;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;



public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, "wxc0869ae09394b840");
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

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                Toast.makeText(this, "支付成功", Toast.LENGTH_LONG).show();
                FriendMsg eventMsg = new FriendMsg();
                eventMsg.setCode(500);
                RxBus.getInstance().post(eventMsg);
                finish();

            } else {
                Log.e("java", "onResp: " + resp.errCode);
                ToastUitl.showImageToastFail("支付失败");
                finish();
            }

        }
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }
}