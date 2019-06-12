package com.jilian.pinzi.common;

import com.jilian.pinzi.common.dto.FriendCommentUser;

import java.io.Serializable;

/**
 * 第二层回复
 */
public class FriendReplyComment implements Serializable {
    private static final long serialVersionUID = 8181278803791659102L;

    private String id;//":86,
    private String uId;//":23,
    private String content;//":"Bhhh ",
    private String fcId;//":57,
    private String parentId;//":0,
    private String type;//":1,
    private String createDate;//":1545019052000,
    private String replyComment;
    private FriendCommentUser replyCommentUser;//
    private FriendCommentUser commentUser;//

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getReplyComment() {
        return replyComment;
    }

    public void setReplyComment(String replyComment) {
        this.replyComment = replyComment;
    }

    public FriendCommentUser getReplyCommentUser() {
        return replyCommentUser;
    }

    public void setReplyCommentUser(FriendCommentUser replyCommentUser) {
        this.replyCommentUser = replyCommentUser;
    }

    public FriendCommentUser getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(FriendCommentUser commentUser) {
        this.commentUser = commentUser;
    }
}
