package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class GoodsEvaluateItem implements Serializable {
    private static final long serialVersionUID = -4150237769117615713L;
    private String profession;//职业
    ;//: ;//养猪员;//,
    private String headImg;//头像: ;//http://120.79.210.114:9006/donghui_app/IMG/201811050517254.jpg;//,
    private String city;//: ;//深圳市;//,
    private String content;//评价内容: ;//还ok;//,
    private String likeNum;//点赞数: 0,
    private String imgUrl;//: ;//评论图片http://b.hiphotos.baidu.com/image/h%3D300/sign=7da3396d56b5c9ea7df305e3e539b622/cf1b9d16fdfaaf51bae68ed9815494eef01f7a4a.jpg;//,
    private String idAnonymity;//是否匿名（0.否 1.是）: 0,
    private String commentNum;//评论数: 0,
    private String grade;//评分: 4,
    private String likeId;//点赞Id: 0,
    private String name;//用户名称: ;//猪猪侠;//,
    private String id;//:评论Id 2,
    private long createDate;//创建时间: 1541490805000

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getIdAnonymity() {
        return idAnonymity;
    }

    public void setIdAnonymity(String idAnonymity) {
        this.idAnonymity = idAnonymity;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getLikeId() {
        return likeId;
    }

    public void setLikeId(String likeId) {
        this.likeId = likeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
