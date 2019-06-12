package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class ScoreBuyGoodsVo extends BaseVo {

    private Integer pageNo;//false number
    private Integer pageSize;//false number
    private int identity;//true string 登录用户身份（1.普通 2.终端 3.渠道 4.总经销商）
    private int type;//1.积分商品 2.相似推荐商品

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

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
