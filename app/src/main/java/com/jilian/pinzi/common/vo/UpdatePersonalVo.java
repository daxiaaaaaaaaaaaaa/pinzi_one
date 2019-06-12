package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

import java.util.Date;

public class UpdatePersonalVo extends BaseVo {


    private String headImg;//      头像 true string

    private String name;//string 名称


    private Integer sex;//性别 true

    private String profession;//string  职业 true


    private String province;//true;//省份 string

    private String city;//string 城市 true


    private String id;//string 用户ID true

    private String birthday ;//true string 生日

    private String area;// true string地区


    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
