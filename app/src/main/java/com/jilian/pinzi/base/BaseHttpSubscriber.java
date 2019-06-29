package com.jilian.pinzi.base;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.jilian.pinzi.exception.ApiException;
import com.jilian.pinzi.exception.ExceptionEngine;
import com.jilian.pinzi.utils.ToastUitl;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;


/**
 * 自定义请服务器被观察者
 *
 * @author weishixiong
 * @Time 2018-04-12
 */
public class BaseHttpSubscriber<T> implements Subscriber<T> {

    private static final String TAG = "CommonHttpSubscriber";
    //异常类
    private ApiException ex;

    public BaseHttpSubscriber() {
        data = new MutableLiveData();

    }

    private MutableLiveData<T> data;

    public MutableLiveData<T> get() {
        return data;
    }

    public void set(T t) {
        this.data.setValue(t);
    }

    public void onFinish(T t) {
        set(t);
    }

    @Override
    public void onSubscribe(Subscription s) {
        // 观察者接收事件 = 1个
        s.request(1);
    }

    @Override
    public void onNext(T t) {

        onFinish(t);

    }

    @Override
    public void onError(Throwable t) {
        Log.e(TAG, "onError{}" + t);
        ex = ExceptionEngine.handleException(t);
        getErrorDto(ex);
    }

    /**
     * 初始化错误的dto
     *
     * @param ex
     */
    /**
     * 初始化错误的dto
     *
     * @param ex
     */
    private void getErrorDto(ApiException ex) {
        ToastUitl.showImageToastFail(ex.getMsg());
    }

    @Override
    public void onComplete() {
    }
}
