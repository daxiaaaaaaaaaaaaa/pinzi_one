package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class CommentListDto implements Serializable {

   private String  headImg;//     true   string   用户头像
   private String  id;//  true     number     评论Id
   private String  userName;//      true     string  用户昵称
   private String  content;//  true  string       评论内容
   private long  createDate;//  true   number  评论时间

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
