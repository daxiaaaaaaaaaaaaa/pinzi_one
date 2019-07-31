package com.jilian.pinzi.common.msg;

import java.io.Serializable;

public class KillMessage implements Serializable {
 private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
