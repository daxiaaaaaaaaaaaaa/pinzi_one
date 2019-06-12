package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class InviteeDetailItem implements Serializable {
    private static final long serialVersionUID = 1064957302234393614L;
    private String file;//true string    商品文件
    private String name;// true string商品名称
    private double commission;//true number  我获取的佣金
    private long payDate;// true number购买时间
    private int isVip;// true number     会员等级（1.会员 2.金牌 3.铂金 4.钻石 5.皇冠）
    private int type;

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public long getPayDate() {
        return payDate;
    }

    public void setPayDate(long payDate) {
        this.payDate = payDate;
    }
}
