package com.jilian.pinzi.common.dto;

import java.io.Serializable;

/**
 * 商铺商品
 */
public class ShopGoodsDto implements Serializable {
    private static final long serialVersionUID = 4231643191856174487L;
    private int id;
    private String file;

    private String name;

    private float terminalBuy;

    private float channelBuy;

    private float personBuy;

    private float franchiseeBuy;
    private float score;
    private String goodsImg;
    private int quantity;//数量


    private int isEarnest;//    true     string  是否需要定金（0.否 1.是）

    private double earnestRate;//     true     string    定金比例（单位%）

    public int getIsEarnest() {
        return isEarnest;
    }

    public void setIsEarnest(int isEarnest) {
        this.isEarnest = isEarnest;
    }

    public double getEarnestRate() {
        return earnestRate;
    }

    public void setEarnestRate(double earnestRate) {
        this.earnestRate = earnestRate;
    }

    private String freight;// true string  运费

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public float getTerminalBuy() {
        return terminalBuy;
    }

    public void setTerminalBuy(float terminalBuy) {
        this.terminalBuy = terminalBuy;
    }

    public float getChannelBuy() {
        return channelBuy;
    }

    public void setChannelBuy(float channelBuy) {
        this.channelBuy = channelBuy;
    }

    public float getPersonBuy() {
        return personBuy;
    }

    public void setPersonBuy(float personBuy) {
        this.personBuy = personBuy;
    }

    public float getFranchiseeBuy() {
        return franchiseeBuy;
    }

    public void setFranchiseeBuy(float franchiseeBuy) {
        this.franchiseeBuy = franchiseeBuy;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }
}
