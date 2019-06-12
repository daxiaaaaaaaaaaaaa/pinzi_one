package com.jilian.pinzi.common.dto;

import java.io.Serializable;
import java.util.List;

public class CollectionFootDto implements Serializable {
    private List<GoodlistDto > goodlist ;//商品
    private List<BusinesslistDto > Businesslist ;//店铺
    private String  createDate;//

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public List<GoodlistDto> getGoodlist() {
        return goodlist;
    }

    public void setGoodlist(List<GoodlistDto> goodlist) {
        this.goodlist = goodlist;
    }

    public List<BusinesslistDto> getBusinesslist() {
        return Businesslist;
    }

    public void setBusinesslist(List<BusinesslistDto> businesslist) {
        Businesslist = businesslist;
    }
}
