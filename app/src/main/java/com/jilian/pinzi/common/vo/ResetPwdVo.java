package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class ResetPwdVo extends BaseVo {
    private String phone;//true string
    private String msgCode;//验证码
    private String password;//确认密码（前端验证两次密码输入一致后传入后台）

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
