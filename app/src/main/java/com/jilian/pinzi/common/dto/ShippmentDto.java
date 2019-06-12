package com.jilian.pinzi.common.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 发货列表
 */
public class ShippmentDto implements Serializable {
    private static final long serialVersionUID = -8546845087469649755L;
    private double payScore;//": 200,支付积分
    private String orderNo;//": "2018110619473325000",订单号
    private double payMoney;//": 0,支付金额
    private String id;//": 3,
    private List<GoodsInfoDto> goodsInfo;

    public double getPayScore() {
        return payScore;
    }

    public void setPayScore(double payScore) {
        this.payScore = payScore;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<GoodsInfoDto> getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(List<GoodsInfoDto> goodsInfo) {
        this.goodsInfo = goodsInfo;
    }
}
