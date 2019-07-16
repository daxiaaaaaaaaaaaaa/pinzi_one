package com.jilian.pinzi.callback;

/**
 * 创建时间：2018/3/7
 * 编写人：weishixiong
 * 功能描述 ：
 */

public interface DownloadCallBack {

    void onProgress(int progress);

    void onCompleted();

    void onError(String msg);

}
