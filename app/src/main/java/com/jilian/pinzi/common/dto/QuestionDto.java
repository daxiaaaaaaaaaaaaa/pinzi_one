package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class QuestionDto implements Serializable {

    private String id;//   true     number   问卷Id
    private String title;//   true     number  问卷标题
    private long createDate;//  true     number    创建时间
    private String imgUrl;//  true  string   图标

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

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
