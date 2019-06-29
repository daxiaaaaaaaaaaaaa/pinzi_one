package com.jilian.pinzi.wxapi;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.AccessTokenDto;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.utils.ToastUitl;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;


public class WXEntryActivity extends FragmentActivity implements IWXAPIEventHandler {


    private static final String TAG = "WXEntryActivity";

    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;
    private MainViewModel viewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PinziApplication.getInstance().getApi().handleIntent(getIntent(), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        PinziApplication.getInstance().getApi().handleIntent(intent, this);
    }


    @Override
    public void onResp(BaseResp resp) {
        Log.d("WXEntryActivity", "错误码 : " + resp.errCode + "");
        //finish();
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (RETURN_MSG_TYPE_LOGIN == resp.getType()) {

                    ToastUitl.showImageToastFail("登录失败");
                    finish();
                }
                if (RETURN_MSG_TYPE_SHARE == resp.getType()) {
                    ToastUitl.showImageToastFail("分享失败");
                    finish();
                }
                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (resp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        //拿到了微信返回的code,立马再去请求access_token
                        String code = ((SendAuth.Resp) resp).code;
                        //这里判断类型是微信登录还是绑定微信账号
                        String state = ((SendAuth.Resp) resp).state;
                        //判断是绑定微信账号
                        if (state.equals("wechat_sdk_bind")) {
                            /*
                             * 绑定微信
                             * */
                            //ToastUitl.showImageToastFail("绑定微信：" + code);
                            getAccessToken(code);
                            finish();
                            //doBindWxHttp(code);

                        } else {
                            /*
                             * 微信登录 获取用户信息
                             **/
                            getAccessToken(code);
                            //ToastUitl.showImageToastSuccess("微信登录 获取用户信息："+code);
                            finish();
                        }
                        break;

                    case RETURN_MSG_TYPE_SHARE:
                        ToastUitl.showImageToastFail("微信分享成功");
                        finish();
                        break;
                }
                break;
        }
    }

    /**
     * 获取第一步的code后，请求以下链接获取access_token
     *
     * @param code
     */
    private void getAccessToken(String code) {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.access_token(Constant.APP_ID,"secret",code,"authorization_code");
        viewModel.getAccess_tokenData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String stringBaseDto) {
                if(TextUtils.isEmpty(stringBaseDto)){
                    AccessTokenDto  accessTokenDto = JSONObject.parseObject(stringBaseDto, AccessTokenDto.class);
                }else{
                    ToastUitl.showImageToastFail("获取access_token失败");
                }
            }
        });

    }


    @Override
    public void onReq(BaseReq baseReq) {
        Log.e(TAG, "onReq: " + baseReq);
    }
}