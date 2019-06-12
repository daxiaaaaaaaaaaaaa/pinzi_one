package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class SmsVo extends BaseVo {

    private String phone;//true string 电话

    private Integer type;//   1.注册 2.找回密码


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
