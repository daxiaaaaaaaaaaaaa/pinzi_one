package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class EvaluateDetailDto implements Serializable {
    private static final long serialVersionUID = -6187465872514865024L;
    private  String id;//true number    评价Id
    private  String orderId;// true number    订单Id
    private  String uId;// true number  用户Id
    private  int grade;//   true number    评分
    private  String content;// true string  内容
    private  String imgUrl;// true string      图片
    private  int isAnonymity;//    true number   是否匿名（0.否 1.是
    private  int commentNum;// true number;评论数
    private  int likeNum;// true number    点赞数
    private  long createDate;// true number创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getIsAnonymity() {
        return isAnonymity;
    }

    public void setIsAnonymity(int isAnonymity) {
        this.isAnonymity = isAnonymity;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
