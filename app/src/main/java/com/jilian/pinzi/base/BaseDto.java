package com.jilian.pinzi.base;


import com.jilian.pinzi.utils.EmptyUtils;

import java.io.Serializable;

/**
 * 服务器返回公共实体
 *
 * @param
 * @author weishixiong
 * @Time 2018-03-19
 */
public class BaseDto<T> implements Serializable{
    private int code;
    private String msg;
    private int totalPage;//": null,
    private int totalRecord;//": null, 总量
    private int pageNo;//": null
    private T data;
    public boolean isLogOut(){
        return code==403;
    }
    public boolean isDiable(){
        return code==401;
    }
    public boolean isSuccess() {
        return code == 200;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public T getData() {
        //过滤掉 []的数组
        return EmptyUtils.isNotEmpty(data)?data:null;
    }

    public void setData(T data) {
        this.data = data;
    }
}
