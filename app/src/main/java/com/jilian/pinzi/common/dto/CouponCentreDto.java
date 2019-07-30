package com.jilian.pinzi.common.dto;

import java.io.Serializable;

/**
 * 领券中心
 */
public class CouponCentreDto implements Serializable {
    private static final long serialVersionUID = -6541354304971976027L;
    private String fullReduct;//": 20,

    private String name;//": "优惠券2",
    private String storeName;//": "全平台",
    private String id;//": 2,
    private Integer type;//": 2,
    private String moneyOrDiscount;//": 5,
    private int useType;//": 2
    private int applyPlatform;
    private int validity;//有效期（1.日期范围 2.固定天数）
    private String fixDay;//固定天数
    private String validityDate;//": "2018-11-15",

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public String getFixDay() {
        return fixDay;
    }

    public void setFixDay(String fixDay) {
        this.fixDay = fixDay;
    }

    public int getApplyPlatform() {
        return applyPlatform;
    }

    public void setApplyPlatform(int applyPlatform) {
        this.applyPlatform = applyPlatform;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getUseType() {
        return useType;
    }

    public void setUseType(int useType) {
        this.useType = useType;
    }
}
