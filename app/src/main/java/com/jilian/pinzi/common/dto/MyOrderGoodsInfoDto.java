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
