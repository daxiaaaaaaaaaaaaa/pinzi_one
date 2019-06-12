package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class UpdatePwdVo extends BaseVo {

    private String pastPassword;//true    旧密码string

    private String newPassword;//新密码string

    private String id;// 用户IDstring

    public String getPastPassword() {
        return pastPassword;
    }

    public void setPastPassword(String pastPassword) {
        this.pastPassword = pastPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
