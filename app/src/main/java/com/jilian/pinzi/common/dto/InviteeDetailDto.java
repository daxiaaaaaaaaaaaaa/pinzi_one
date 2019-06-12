package com.jilian.pinzi.common.dto;

import java.io.Serializable;
import java.util.List;

public class InviteeDetailDto implements Serializable {
    private static final long serialVersionUID = 1064957302234393614L;
    private String headImg;// true string 头像
    private String phone;// true string  联系电话
    private String name;// true string  名称
    private List<InviteeDetailItem> list;//
    private int isVip;// true number     会员等级（1.会员 2.金牌 3.铂金 4.钻石 5.皇冠）
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private String linkPhone;//

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<InviteeDetailItem> getList() {
        return list;
    }

    public void setList(List<InviteeDetailItem> list) {
        this.list = list;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }
}
