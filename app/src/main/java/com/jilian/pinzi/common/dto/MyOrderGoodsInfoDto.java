package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class MyOrderGoodsInfoDto implements Serializable {
    private static final long serialVersionUID = 2787546064883328109L;

    private String file;//true string    商品文件
    private int  quantity;// true number  数量
    private double totalPrice;// true number      总价
    private int  goodsId;// true number  商品Id
    private double goodsPrice;// true number 单价
    private String name;// true string   商品名称
    private int  isApply;// true string0.未申请退货 1.已申请退货
    private String orderId;//订单ID
    private double scoreBuy;//truenumber积分购买 如果  scoreBuy 大于 0  就是积分商城买的 商品

    private int isShow;// (是否上下架（0.上架  1.下架）)
    private int isSeckill;//(是否为秒杀商品（0.否  1.是）)
    private int isClose;//(0.秒杀已结束或暂未秒杀  1.秒杀中)

    public double getScoreBuy() {
        return scoreBuy;
    }

    public void setScoreBuy(double scoreBuy) {
        this.scoreBuy = scoreBuy;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public int getIsSeckill() {
        return isSeckill;
    }

    public void setIsSeckill(int isSeckill) {
        this.isSeckill = isSeckill;
    }

    public int getIsClose() {
        return isClose;
    }

    public void setIsClose(int isClose) {
        this.isClose = isClose;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsApply() {
        return isApply;
    }

    public void setIsApply(int isApply) {
        this.isApply = isApply;
    }
}
