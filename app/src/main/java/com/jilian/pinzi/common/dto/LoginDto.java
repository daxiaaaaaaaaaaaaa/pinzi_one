package com.jilian.pinzi.common.dto;

import java.io.Serializable;

/**
 * 登陆返回实体
 *
 * @author weishixiong
 * @Time 2018-03-30
 */
public class LoginDto implements Serializable {
    private static final long serialVersionUID = 2635732415971204098L;
    private String id;//":106,
    private String name;//":"系统名称",
    private String phone;//":"13129554264",
    private String password;//":"9714a0c8b8a0a88e3d29a278b85b2390",
    private String headImg;//":"http://120.79.210.114:9006/donghui_app/IMG/2018110810480920181108104809[0].jpg",
    private String sex;//":null,
    private String birthday;//":null,
    private String province;//":null,
    private String city;//":null,
    private String area;//":null,
    private String wxId;//":null,
    private String qqId;//":null,
    private String wbId;//":null,
    private String profession;//":null,
    private String contact;//":null,
    private String linkPhone;//":null,
    private String imgUrl;//":null,
    private int type;//":1,类型（1.普通用户 2.终端 3.渠道 4.总经销商 5平台賬號）
    private int isVip;//":1,会员等级（1.会员 2.金牌 3.铂金 4.钻石 5.皇冠）
    private String checkStatus;//":2,
    private String isUse;//":0,
    private String isDelete;//":0,
    private String createDate;//":1543484059000,
    private String invitationCode;//":"860356",
    private String totalMoney;//":null,
    private String totalpepole;//":null,
    private String token;//":"vPTVAuV/w+Wg7YMt+lXdPJarqHTLIX9BGLDgW1STyFc+ZRnjUGqdlw5BEPmWM61zfqnmbJhCTz8PCsuoSO7URw==",
    private String month;//":null,
    private String scoreNum;//":null,
    private String balanceNum;//":null,
    private String commisionNum;//":null,
    private String orderCount;//":0,
    private String loginNumber;//":1
    //        public final static int SUCCESS_CODE = 200;
    //        public final static int NOPERFORM_CODE = 202;//未完善资料
    //        public final static int CHECKING_CODE = 204;//用户审核中
    //        public final static int CHECKFAILUER_CODE = 206;// 用户未通过审核;
    private int code;//

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
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

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getQqId() {
        return qqId;
    }

    public void setQqId(String qqId) {
        this.qqId = qqId;
    }

    public String getWbId() {
        return wbId;
    }

    public void setWbId(String wbId) {
        this.wbId = wbId;
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

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getTotalpepole() {
        return totalpepole;
    }

    public void setTotalpepole(String totalpepole) {
        this.totalpepole = totalpepole;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getScoreNum() {
        return scoreNum;
    }

    public void setScoreNum(String scoreNum) {
        this.scoreNum = scoreNum;
    }

    public String getBalanceNum() {
        return balanceNum;
    }

    public void setBalanceNum(String balanceNum) {
        this.balanceNum = balanceNum;
    }

    public String getCommisionNum() {
        return commisionNum;
    }

    public void setCommisionNum(String commisionNum) {
        this.commisionNum = commisionNum;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public String getLoginNumber() {
        return loginNumber;
    }

    public void setLoginNumber(String loginNumber) {
        this.loginNumber = loginNumber;
    }
}
