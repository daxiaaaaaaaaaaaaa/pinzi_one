package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class AddMySuperiorVo extends BaseVo {
    private static final long serialVersionUID = 987937507798501570L;
    private String uId;//     用户Id
    private String account;//true string      添加账号

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
