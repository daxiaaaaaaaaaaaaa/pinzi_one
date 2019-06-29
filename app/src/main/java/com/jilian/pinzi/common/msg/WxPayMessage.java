package com.jilian.pinzi.common.msg;

import java.io.Serializable;

public class WxPayMessage implements Serializable {
    private int payCode;//200 成功

    public int getPayCode() {
        return payCode;
    }

    public void setPayCode(int payCode) {
        this.payCode = payCode;
    }
}
