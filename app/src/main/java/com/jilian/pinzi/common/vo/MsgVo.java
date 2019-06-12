package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class MsgVo extends BaseVo {
    private String uId;// 用户ID true string


    private int startNum;//false string当前页数


    private int pageSize;//false string每页条数


    private int type;//true string类型（1.新闻公告 2.消息

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
