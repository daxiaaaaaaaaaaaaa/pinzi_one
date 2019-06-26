package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

/**
 *店铺展示
 */
public class StoreShowVo extends BaseVo {

    private static final long serialVersionUID = 6322035633534767456L;
    private int startNum;//alse string 1  当前页数
    private int pageSize;//false string 10 每页条数
    private String content;//    false string 搜索内容
    private String  province;//   false string 省
    private String city;//false string 市
    private String area;//false string 区
    private Double lat;//false string  纬度（用户定位地址）
    private Double lot;// false string 经度（用户定位地址）
    private String orderby;//false string 1.距离从近到远 2.距离从远到近
    private String scoreBy;//true string 1.好评优先

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLot() {
        return lot;
    }

    public void setLot(Double lot) {
        this.lot = lot;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }



    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public String getScoreBy() {
        return scoreBy;
    }

    public void setScoreBy(String scoreBy) {
        this.scoreBy = scoreBy;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
