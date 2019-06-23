package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

/**
 * 作品
 */
public class ProductVo extends BaseVo {


    private String uId;//    true     string       用户Id
    private String aId;//     true     string 活动Id
    private int type;//    true     string     1.投票 2，取消投票
    private String apId;//  作品Id


    public String getApId() {
        return apId;
    }

    public void setApId(String apId) {
        this.apId = apId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
