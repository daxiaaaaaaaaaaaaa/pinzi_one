package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class ThirdUserLoginVo extends BaseVo {
    private String loginId;//true string    登录ID
    private String loginType;// false string 0微信 1QQ 2微博
    private String headImg;//false string 头像
    private String uName;//false string    名称
    private String type;// false number    -1.第一次传参 0,用户输入手机号界面调用三方登录接口，
    // 二次调用需要携带以下参数且type类型的值必须为类型（1.普通用户 2.门店 3.二批商）

    private String phone;// false number 电话号码
    private String msgCode;//false number     短信验证码
    private String InvitationCode;//false number    邀请码
    private String password;// false number  密码

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public String getInvitationCode() {
        return InvitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        InvitationCode = invitationCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
