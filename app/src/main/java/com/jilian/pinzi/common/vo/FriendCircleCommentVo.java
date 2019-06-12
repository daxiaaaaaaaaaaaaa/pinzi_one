package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class  FriendCircleCommentVo extends BaseVo {

    private static final long serialVersionUID = 4990856328095185611L;
    private String uId;//true string用户ID
    private String content;//true string评论内容
    private String fcId;//true string朋友圈ID

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

    public String getFcId() {
        return fcId;
    }

    public void setFcId(String fcId) {
        this.fcId = fcId;
    }
}
