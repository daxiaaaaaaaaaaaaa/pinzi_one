package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class DeleteGoodsVo extends BaseVo {

    private Integer type;//true number1.单个 2.全部

    private String uId;//false number   全部删除传用户Id


    private String shopIds;//false number    单个删除传购物车Id(多个用逗号隔开)

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getShopIds() {
        return shopIds;
    }

    public void setShopIds(String shopIds) {
        this.shopIds = shopIds;
    }
}
