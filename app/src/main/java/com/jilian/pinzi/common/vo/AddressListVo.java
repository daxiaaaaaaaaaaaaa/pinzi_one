package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class AddressListVo extends BaseVo {

    private String uId;//string 用户ID
    private Integer pageNo;//true
    private Integer pageSize;//true

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
