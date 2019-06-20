package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class InformationVo extends BaseVo {

    private Integer pageNo;//    false     string
    private Integer pageSize;//    false  string
    private String typeId;// 分类Id
    private String id;//   true     string   资讯Id
    private String uId;//    true     string   用户Id

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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}
