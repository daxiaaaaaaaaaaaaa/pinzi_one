package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class PayOrderVo extends BaseVo {
    private String orderNo;//true string          订单号
    private int type;//true string  支付方式 1.微信支付 2.支付宝支付 3.积分兑换 4.货到付款
    private int payfright;//true string    为积分兑换的时候，运费支付方式：1.微信支付 2.支付宝支付
    private String platform;//平台 1.APP支付 2.PC支付

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPayfright() {
        return payfright;
    }

    public void setPayfright(int payfright) {
        this.payfright = payfright;
    }
}
