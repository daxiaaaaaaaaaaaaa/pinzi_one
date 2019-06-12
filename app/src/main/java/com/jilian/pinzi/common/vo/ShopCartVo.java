package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class ShopCartVo extends BaseVo {

    private String uId;//true number 用户Id

    private Integer pageNo;//false number

    private  Integer pageSize;//false number

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
