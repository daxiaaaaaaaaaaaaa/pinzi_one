package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class FriendCircleListVo extends BaseVo {
    private static final long serialVersionUID = 5334196477825655396L;
    private String uId;//true string       用户ID
    private int startNum;//true string 当前页数
    private int pageSize;//true string  每页条数

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
