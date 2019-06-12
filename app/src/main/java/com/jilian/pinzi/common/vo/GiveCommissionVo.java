package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class GiveCommissionVo extends BaseVo {

   private String uId;//true number用户id

    private String phone;//true string手机号码

    private String commission;//true number赠送佣金

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
