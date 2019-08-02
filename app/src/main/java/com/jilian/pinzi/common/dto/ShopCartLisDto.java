package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class ShopCartLisDto implements Serializable {
    private static final long serialVersionUID = 107457034883854725L;
    private String personbBuy;//": 20,
    private String file;//": "http://g.hiphotos.baidu.com/image/h%3D300/sign=6f4318466e2762d09f3ea2bf90ed0849/5243fbf2b211931376d158d568380cd790238dc1.jpg,http://e.hiphotos.baidu.com/image/h%3D300/sign=e81839633efa828bce239be3cd1f41cd/0eb30f2442a7d9339cb35915a04bd11373f001b8.jpg",
    private String goodsId;//": 1,
    private double topScore;//": 0,
    private String freight;//": 0,
    private String name;//": "牛栏山 二锅头",
    private String channelBuy;//": 10,
    private String terminalBuy;//": 15,
    private String id;//": 1,
    private String franchiseeBuy;//": 8
    private String quantity;//数量
    private boolean isChecked;
    private String storeId;//
    private double fastPrice;//true string   实际的价格（商品展示的价格）
    private double seckillPrice;//商品的 秒杀价格
    private Integer classes;// true string1.在首页加入 2.在采购中心加入
    private int isEarnest;//    true     string  是否需要定金（0.否 1.是）

    private double earnestRate;//     true     string    定金比例（单位%）
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

    public int getIsEarnest() {
        return isEarnest;
    }

    public void setIsEarnest(int isEarnest) {
        this.isEarnest = isEarnest;
    }

    public double getEarnestRate() {
        return earnestRate;
    }

    public void setEarnestRate(double earnestRate) {
        this.earnestRate = earnestRate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public double getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(double seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    private String storeName;//

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }



    public double getFastPrice() {
        return fastPrice;
    }

    public void setFastPrice(double fastPrice) {
        this.fastPrice = fastPrice;
    }

    public Integer getClasses() {
        return classes;
    }

    public void setClasses(Integer classes) {
        this.classes = classes;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPersonbBuy() {
        return personbBuy;
    }

    public void setPersonbBuy(String personbBuy) {
        this.personbBuy = personbBuy;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public double getTopScore() {
        return topScore;
    }

    public void setTopScore(double topScore) {
        this.topScore = topScore;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChannelBuy() {
        return channelBuy;
    }

    public void setChannelBuy(String channelBuy) {
        this.channelBuy = channelBuy;
    }

    public String getTerminalBuy() {
        return terminalBuy;
    }

    public void setTerminalBuy(String terminalBuy) {
        this.terminalBuy = terminalBuy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFranchiseeBuy() {
        return franchiseeBuy;
    }

    public void setFranchiseeBuy(String franchiseeBuy) {
        this.franchiseeBuy = franchiseeBuy;
    }
}

