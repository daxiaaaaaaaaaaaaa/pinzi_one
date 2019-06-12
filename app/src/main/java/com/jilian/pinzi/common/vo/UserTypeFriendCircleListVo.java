package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class UserTypeFriendCircleListVo extends BaseVo {

    private String uId;//true string 用户ID
    private Integer type;//true string  (2.终端 3.渠道 4.总经销商)
    private int startNum;//true string 当前页数
    private int pageSize;//true string  每页条数

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
}
