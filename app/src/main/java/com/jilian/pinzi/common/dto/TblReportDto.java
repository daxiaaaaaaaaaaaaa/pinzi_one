package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class TblReportDto implements Serializable {

    private String id;//":1,
    private String title;//":"全体通告",
    private String content;//":"全体公告内容",
    private String releaseObject;//:0,
    private String sysUserId;//":null,
    private String type;//":2,
    private String createDate;//":1541597311000

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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
