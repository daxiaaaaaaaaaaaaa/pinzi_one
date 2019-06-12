package com.jilian.pinzi.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

public class AlipayUtil {


    private static final int SDK_PAY_FLAG = 1;

    //静态内部类单例模式
    public static AlipayUtil getInstance(){
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder{
        private static final AlipayUtil INSTANCE = new AlipayUtil();
    }

    private AlipayUtil(){

    }
    //orderInfo是服务端经过签名后的字符串，isShowPayLoading是否弹出支付宝支付dialog
    public void pay(final Activity activity , final String orderInfo, final boolean isShowPayLoading, final AlipayCallBack callBack){

        @SuppressLint("HandlerLeak")
        final Handler mHandler = new Handler() {
            @SuppressWarnings("unused")
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SDK_PAY_FLAG: {
                        @SuppressWarnings("unchecked")
                        String payResult =  (String) msg.obj;
                        callBack.callBack(payResult);
                    }
                }
            }
        };

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                String result = alipay.pay(orderInfo,isShowPayLoading);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    public interface AlipayCallBack{
        void callBack(String resultSet);
    }


    }