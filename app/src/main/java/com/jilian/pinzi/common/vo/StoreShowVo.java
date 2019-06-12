package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

/**
 *店铺展示
 */
public class StoreShowVo extends BaseVo {

    private static final long serialVersionUID = 6322035633534767456L;
    private int startNum;//alse string 1  当前页数
    private int pageSize;//false string 10 每页条数

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
