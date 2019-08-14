package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class HotWordSelectDto implements Serializable {
    private static final long serialVersionUID = -1597533878730935905L;
    private String id;//": 1,
              private String storeId;//": 1,
              private String name;//": "牛栏山 二锅头",
              private String goodsType;//": 1,
              private String goodsStandard;//": "500ml*12",
              private String introduce;//": "The introduction Lorem ipsum dolor sit amet, Consectetur adipiscing elit.aenean euismod bibendum laoreet. Proin gravida dolor sit amet lacus lacsan et viverra justo commodo.proin sodales pulvinar sic tempor.sociis natoque penatibus et Magnis dis parturient montes.",
              private String file;//": "http://g.hiphotos.baidu.com/image/h%3D300/sign=6f4318466e2762d09f3ea2bf90ed0849/5243fbf2b211931376d158d568380cd790238dc1.jpg,http://e.hiphotos.baidu.com/image/h%3D300/sign=e81839633efa828bce239be3cd1f41cd/0eb30f2442a7d9339cb35915a04bd11373f001b8.jpg",
              private String repertory;//": 99,
              private double personBuy;//": 20,
              private double terminalBuy;//": 15,
              private double channelBuy;//": 10,
              private double franchiseeBuy;//": 8,
              private String parameterName;//": "商品编号,酒精度,省份,容量",
              private String parameterContent;//": "10001,45°,湖南省,500ml*12",
              private String content;//": "
        private String giveScore;//": 5,
        private String getCommission;//": 0,
        private double scoreBuy;//": 50,
        private String topScore;//": 0,
        private String isShow;//": 0,
        private String recommend;//": 0,
        private String isCheck;//": 1,
        private String failReason;//": null,
        private String isDelete;//": 0,
        private String isRecommend;//": 0,
        private String freight;//": "0.00",
        private String createDate;//": 1541669914000
        private int isSeckill;//秒杀商品
        private double seckillPrice;//秒杀价格

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getIsSeckill() {
        return isSeckill;
    }

    public void setIsSeckill(int isSeckill) {
        this.isSeckill = isSeckill;
    }

    public double getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(double seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsStandard() {
        return goodsStandard;
    }

    public void setGoodsStandard(String goodsStandard) {
        this.goodsStandard = goodsStandard;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getRepertory() {
        return repertory;
    }

    public void setRepertory(String repertory) {
        this.repertory = repertory;
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

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterContent() {
        return parameterContent;
    }

    public void setParameterContent(String parameterContent) {
        this.parameterContent = parameterContent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGiveScore() {
        return giveScore;
    }

    public void setGiveScore(String giveScore) {
        this.giveScore = giveScore;
    }

    public String getGetCommission() {
        return getCommission;
    }

    public void setGetCommission(String getCommission) {
        this.getCommission = getCommission;
    }

    public double getScoreBuy() {
        return scoreBuy;
    }

    public void setScoreBuy(double scoreBuy) {
        this.scoreBuy = scoreBuy;
    }

    public String getTopScore() {
        return topScore;
    }

    public void setTopScore(String topScore) {
        this.topScore = topScore;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
