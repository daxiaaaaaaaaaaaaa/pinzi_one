package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class FriendCommentUser implements Serializable {
    private static final long serialVersionUID = -796642976697967934L;
              private String id;//":23,
              private String headImg;//":null,
              private String name;//":"well"

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
