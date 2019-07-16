package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class VersionInfoDto implements Serializable {


    private String id;//  true     number
    private String versionNo ;//  true    string    版本号
    private String linkUrl;//   true   string 下载路径
    private String descs;// true   string   描述
    private long createDate;//    true     number 更新时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
