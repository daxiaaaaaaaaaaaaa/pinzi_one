package com.jilian.pinzi.common.msg;

import java.io.Serializable;

/**
 * 首页创建消息实体
 */
public class MainCreatMessage implements Serializable {
    private int code;//200 成功
    private int type;//1
    private String uId;//

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
