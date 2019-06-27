package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class StoreCouponDto implements Serializable {

   private String id;//  true  number       优惠券Id
   private String name;// true     string 优惠券名称
   private int type;//    true     number     优惠券类型（1.折扣劵 2.代金券）
   private int amount;//     true     number 总发行量
   private String moneyOrDiscount;//   true     number   面额或者折扣
   private String fullReduct;//  true     number;//    满多少元减。。。（0默认无限制）
   private int validity;//   true     number     有效期（1.日期范围 2.固定天数）
   private String validityDate;//      true   string             有效日期
   private int fixDay;//  number         领券后固定天数后过期
   private int useType ;//     true     number     使用商品类型（1.全场通用 2.指定商品 3.指定分类）
   private String goodsOrType ;// 指定商品或指定分类
   private long createDate ;//     true  number   创建时间
   private double price;// true   number     优惠券价格（0为该优惠券免费领取）
   private int isGet;//   true     number 是否已领取（0.否 >1是）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMoneyOrDiscount() {
        return moneyOrDiscount;
    }

    public void setMoneyOrDiscount(String moneyOrDiscount) {
        this.moneyOrDiscount = moneyOrDiscount;
    }

    public String getFullReduct() {
        return fullReduct;
    }

    public void setFullReduct(String fullReduct) {
        this.fullReduct = fullReduct;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public String getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(String validityDate) {
        this.validityDate = validityDate;
    }

    public int getFixDay() {
        return fixDay;
    }

    public void setFixDay(int fixDay) {
        this.fixDay = fixDay;
    }

    public int getUseType() {
        return useType;
    }

    public void setUseType(int useType) {
        this.useType = useType;
    }

    public String getGoodsOrType() {
        return goodsOrType;
    }

    public void setGoodsOrType(String goodsOrType) {
        this.goodsOrType = goodsOrType;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIsGet() {
        return isGet;
    }

    public void setIsGet(int isGet) {
        this.isGet = isGet;
    }
}
