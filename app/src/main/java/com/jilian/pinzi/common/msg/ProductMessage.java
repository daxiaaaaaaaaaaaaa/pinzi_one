package com.jilian.pinzi.common.msg;

import java.io.Serializable;

public class ProductMessage implements Serializable {
    private int code;//200 成功

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
