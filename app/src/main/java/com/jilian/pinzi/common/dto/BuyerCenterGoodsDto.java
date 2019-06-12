package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class BuyerCenterGoodsDto implements Serializable {
    private static final long serialVersionUID = -8083455441105484577L;
    private String score;//": 50,
    private String file;//": "http://g.hiphotos.baidu.com/image/h%3D300/sign=6f4318466e2762d09f3ea2bf90ed0849/5243fbf2b211931376d158d568380cd790238dc1.jpg,http://e.hiphotos.baidu.com/image/h%3D300/sign=e81839633efa828bce239be3cd1f41cd/0eb30f2442a7d9339cb35915a04bd11373f001b8.jpg",
    private String name;//": "牛栏山 二锅头",
    private String storeName;//": "自营",
    private double terminalBuy;//": 15,
    private double channelBuy;//": 10,
    private String id;//": 1,
    private double personBuy;//": 20,
    private double franchiseeBuy;//": 8

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public double getPersonBuy() {
        return personBuy;
    }

    public void setPersonBuy(double personBuy) {
        this.personBuy = personBuy;
    }

    public double getFranchiseeBuy() {
        return franchiseeBuy;
    }

    public void setFranchiseeBuy(double franchiseeBuy) {
        this.franchiseeBuy = franchiseeBuy;
    }
}
