package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class GoodsInfoDto implements Serializable {
    private static final long serialVersionUID = -271702874431337801L;
    private String file;//": 商品图片"http://g.hiphotos.baidu.com/image/h%3D300/sign=6f4318466e2762d09f3ea2bf90ed0849/5243fbf2b211931376d158d568380cd790238dc1.jpg,http://e.hiphotos.baidu.com/image/h%3D300/sign=e81839633efa828bce239be3cd1f41cd/0eb30f2442a7d9339cb35915a04bd11373f001b8.jpg",
    private String quantity;//": 数量1,
    private String totalPrice;//": 总价20,
    private String goodsId;//": 商品Id1,
    private double goodsPrice;//": 商品单价20,
    private String name;//": 商品名称"牛栏山 二锅头"
    private double scoreBuy;//truenumber积分购买 如果  scoreBuy 大于 0  就是积分商城买的 商品
    private double earnest;//

    private int isShow;// (是否上下架（0.上架  1.下架）)
    private int isSeckill;//(是否为秒杀商品（0.否  1.是）)
    private int isClose;//(0.秒杀已结束或暂未秒杀  1.秒杀中)

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public double getEarnest() {
        return earnest;
    }

    public void setEarnest(double earnest) {
        this.earnest = earnest;
    }

    public double getScoreBuy() {
        return scoreBuy;
    }

    public void setScoreBuy(double scoreBuy) {
        this.scoreBuy = scoreBuy;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
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
}
