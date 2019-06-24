package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class QuestionVo extends BaseVo {
    private Integer pageNo;//    false     string
    private Integer pageSize;//    false string
    private String uId;
    private int type;//1.未填写 2.已填写
    private String qId;//问卷ID

    public String getqId() {
        return qId;
    }

    public void setqId(String qId) {
        this.qId = qId;
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

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
