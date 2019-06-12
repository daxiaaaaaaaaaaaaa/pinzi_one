package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class RegisterVo extends BaseVo {


    private Integer type;//用户类型（1.普通用户 2.终端 3.渠道 4.总经销商）

    private String phone;//  电话号码

    private String msgCode;//验证码

    private String password;//密码

    private String invitationCode;//   邀请码

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

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

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }
}
