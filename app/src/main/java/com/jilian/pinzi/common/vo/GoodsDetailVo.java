package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

/**
 * 商品详情
 */
public class GoodsDetailVo extends BaseVo {
    private Integer type;//true number1.普通商品 2.秒杀商品
    private String goodsId;//true number      商品id
    private String uId;//true number   用户Id

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}
