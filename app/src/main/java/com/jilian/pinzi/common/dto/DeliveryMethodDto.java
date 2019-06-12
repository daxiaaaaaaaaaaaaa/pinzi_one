package com.jilian.pinzi.common.dto;

import java.io.Serializable;

/**
 * 快递公司
 */
public class DeliveryMethodDto implements Serializable {
    private String id;// true number    快递公司物流


    private String deliveryMethodNumber;// true string           快递公司编号


    private String deliveryMethodName;// true string   快递公司名称


    private String deliveryMethodPhone;// true string    快递公司电话


    private long createDate;// true string创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeliveryMethodNumber() {
        return deliveryMethodNumber;
    }

    public void setDeliveryMethodNumber(String deliveryMethodNumber) {
        this.deliveryMethodNumber = deliveryMethodNumber;
    }

    public String getDeliveryMethodName() {
        return deliveryMethodName;
    }

    public void setDeliveryMethodName(String deliveryMethodName) {
        this.deliveryMethodName = deliveryMethodName;
    }

    public String getDeliveryMethodPhone() {
        return deliveryMethodPhone;
    }

    public void setDeliveryMethodPhone(String deliveryMethodPhone) {
        this.deliveryMethodPhone = deliveryMethodPhone;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
