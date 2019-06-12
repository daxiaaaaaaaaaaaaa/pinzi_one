package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class MsgDto implements Serializable {
    private String id;//":4,
    private String title;//":"通告3",
    private String content;//":"通告3内容",
    private String releaseObject;//":0,
    private String sysUserId;//":null,
    private String type;//":1,
    private long createDate;//":1542030337000,
    private String rId;//":null

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReleaseObject() {
        return releaseObject;
    }

    public void setReleaseObject(String releaseObject) {
        this.releaseObject = releaseObject;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }
}
