package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class DiscountMoneyDto implements Serializable {
    private static final long serialVersionUID = -8802114124813589070L;
    private double scoreRemission;//true number   积分抵扣金额
    private double couponRemission;// true number       优惠券抵扣金额
    private double commissionRemission;// true number   佣金抵扣金额

    public double getScoreRemission() {
        return scoreRemission;
    }

    public void setScoreRemission(double scoreRemission) {
        this.scoreRemission = scoreRemission;
    }

    public double getCouponRemission() {
        return couponRemission;
    }

    public void setCouponRemission(double couponRemission) {
        this.couponRemission = couponRemission;
    }

    public double getCommissionRemission() {
        return commissionRemission;
    }

    public void setCommissionRemission(double commissionRemission) {
        this.commissionRemission = commissionRemission;
    }
}
