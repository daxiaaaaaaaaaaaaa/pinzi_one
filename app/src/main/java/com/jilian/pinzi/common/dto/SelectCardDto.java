package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class SelectCardDto  implements Serializable {
    private static final long serialVersionUID = 6987233172486324481L;
             private String fullReduct;//": 20,
             private String validityDate;//": "2018-11-15",
             private String goodsId;//": "1",
             private String name;//": "优惠券1",
             private String applyPlatform;//": "0",
             private Integer type;//": 2,
             private String moneyOrDiscount;//": 5,
             private String useType;//": 1
             private String storeName;
             private boolean isCheck;
             private String id;
             private String goodsQuantity;

    public String getGoodsQuantity() {
        return goodsQuantity;
    }

    public void setGoodsQuantity(String goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getFullReduct() {
        return fullReduct;
    }

    public void setFullReduct(String fullReduct) {
        this.fullReduct = fullReduct;
    }

    public String getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(String validityDate) {
        this.validityDate = validityDate;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApplyPlatform() {
        return applyPlatform;
    }

    public void setApplyPlatform(String applyPlatform) {
        this.applyPlatform = applyPlatform;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMoneyOrDiscount() {
        return moneyOrDiscount;
    }

    public void setMoneyOrDiscount(String moneyOrDiscount) {
        this.moneyOrDiscount = moneyOrDiscount;
    }

    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }
}
