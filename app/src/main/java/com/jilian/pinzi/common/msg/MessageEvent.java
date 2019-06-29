package com.jilian.pinzi.common.msg;

import java.io.Serializable;

public class MessageEvent implements Serializable {
    private WxPayMessage wxPayMessage;

    public WxPayMessage getWxPayMessage() {
        return wxPayMessage;
    }

    public void setWxPayMessage(WxPayMessage wxPayMessage) {
        this.wxPayMessage = wxPayMessage;
    }
}
