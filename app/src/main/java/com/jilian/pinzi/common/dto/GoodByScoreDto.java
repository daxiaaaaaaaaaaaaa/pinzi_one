package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class GoodByScoreDto implements Serializable {
    private static final long serialVersionUID = -381795599002105821L;
    private String orderNo;//": "2018122516084914876",
    private String payMoney;//": 0.0,
    private String orderId;//": 325
    private long createDate;// 订单创建时间
    private double payFirstPrice;//定金

    public double getPayFirstPrice() {
        return payFirstPrice;
    }

    public void setPayFirstPrice(double payFirstPrice) {
        this.payFirstPrice = payFirstPrice;
    }
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
        this.orderId = orderId;
    }
}
