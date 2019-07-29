package com.jilian.pinzi.common.dto;

import java.io.Serializable;
import java.util.List;

public class MyOrderDto implements Serializable {
    private static final long serialVersionUID = -3522280489749583137L;
    private String orderNo;// true string订单号
    private String payMoney;// true number支付金额
    private String id;// true number订单ID
    private String payScore;// true number  支付积分
    private int isReturn;// true string0.可以退货 1.超过退货日期
    private Integer payStatus;// true number    支付状态（1.待支付 2.已支付，待发货 3.已发货 4.已完成，待评价 5.已评价 6.已取消 7 等待付尾款）
    private long createDate;// true string       订单时间
    private int orderType;// true number1.商品 2.奖品
    private String awardName;// true string奖品名称
    private int payWay;//支付方式 1.微信 2.支付宝 3.积分 4.货到付款L
    private double freight;//运费
    private double payFirstMoney;//首付款




    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public double getPayFirstMoney() {
        return payFirstMoney;
    }

    public void setPayFirstMoney(double payFirstMoney) {
        this.payFirstMoney = payFirstMoney;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    private List<MyOrderGoodsInfoDto> goodsInfo;//商品信息
    public int getPayWay() {
        return payWay;
    }
    public void setPayWay(int payWay) {
        this.payWay = payWay;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayScore() {
        return payScore;
    }

    public void setPayScore(String payScore) {
        this.payScore = payScore;
    }

    public int getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(int isReturn) {
        this.isReturn = isReturn;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public List<MyOrderGoodsInfoDto> getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(List<MyOrderGoodsInfoDto> goodsInfo) {
        this.goodsInfo = goodsInfo;
    }
}
