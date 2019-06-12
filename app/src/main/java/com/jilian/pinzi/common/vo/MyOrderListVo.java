package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class MyOrderListVo extends BaseVo {
    private static final long serialVersionUID = -7231337303691346686L;
    private String uId;////true number    用户Id
    private Integer payStatus;//true number0.全部 1.待支付 2.待收货 3.已完成 4.已取消
    private Integer pageNo;//false number
    private Integer paegSize;//false number

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPaegSize() {
        return paegSize;
    }

    public void setPaegSize(Integer paegSize) {
        this.paegSize = paegSize;
    }
}
