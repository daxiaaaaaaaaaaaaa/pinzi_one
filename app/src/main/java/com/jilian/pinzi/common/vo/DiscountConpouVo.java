package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class DiscountConpouVo extends BaseVo {
    private static final long serialVersionUID = 2156964388849319873L;
    private String uId;//true string用户Id
    private String goodsId;//true string  商品Id
    private String quantity;//true string   对应的数量
    private String classes;

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
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


}
