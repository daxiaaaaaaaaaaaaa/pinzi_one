package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

/**
 * 加入购物车
 */
public class JoinShopCartVo extends BaseVo {
    private static final long serialVersionUID = -2530035953187994004L;
    private String uId;//true number     用户Id

    private String goodsId;//true number   商品Id

    private String quantity;//true number   数量
    private Integer classes;

    public Integer getClasses() {
        return classes;
    }

    public void setClasses(Integer classes) {
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
