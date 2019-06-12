package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class GetUserAddressListVo extends BaseVo {

    private String uId;//    truestring用户ID

    private int pageNo;//    truestring用户ID
    private int pageSize;//    truestring用户ID


    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
