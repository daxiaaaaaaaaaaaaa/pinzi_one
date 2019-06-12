package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class AddOrderVo extends BaseVo{
    private static final long serialVersionUID = 6786202037344578529L;
    private String identity;//true string   身份
   private String uId;//true string   用户Id
   private String addressId;//true string收货地址
   private String goodsId;//true string    商品Id(多个商品用逗号隔开)
   private String quantity;//true string    数量（一对一商品）
   private String couponId;//true string       优惠券Id
   private String conpouGoodsId;//true string  适用该优惠券的商品Id（默认传0）
   private String isUseScore;//true string    使用积分（未使用传0）
   private String isUseCommisson;//true string    使用佣金（未使用传0）
   private String shipper;//true string    发货人（平台为0）
   private String freightPrice;//true number      运费
   private String invoiceId;//true number            发票id
   private String conpouQuantity;//true string    适用该优惠券的商品数量(默认传0)
   private String type;//true string1.购物车购买 2.直接购买
   private String orderType;//true string1.商品 2.奖品
   private String name;//true string奖品名称（默认传""）
   private String classes;

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getConpouGoodsId() {
        return conpouGoodsId;
    }

    public void setConpouGoodsId(String conpouGoodsId) {
        this.conpouGoodsId = conpouGoodsId;
    }

    public String getIsUseScore() {
        return isUseScore;
    }

    public void setIsUseScore(String isUseScore) {
        this.isUseScore = isUseScore;
    }

    public String getIsUseCommisson() {
        return isUseCommisson;
    }

    public void setIsUseCommisson(String isUseCommisson) {
        this.isUseCommisson = isUseCommisson;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(String freightPrice) {
        this.freightPrice = freightPrice;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getConpouQuantity() {
        return conpouQuantity;
    }

    public void setConpouQuantity(String conpouQuantity) {
        this.conpouQuantity = conpouQuantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
