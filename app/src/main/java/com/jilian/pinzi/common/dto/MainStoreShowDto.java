package com.jilian.pinzi.common.dto;

import java.io.Serializable;

/**
 * 店铺展示
 */
public class MainStoreShowDto implements Serializable {
    private static final long serialVersionUID = -5140069961316930790L;
    private String id;//": 2,
    private String imgUrl;//": "http://120.79.210.114:9006/donghui_app/IMG/201811050517254.jpg",
    private String name;//": "极联商铺2",
    private String province;//": "广东省",
    private String city;//": "深圳市",
    private String area;//": "南山区",
    private String address;//": "华瀚科技2",
    private String proprietorName;//": "极联测试2",
    private String phone;//": "74120",
    private String alipayAccount;//": "54110",
    private String wechatAccount;//": "94110",
    private String grade;//": 5,
    private String status;//": 2,
    private long createDate;//": 1541660804000

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProprietorName() {
        return proprietorName;
    }

    public void setProprietorName(String proprietorName) {
        this.proprietorName = proprietorName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    public String getWechatAccount() {
        return wechatAccount;
    }

    public void setWechatAccount(String wechatAccount) {
        this.wechatAccount = wechatAccount;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
