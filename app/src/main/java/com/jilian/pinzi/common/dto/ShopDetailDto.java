package com.jilian.pinzi.common.dto;

import java.io.Serializable;

/**
 * 商铺详情
 */
public class ShopDetailDto implements Serializable {
    private static final long serialVersionUID = -5717026744736892026L;
    private String area;

    private String address;

    private String province;

    private String city;

    private String phone;

    private int collectId;
    private int collectCount;

    private float storeGrade;

    private String storeLogo;

    private String storeName;

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return this.area;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return this.province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setCollectId(int collectId) {
        this.collectId = collectId;
    }

    public int getCollectId() {
        return this.collectId;
    }

    public void setStoreGrade(int storeGrade) {
        this.storeGrade = storeGrade;
    }

    public float getStoreGrade() {
        return this.storeGrade;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
    }

    public String getStoreLogo() {
        return this.storeLogo;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreName() {
        return this.storeName;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }
}
