package com.jilian.pinzi.common.msg;

import java.io.Serializable;

/**
 * 首页创建消息实体
 */
public class MainCreatMessage implements Serializable {
    private int code;//200 成功

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
