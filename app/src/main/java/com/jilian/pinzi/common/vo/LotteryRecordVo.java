package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class LotteryRecordVo extends BaseVo{
    private String uId;
    private int startNum = 1;//
    private int pageSize = 20;//

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
