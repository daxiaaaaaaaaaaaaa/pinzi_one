package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class CommentInformationVo extends BaseVo {


   private String  uId;//    true     string    用户Id
   private String  content;//    true   string        评论内容
   private String  id;//    true    string    资讯Id


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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
