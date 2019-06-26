package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class  FriendCircleIssueVo extends BaseVo {
    private String uId;//true string 用户ID
    private String content;//true string内容
    private String imgUrl;//内容true string  图片
    private String photoSize;// true string图片尺寸
    private String video;//

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
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

    public String getPhotoSize() {
        return photoSize;
    }

    public void setPhotoSize(String photoSize) {
        this.photoSize = photoSize;
    }
}
