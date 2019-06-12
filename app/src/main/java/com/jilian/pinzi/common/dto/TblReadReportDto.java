package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class TblReadReportDto implements Serializable {

    private String id;//":1,
    private String uId;//":1,
    private String reportId;//":1,
    private String createDate;//":1541596584000

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
