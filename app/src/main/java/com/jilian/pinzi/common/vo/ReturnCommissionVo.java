package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class ReturnCommissionVo extends BaseVo {


    private int startNum;//true string

    private int pageSize;//true string


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
