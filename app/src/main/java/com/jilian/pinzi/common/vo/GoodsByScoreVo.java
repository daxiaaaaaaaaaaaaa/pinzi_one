package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class GoodsByScoreVo extends BaseVo {
    private String goodsIds;//true string 商品Id(多个用逗号隔开)
    private String quantity;//true string   数量（与商品Id一对一）
    private String score;//true string  兑换积分
    private String addressId;//true number   收货地址
    private String shipper;//true number发货人（0.平台）
    private String uId;//true number 用户id
    private String identity;//true number  登录用户身份（1.普通 2.终端 3.渠道 4.总经销商）
    private String freightPrice;//true string       运费
    private String isUseCommisson;//true string  使用的佣金(未使用传0，使用传1)

    public String getGoodsIds() {
        return goodsIds;
    }

    public void setGoodsIds(String goodsIds) {
        this.goodsIds = goodsIds;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
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

    public String getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(String freightPrice) {
        this.freightPrice = freightPrice;
    }

    public String getIsUseCommisson() {
        return isUseCommisson;
    }

    public void setIsUseCommisson(String isUseCommisson) {
        this.isUseCommisson = isUseCommisson;
    }
}
