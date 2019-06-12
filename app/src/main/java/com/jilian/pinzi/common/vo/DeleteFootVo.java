package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class DeleteFootVo extends BaseVo {
    private static final long serialVersionUID = -6443827025004320206L;
    private String id;//false array[number]  浏览记录ID（多个使用数组）
    private Integer status;//true string1 单个删除，或多个删除，2删除全部
    private String uId;//false string            删除全部需要传入用户ID

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}
