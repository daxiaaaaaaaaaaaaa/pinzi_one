package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

/**
 *  我的积分、钱包、佣金入参
 */
public class MyRecordVo extends BaseVo {
   private String uId;//true number用户Id

   private Integer type;//1.积分 2.钱包 3.佣金

   private int  pageNo;//

   private int pageSize;//

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
