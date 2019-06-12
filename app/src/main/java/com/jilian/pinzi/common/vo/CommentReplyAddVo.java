package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class CommentReplyAddVo extends BaseVo {
    private static final long serialVersionUID = 1581156557450740430L;

    private String uId;//true string 用户
    private String content;// true string 内容
    private String fcId;//true string 朋友圈
    private String parentId;//true string    回复评论ID

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
