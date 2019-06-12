package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class ApplyRefundVo extends BaseVo {

    private static final long serialVersionUID = -409929035060528545L;
    private String orderId;//true number     订单Id
    private String goodsId;//true number   退货商品Id
    private int quantity;//true number数量
    private String reasonId;//true number   退货原因Id
    private String describtion;//true string    问题描述
    private String imgUrl;//true string    上传凭证
    private String linkPeople;//true string 联系人
    private String linkPhone;//true string  联系电话

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getReasonId() {
        return reasonId;
    }

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLinkPeople() {
        return linkPeople;
    }

    public void setLinkPeople(String linkPeople) {
        this.linkPeople = linkPeople;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }
}
