package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class StoreCouponVo extends BaseVo {


    private String storeId;//    true     number
    private String uId;//    true    number
    private Integer pageNo;//  true    string
    private Integer pageSize;//    true  string

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

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
