package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

/**
 * 活动
 */
public class ActivityVo extends BaseVo {
    //活动列表接口
    private String identity;//  true     string     1.个人 2.门店 3.二批商 4.总经销商
    private int type;//   true   string     0.进行中 1.已结束
    private Integer pageNo;//    false     string
    private Integer pageSize;//    false string
    //活动详情接口
    private String id;//    true     string      活动Id
    private String uId;//   true    string  用户Id
    //报名接口
    private String aId;//    true     string       活动id
    //取消报名
    private String applyId;//报名IDD

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
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

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
}
