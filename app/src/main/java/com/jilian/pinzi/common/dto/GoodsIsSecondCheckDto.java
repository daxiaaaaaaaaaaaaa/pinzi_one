package com.jilian.pinzi.common.dto;

import com.jilian.pinzi.base.BaseVo;

public class GoodsIsSecondCheckDto extends BaseVo {
    private String goodsId;//       "goodsId": "1,2,3,4",
    private String prices;//"prices": "100.0,200.0,500.0,20.0"

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }
}
