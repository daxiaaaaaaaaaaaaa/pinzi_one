package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class InformationtDto implements Serializable {

    private String id;//     true    number      资讯Id
    private String title;//     true    string  标题
    private String imgUrl;// true    string   图标
    private long createDate;//     true   number    创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
