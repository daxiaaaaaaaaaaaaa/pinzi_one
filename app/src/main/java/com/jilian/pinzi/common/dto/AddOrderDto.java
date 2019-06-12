package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class AddOrderDto implements Serializable {
    private static final long serialVersionUID = -5729391505058309353L;
    private String orderNo;//";//: "2018111218534886769",
    private String payMoney;//": 315,
    private String orderId;//": 6
    private long createDate;// 订单创建时间

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayMoney() {
        return payMoney;
    }


    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId ;
    }
}
