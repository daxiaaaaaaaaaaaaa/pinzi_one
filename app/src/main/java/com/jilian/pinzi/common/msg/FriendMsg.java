package com.jilian.pinzi.common.msg;

import java.io.Serializable;

public class FriendMsg implements Serializable {

    private String msg;
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
