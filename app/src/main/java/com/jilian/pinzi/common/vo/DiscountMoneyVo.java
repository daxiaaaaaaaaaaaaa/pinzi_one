package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class DiscountMoneyVo extends BaseVo {
    private static final long serialVersionUID = -2397778700606546937L;
    private String quantity;//true string 商品数量（与商品Id一对一）(多个用逗号隔开)
    private String uId;//true number 用户Id
    private String identity;//true number    用户身份（1.普通用户 2.终端 3.渠道 4.总经销商）
    private String couponId;//true string    优惠券Id(未使用传0)
    private String classes;//true string1.在首页购买 2.在采购中心购买 （与商品一对一）
    private String conpouGoodsId;//true string    适用该优惠券的商品Id(多个用逗号隔开，在选择优惠劵接口有返回这个参数)
    private String isUseCommisson;//true string  使用佣金（0.未使用 1.使用）
    private String isUseScore;//true string   使用积分（0.未使用 1.使用）
    private String conpouQuantity;//true string    适用该优惠券的商品数量(与商品Id一对一，在选择优惠劵接口有返回这个参数)
    private String goodsId;//true string  商品Id(多个用逗号隔开）

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getConpouGoodsId() {
        return conpouGoodsId;
    }

    public void setConpouGoodsId(String conpouGoodsId) {
        this.conpouGoodsId = conpouGoodsId;
    }

    public String getIsUseCommisson() {
        return isUseCommisson;
    }

    public void setIsUseCommisson(String isUseCommisson) {
        this.isUseCommisson = isUseCommisson;
    }

    public String getIsUseScore() {
        return isUseScore;
    }

    public void setIsUseScore(String isUseScore) {
        this.isUseScore = isUseScore;
    }

    public String getConpouQuantity() {
        return conpouQuantity;
    }

    public void setConpouQuantity(String conpouQuantity) {
        this.conpouQuantity = conpouQuantity;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
