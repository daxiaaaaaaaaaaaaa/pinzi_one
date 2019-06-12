package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class BuyerCenterGoodsVo extends BaseVo {

    private Integer identity;// true string登录用户身份（1.普通 2.终端 3.渠道 4.总经销商）
    private String goodsType;//     true string分类Id
    private int pageNo;//false number
    private int pageSize;//false number
    private String entrance;//1 采购中心 2.分类商品接口

    public String getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
