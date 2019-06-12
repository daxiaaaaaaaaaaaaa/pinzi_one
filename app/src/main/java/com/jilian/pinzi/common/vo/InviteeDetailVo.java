package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class InviteeDetailVo extends BaseVo {
    private static final long serialVersionUID = 1788340200150940788L;
    private String uId;//true number    用户Id
    private String inviteeId;//下级Id
    private Integer pageNo;//false number
    private Integer pageSize;//false number

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getInviteeId() {
        return inviteeId;
    }

    public void setInviteeId(String inviteeId) {
        this.inviteeId = inviteeId;
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
