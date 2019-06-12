package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class MemberDto implements Serializable {
    private static final long serialVersionUID = -6420514683734913355L;
    private String id;//true numberID
    private String name;// true string  名称
    private String phone;// true string    电话
    private String password;// true string  密码
    private String headImg;// true string 头像
    private String sex;// true string    性别
    private long birthday;// true number   生日
    private String province;// true string   省份
    private String city;// true string     城市
    private String area;// true string   地区
    private String profession;// true string   职业
    private String contact;// true string   联系人
    private String linkPhone;// true string       联系电话
    private String imgUrl;// true string 图片（多张用逗号隔开）
    private int type;// true number类型（1.普通用户 2.终端 3.渠道 4.总经销商）
    private int isVip;// true number  会员等级（1.会员 2.金牌 3.铂金 4.钻石 5.皇冠）
    private int checkStatus;// true number    审核状态（1.待审核 2.审核通过 3.审核失败） type为2,3需填
    private int isUse;// true number    是否启用（0.启用 1.禁用）
    private int isDelete;// true number    是否删除（0.否 1.是）
    private long createDate;// true number   创建时间
    private String invitationCode;// true string   邀请码
    private double totalMoney;// true string   销售额
    private String totalpepole;// true string      直推人数
    private int upgrade;// true string   是否升级（0 未升级， 1 升级成功）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
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

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public int getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(int checkStatus) {
        this.checkStatus = checkStatus;
    }

    public int getIsUse() {
        return isUse;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getTotalpepole() {
        return totalpepole;
    }

    public void setTotalpepole(String totalpepole) {
        this.totalpepole = totalpepole;
    }

    public int getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(int upgrade) {
        this.upgrade = upgrade;
    }
}
