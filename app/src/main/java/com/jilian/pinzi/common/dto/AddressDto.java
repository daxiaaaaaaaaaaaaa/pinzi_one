package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class AddressDto  implements Serializable {
    private static final long serialVersionUID = 282740417065693294L;
    private String id;//地址id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

     private String uId;//": 1,
    private String  name;//": "张三",
    private String  phone;//": "18652145215",
    private String  postalCode;//": "414100",
    private String  area;//": "广东省深圳市龙华区",
    private String  address;//": "民治南景新村",
    private int  isDefault;//": 0, 是否默认（0.否 1.是）
    private String  isDelete;//": 0,是否删除（0.否 1.是）
    private long createDate;//": 1541578500000
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
