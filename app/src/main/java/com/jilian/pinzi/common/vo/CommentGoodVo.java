package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class CommentGoodVo extends BaseVo {
    private static final long serialVersionUID = -8559199045545004639L;
    private String uId;//true number用户Id
   private String orderId;//true number订单Id
   private Integer grade;//订单Idtrue string评分
   private String content;//true string 内容
   private String imgUrl;//true string   图片
   private Integer isAnonymity;//true number    是否匿名（0.否 1.是）

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
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

    public Integer getIsAnonymity() {
        return isAnonymity;
    }

    public void setIsAnonymity(Integer isAnonymity) {
        this.isAnonymity = isAnonymity;
    }
}
