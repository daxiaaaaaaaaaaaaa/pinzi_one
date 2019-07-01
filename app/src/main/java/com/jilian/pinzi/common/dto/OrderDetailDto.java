package com.jilian.pinzi.common.dto;

import java.io.Serializable;
import java.util.List;

public class OrderDetailDto implements Serializable {
    private static final long serialVersionUID = -5691815119185309926L;
    private String address;// true string  收货地址
    private String orderNo;// true string    订单编号
    private double couponRemission;// true number   优惠券抵扣
    private int  payWay;// true number1.微信 2.支付宝 3.积分 4.货到付款
    private int type;// true number    类型（1.增值税专用发票 2.增值税普通发票）
    private double goodsTotalPrice;// true number    商品总价
    private double payScore;// true number   支付积分
    private double commissionDeduction;// true number    佣金抵扣
    private double payMoney;// true number  实际支付金额
    private String phone;// true string    收货电话
    private String name;// true string    收货人
    private double scoreDeduction;// true number    积分抵扣
    private double freightPrice;// true number   运费
    private String invoiceTitle;// true string    发票抬头
    private List<GoodsInfoDto> goodsInfo;// true array[object]
    private long createDate;// true number提交时间
    private long payDate;// true number   支付时间
    private int payStatus;// true number   支付状态（1.待支付 2.已支付，待发货 3.已发货 4.已完成，待评价 5.已评价 6.已取消）
    private String shipperName;// true string    发货人
    private int orderType;// true number 1.商品 2.奖品
    private String awardName;//true string    奖品名称
    private String orderId;//订单id
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAddress() {
        return address;
    }



    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public double getCouponRemission() {
        return couponRemission;
    }

    public void setCouponRemission(double couponRemission) {
        this.couponRemission = couponRemission;
    }

    public int getPayWay() {
        return payWay;
    }

    public void setPayWay(int payWay) {
        this.payWay = payWay;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getGoodsTotalPrice() {
        return goodsTotalPrice;
    }

    public void setGoodsTotalPrice(double goodsTotalPrice) {
        this.goodsTotalPrice = goodsTotalPrice;
    }

    public double getPayScore() {
        return payScore;
    }

    public void setPayScore(double payScore) {
        this.payScore = payScore;
    }

    public double getCommissionDeduction() {
        return commissionDeduction;
    }

    public void setCommissionDeduction(double commissionDeduction) {
        this.commissionDeduction = commissionDeduction;
    }

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScoreDeduction() {
        return scoreDeduction;
    }

    public void setScoreDeduction(double scoreDeduction) {
        this.scoreDeduction = scoreDeduction;
    }

    public double getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(double freightPrice) {
        this.freightPrice = freightPrice;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public List<GoodsInfoDto> getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(List<GoodsInfoDto> goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getPayDate() {
        return payDate;
    }

    public void setPayDate(long payDate) {
        this.payDate = payDate;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
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
}
