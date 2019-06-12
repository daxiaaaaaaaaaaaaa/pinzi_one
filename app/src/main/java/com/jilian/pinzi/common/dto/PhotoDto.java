package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class PhotoDto implements Serializable {
    private int type;//营业执照
    private String url;//门店照片

    public PhotoDto() {
    }

    public PhotoDto(int type, String url) {
        this.type = type;
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
