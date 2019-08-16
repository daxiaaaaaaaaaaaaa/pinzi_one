package com.jilian.pinzi.common.msg;

import com.jilian.pinzi.common.dto.GoodsDetailDto;

import java.io.Serializable;

public class MessageEvent implements Serializable {
    private WxPayMessage wxPayMessage;
    private ProductMessage productMessage;
    private GoodsDetailDto goodsDetailDto;
    private KillMessage killMessage;
    private MainCreatMessage mainCreatMessage;

    public MainCreatMessage getMainCreatMessage() {
        return mainCreatMessage;
    }

    public void setMainCreatMessage(MainCreatMessage mainCreatMessage) {
        this.mainCreatMessage = mainCreatMessage;
    }

    public KillMessage getKillMessage() {
        return killMessage;
    }

    public void setKillMessage(KillMessage killMessage) {
        this.killMessage = killMessage;
    }

    public GoodsDetailDto getGoodsDetailDto() {
        return goodsDetailDto;
    }

    public void setGoodsDetailDto(GoodsDetailDto goodsDetailDto) {
        this.goodsDetailDto = goodsDetailDto;
    }
    public ProductMessage getProductMessage() {
        return productMessage;
    }

    public void setProductMessage(ProductMessage productMessage) {
        this.productMessage = productMessage;
    }

    public WxPayMessage getWxPayMessage() {
        return wxPayMessage;
    }

    public void setWxPayMessage(WxPayMessage wxPayMessage) {
        this.wxPayMessage = wxPayMessage;
    }
}
