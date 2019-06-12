package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class InviteListDto implements Serializable {
    private static final long serialVersionUID = 2139773118705248980L;
    private String headImg;//true string;//头像
    private String phone;// true string    电话（账号）
    private String uId;// true number   上级或下级Id
    private String name;// true string   用户名称
    private int isVip;// true number    会员等级（1.会员 2.金牌 3.铂金 4.钻石 5.皇冠）
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }
}
