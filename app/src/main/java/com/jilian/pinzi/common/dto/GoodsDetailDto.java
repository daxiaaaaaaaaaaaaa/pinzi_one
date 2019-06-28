package com.jilian.pinzi.common.dto;

import java.io.Serializable;

/**
 * 商品详情
 */
public class GoodsDetailDto implements Serializable {
    private static final long serialVersionUID = 2522457479916133558L;
    private String introduce;//   商品介绍
    private int collectId;// true number    收藏Id(0为未收藏)
    private double channelBuy;// true number     渠道购买价格
    private String parameterName;// true string 参数名称
    private double personBuy;// true number    个人购买价格
    private int storeId;// true number   商家Id ID  = 0   就是自营的店铺
    private String repertory;// true number   库存
    private String content;// true string    图文详情
    private String score;// true number   积分购买
    private String file;// true string    库存
    private String getCommission;// true number是否返佣金（0.否 1.是）
    private String name;// true string    商品名称
    private String evaluateImg;// true string  评论图片
    private String storeLogo;// true string       店铺Logo
    private String userImgUrl;// true string  评论用户头像
    private String storeName;// true string     店铺名称
    private String id;// true number    商品Id
    private double franchiseeBuy;// true number      总经销商购买价格
    private String startTime;// true string           秒杀开始时间
    private String endTime;// true string 秒杀结束时间
    private double seckillPrice;// true string         秒杀价格
    private String residueQuantity;// true string    秒杀剩余数量
    private String evaluateCount;// true string           总评论数
    private double topScore;// true string 最高抵扣积分
    private String goodsStandard;// true string   商品规格
    private String freight;// true string  运费
    private double terminalBuy;//终端购买价格
    private String parameterContent;//参数内容
    private double storeGrade;//店铺评分

    private int isEarnest;//    true     string  是否需要定金（0.否 1.是）

    private double earnestRate;//     true     string    定金比例（单位%）



    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public String getParameterContent() {
        return parameterContent;
    }

    public void setParameterContent(String parameterContent) {
        this.parameterContent = parameterContent;
    }

    public double getChannelBuy() {
        return channelBuy;
    }

    public void setChannelBuy(double channelBuy) {
        this.channelBuy = channelBuy;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
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

    public double getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(double seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    public double getTerminalBuy() {
        return terminalBuy;
    }

    public void setTerminalBuy(double terminalBuy) {
        this.terminalBuy = terminalBuy;
    }

    public double getStoreGrade() {
        return storeGrade;
    }

    public void setStoreGrade(double storeGrade) {
        this.storeGrade = storeGrade;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getCollectId() {
        return collectId;
    }

    public void setCollectId(int collectId) {
        this.collectId = collectId;
    }




    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getRepertory() {
        return repertory;
    }

    public void setRepertory(String repertory) {
        this.repertory = repertory;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public String getGetCommission() {
        return getCommission;
    }

    public void setGetCommission(String getCommission) {
        this.getCommission = getCommission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvaluateImg() {
        return evaluateImg;
    }

    public void setEvaluateImg(String evaluateImg) {
        this.evaluateImg = evaluateImg;
    }

    public String getStoreLogo() {
        return storeLogo;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
    }

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
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



    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }



    public String getResidueQuantity() {
        return residueQuantity;
    }

    public void setResidueQuantity(String residueQuantity) {
        this.residueQuantity = residueQuantity;
    }

    public String getEvaluateCount() {
        return evaluateCount;
    }

    public void setEvaluateCount(String evaluateCount) {
        this.evaluateCount = evaluateCount;
    }

    public double getTopScore() {
        return topScore;
    }

    public void setTopScore(double topScore) {
        this.topScore = topScore;
    }

    public String getGoodsStandard() {
        return goodsStandard;
    }

    public void setGoodsStandard(String goodsStandard) {
        this.goodsStandard = goodsStandard;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }
}
