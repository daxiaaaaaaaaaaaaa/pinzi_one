package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class ShopCartListVo extends BaseVo {
   private String  uId;//true number            用户Id

    private int pageNo;//false number

    private int pageSize;//false number

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
