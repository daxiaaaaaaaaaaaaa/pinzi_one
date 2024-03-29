package com.jilian.pinzi.base;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.exception.ApiException;
import com.jilian.pinzi.exception.ExceptionEngine;
import com.jilian.pinzi.exception.ServerException;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;


/**
 * 自定义请服务器被观察者
 *
 * @author weishixiong
 * @Time 2018-04-12
 */
public class CommonHttpSubscriber<T> implements Subscriber<BaseDto<T>> {

    private static final String TAG = "CommonHttpSubscriber";
    //异常类
    private ApiException ex;

    public CommonHttpSubscriber() {
        data = new MutableLiveData();

    }

    private MutableLiveData<BaseDto<T>> data;

    public MutableLiveData<BaseDto<T>> get() {
        return data;
    }

    public void set(BaseDto<T> t) {
        this.data.setValue(t);
    }

    public void onFinish(BaseDto<T> t) {
        set(t);
    }

    @Override
    public void onSubscribe(Subscription s) {
        // 观察者接收事件 = 1个
        s.request(1);
    }

    @Override
    public void onNext(BaseDto<T> t) {
        if (t.getCode() == Constant.Server.SUCCESS_CODE
                || t.getCode() == Constant.Server.NOPERFORM_CODE
                || t.getCode() == Constant.Server.CHECKFAILUER_CODE
                || t.getCode() == Constant.Server.CHECKING_CODE) {
            onFinish(t);
            // PinziApplication.getInstance().logout("登录失效测试");
        }
        //登录失效
        else if (t.isLogOut()) {
            //403
            PinziApplication.getInstance().logout("您的账号登录已失效，请重新登录");
        }
        //  该账号被删除或被禁用
        else if (t.isDiable()) {
            //401
            PinziApplication.getInstance().logout("您的账号被删除或被禁用");
        }
        //抛异常了
        else {
            ex = ExceptionEngine.handleException(new ServerException(t.getCode(), t.getMsg()));
            getErrorDto(ex);
        }
    }

    @Override
    public void onError(Throwable t) {
        if(t.getLocalizedMessage().contains("HTTP 403 Forbidden")){
            PinziApplication.getInstance().logout("您的账号登录已失效，请重新登录");
        }
        else{
            Log.e(TAG, "onError{}" + t);
            ex = ExceptionEngine.handleException(t);
            getErrorDto(ex);
        }

    }

    /**
     * 初始化错误的dto
     *
     * @param ex
     */
    private void getErrorDto(ApiException ex) {
        BaseDto dto = new BaseDto();
        dto.setCode(ex.getCode());
        dto.setMsg(ex.getMsg());
        onFinish((BaseDto<T>) dto);
    }

    @Override
    public void onComplete() {
    }
}
