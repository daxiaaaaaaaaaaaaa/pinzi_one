package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;


public class InvoiceVo extends BaseVo {
    private static final long serialVersionUID = 4098153891361339131L;
    private Integer type;//false string    类型（1.增值税专用发票 2.增值税普通发票）
    private String invoiceTitle;//false string     发票抬头
    private String unitAddress;//false string        单位地址
    private String phone;//false string       联系电话
    private String bankAccount;//false string银行账户
    private String openBank;//false string     开户行
    private String dutyMark;//false string  税号
    private String takerName;//false string         收票人姓名
    private String takerPhone;//false string        收票人电话
    private String takerAddress;//false string  收票地址

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getUnitAddress() {
        return unitAddress;
    }

    public void setUnitAddress(String unitAddress) {
        this.unitAddress = unitAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public String getDutyMark() {
        return dutyMark;
    }

    public void setDutyMark(String dutyMark) {
        this.dutyMark = dutyMark;
    }

    public String getTakerName() {
        return takerName;
    }

    public void setTakerName(String takerName) {
        this.takerName = takerName;
    }

    public String getTakerPhone() {
        return takerPhone;
    }

    public void setTakerPhone(String takerPhone) {
        this.takerPhone = takerPhone;
    }

    public String getTakerAddress() {
        return takerAddress;
    }

    public void setTakerAddress(String takerAddress) {
        this.takerAddress = takerAddress;
    }
}
