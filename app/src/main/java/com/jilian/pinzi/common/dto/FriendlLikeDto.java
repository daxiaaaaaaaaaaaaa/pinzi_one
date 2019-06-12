package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class FriendlLikeDto implements Serializable {
    private static final long serialVersionUID = -7726751372445675440L;
    private String id;//":173,
    private String fcId;//":57,
    private String uId;//":23,
    private String type;//":1,
    private String createDate;//":1544756912000,
    private String name;//":"well",
    private String headImg;//":"http://120.79.210.114:9006/donghui_app/IMG/2018121003350620181210153506[0].jpg"

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFcId() {
        return fcId;
    }

    public void setFcId(String fcId) {
        this.fcId = fcId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
}
