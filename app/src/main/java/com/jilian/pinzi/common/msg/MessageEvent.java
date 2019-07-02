package com.jilian.pinzi.common.msg;

import java.io.Serializable;

public class MessageEvent implements Serializable {
    private WxPayMessage wxPayMessage;
    private ProductMessage productMessage;

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
