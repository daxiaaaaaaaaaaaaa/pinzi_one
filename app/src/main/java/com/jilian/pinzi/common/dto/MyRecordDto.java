package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class MyRecordDto implements Serializable {

    private String id;//":5,
    private String uId;//":1,
    private String title;//":"购买商品赠送积分",
    private Double source;//":15,
    private int status;//":1, 1.增 2.减
    private Integer type;//":1,
    private long createDate;//":1541504923000
    private boolean showDay;
    private Double getCount;//获得的数量
    private Double useCount;//使用的数量
    private String day;
    private String buyUserName;//":"系统名称",
    private int wdStatus;//（提现状态）1.待审核 2.审核成功 3.审核失败

    public int getWdStatus() {
        return wdStatus;
    }

    public void setWdStatus(int wdStatus) {
        this.wdStatus = wdStatus;
    }

    public String getBuyUserName() {
        return buyUserName;
    }

    public void setBuyUserName(String buyUserName) {
        this.buyUserName = buyUserName;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getSource() {
        return source;
    }

    public void setSource(Double source) {
        this.source = source;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public boolean isShowDay() {
        return showDay;
    }

    public void setShowDay(boolean showDay) {
        this.showDay = showDay;
    }

    public Double getGetCount() {
        return getCount;
    }

    public void setGetCount(Double getCount) {
        this.getCount = getCount;
    }

    public Double getUseCount() {
        return useCount;
    }

    public void setUseCount(Double useCount) {
        this.useCount = useCount;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
