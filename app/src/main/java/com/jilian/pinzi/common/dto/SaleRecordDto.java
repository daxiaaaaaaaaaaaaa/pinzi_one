package com.jilian.pinzi.common.dto;

import java.io.Serializable;

/**
 *
 */
public class SaleRecordDto implements Serializable {
    private static final long serialVersionUID = -6487204987590173048L;

    /**
     * createDate : 1546821849000
     * file : http://g.hiphotos.baidu.com/image/h%3D300/sign=6f4318466e2762d09f3ea2bf90ed0849/5243fbf2b211931376d158d568380cd790238dc1.jpg,http://e.hiphotos.baidu.com/image/h%3D300/sign=e81839633efa828bce239be3cd1f41cd/0eb30f2442a7d9339cb35915a04bd11373f001b8.jpg
     * goodsPrice : 1000
     * id : 45
     * name : 牛栏山 红酒
     * orderNo : 2019010708390810747
     * payDate : 1546821551000
     * quantity : 1
     * serviceNo : 2019010708440838469
     * status : 1
     */

    private long createDate;
    private String file;
    private double goodsPrice;
    private int id;
    private String name;
    private String orderNo;
    private long payDate;
    private int quantity;
    private String serviceNo;
    private int status;

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public long getPayDate() {
        return payDate;
    }

    public void setPayDate(long payDate) {
        this.payDate = payDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
