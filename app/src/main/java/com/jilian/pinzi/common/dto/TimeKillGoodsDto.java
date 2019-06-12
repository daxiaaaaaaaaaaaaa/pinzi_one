package com.jilian.pinzi.common.dto;

import java.io.Serializable;

/**
 * 秒杀商品
 */
public class TimeKillGoodsDto implements Serializable {

    private static final long serialVersionUID = 507520174951409869L;
    private String id;// 2,
    private String name;// "牛栏山 葡萄酒",
    private String file;// "http://g.hiphotos.baidu.com/image/h%3D300/sign=6f4318466e2762d09f3ea2bf90ed0849/5243fbf2b211931376d158d568380cd790238dc1.jpg,http://e.hiphotos.baidu.com/image/h%3D300/sign=e81839633efa828bce239be3cd1f41cd/0eb30f2442a7d9339cb35915a04bd11373f001b8.jpg",
    private double personBuy;// 300,
    private double terminalBuy;// 200,
    private double channelBuy;// 150,
    private double franchiseeBuy;// 100,
    private double seckillPrice;// 200,
    private String seckillQuantity;// 50,
    private String residueQuantity;// 50

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getPersonBuy() {
        return personBuy;
    }

    public void setPersonBuy(double personBuy) {
        this.personBuy = personBuy;
    }

    public double getTerminalBuy() {
        return terminalBuy;
    }

    public void setTerminalBuy(double terminalBuy) {
        this.terminalBuy = terminalBuy;
    }

    public double getChannelBuy() {
        return channelBuy;
    }

    public void setChannelBuy(double channelBuy) {
        this.channelBuy = channelBuy;
    }

    public double getFranchiseeBuy() {
        return franchiseeBuy;
    }

    public void setFranchiseeBuy(double franchiseeBuy) {
        this.franchiseeBuy = franchiseeBuy;
    }

    public double getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(double seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    public String getSeckillQuantity() {
        return seckillQuantity;
    }

    public void setSeckillQuantity(String seckillQuantity) {
        this.seckillQuantity = seckillQuantity;
    }

    public String getResidueQuantity() {
        return residueQuantity;
    }

    public void setResidueQuantity(String residueQuantity) {
        this.residueQuantity = residueQuantity;
    }
}
