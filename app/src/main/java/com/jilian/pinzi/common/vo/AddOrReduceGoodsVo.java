package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class AddOrReduceGoodsVo extends BaseVo {

    private String shopId;//true number商品Id

   private int  type;//true number1.增加 2.减少

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
