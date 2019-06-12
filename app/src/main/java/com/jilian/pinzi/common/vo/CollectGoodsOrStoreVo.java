package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class CollectGoodsOrStoreVo  extends BaseVo {
    private static final long serialVersionUID = 5090860535454810722L;
    private String uId;//true number      用户Id
    private String goodOrStoreId;//true number    商品或店铺Id
    private Integer type;//true number1.收藏商品 2.收藏店铺

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getGoodOrStoreId() {
        return goodOrStoreId;
    }

    public void setGoodOrStoreId(String goodOrStoreId) {
        this.goodOrStoreId = goodOrStoreId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
