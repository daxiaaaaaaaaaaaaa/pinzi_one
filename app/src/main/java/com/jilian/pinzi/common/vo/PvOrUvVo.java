package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class PvOrUvVo extends BaseVo {

    private String mac;//   true    string 手机或电脑唯一标识
    private String goodsId ;// true string  商品Id

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
