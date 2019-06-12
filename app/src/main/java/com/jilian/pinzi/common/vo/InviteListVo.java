package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class InviteListVo extends BaseVo {
    private static final long serialVersionUID = 1788340200150940788L;
    private String uId;//true number    用户Id
    private Integer type;//true number1.上级 2.下级 3.下二级 4下三级
    private Integer pageNo;//false number
    private Integer pageSize;//false number

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
