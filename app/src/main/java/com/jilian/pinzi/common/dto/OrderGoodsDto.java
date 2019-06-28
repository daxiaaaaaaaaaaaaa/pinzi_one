package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class OrderGoodsDto implements Serializable {
    private static final long serialVersionUID = 2760877968650412817L;
    private String id;// true number    商品Id
    private String name;// true string    商品名称
    private double price;//价格
    private int count;//数量
    private String file;// true string    商品图片
    private String shopId;
    private String freight;//运费
    private String score;//积分
    private int classes;//商品类型
    private double topScore;// true string 最高抵扣积分
    private String earnest;//    定金 金额

    public String getEarnest() {
        return earnest;
    }

    public void setEarnest(String earnest) {
        this.earnest = earnest;
    }

    public double getTopScore() {
        return topScore;
    }

    public void setTopScore(double topScore) {
        this.topScore = topScore;
    }

    public int getClasses() {
        return classes;
    }

    public void setClasses(int classes) {
        this.classes = classes;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getFreight() {
        return freight==null?"0":freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
