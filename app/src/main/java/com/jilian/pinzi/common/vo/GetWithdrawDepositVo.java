package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class GetWithdrawDepositVo extends BaseVo {

    private String uId;//true string   用户Id


   private String  money;//true string金额


    private String accountName;//true string账号名称


    private String account;//true string账号


    private Integer type;//true string1.钱包 2.佣金

    private int classify;//1.支付宝 2.银行卡

    public int getClassify() {
        return classify;
    }

    public void setClassify(int classify) {
        this.classify = classify;
    }

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

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
