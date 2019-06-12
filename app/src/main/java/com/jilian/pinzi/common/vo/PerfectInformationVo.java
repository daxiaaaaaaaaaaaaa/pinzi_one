package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class PerfectInformationVo extends BaseVo {

    private String province;//true string 省份

    private String city;//城市   true  string

    private String area;//   true string 地区

    private String name;//true string     终端名称（渠道名称）

    private String contact;// true string 联系人

    private String linkPhone;//true string 联系电话

    private String id;//true ID

    private String imgUrl;// File  图片地址

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

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
}
