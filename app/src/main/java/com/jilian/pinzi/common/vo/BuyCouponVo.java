package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class BuyCouponVo extends BaseVo {


    private String uId;//    true     number  用户Id
    private String money;//    true  number  金额
    private String couponId;//     true     number     优惠券Id
    private String score;//     true     number       使用积分（未使用传0）
    private String commission;//     true      使用佣金（未使用传0）
    private String type;//  true  number     1.微信 2.支付宝
    private String platform;//     true    number    1.app 2.pc 3.小程序

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
