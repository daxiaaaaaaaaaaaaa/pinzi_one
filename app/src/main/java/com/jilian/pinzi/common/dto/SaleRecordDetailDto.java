package com.jilian.pinzi.common.dto;

import java.io.Serializable;

/**
 * 记录详情
 */
public class SaleRecordDetailDto implements Serializable {

    private static final long serialVersionUID = 2080534966895976224L;

    /**
     * courierName :
     * courierNo :
     * createDate : 1546864694000
     * describtion : 方便
     * goodsName : 牛栏山 二锅头
     * id : 47
     * linkPeople : 方便
     * linkPhone : 15277432764
     * quantity : 1
     * reason : 7天无理由退货
     * receiveAddress :
     * receiveName :
     * receivePhone :
     * refundPrice : 10
     * serviceNo : 2019010720381429290
     * status : 1
     */

    private String courierName;
    private String courierNo;
    private long createDate;
    private String describtion;
    private String goodsName;
    private int id;
    private String linkPeople;
    private String linkPhone;
    private int quantity;
    private String reason;
    private String receiveAddress;
    private String receiveName;
    private String receivePhone;
    private int refundPrice;
    private String serviceNo;
    private int status;

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public String getCourierNo() {
        return courierNo;
    }

    public void setCourierNo(String courierNo) {
        this.courierNo = courierNo;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLinkPeople() {
        return linkPeople;
    }

    public void setLinkPeople(String linkPeople) {
        this.linkPeople = linkPeople;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public int getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(int refundPrice) {
        this.refundPrice = refundPrice;
    }

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
