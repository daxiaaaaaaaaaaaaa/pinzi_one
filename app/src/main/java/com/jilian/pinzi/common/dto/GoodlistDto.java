package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class GoodlistDto implements Serializable {

    private String id;//商品ID numbertrue


    private String storeId;//   商家Id(0.平台)numbertrue


    private String storeName;//truestring


    private String name;//truestring商品名称

    private String goodsType;//


    private String number;//


    private String goodsStandard;//truestring商品规格


    private String introduce;//true商品介绍string


    private String file;//truestring 图片/视频


    private String repertory;//truenumber库存


    private String personBuy;//truenumber个人购买价格


    private String terminalBuy;//truenumber终端购买价格


    private String channelBuy;//true渠道购买价格number


    private String franchiseeBuy;//truenumber总经销商购买价格


    private String parameterName;//truestring参数名称


    private String parameterContent;//truestring参数内容


    private String content;//truestring商品详情


    private String giveScore;//truenumber赠送积分


    private String getCommission;//truenumber是否返佣金（0.否 1.是  ）


    private double scoreBuy;//truenumber积分购买


    private String topScore;//truenumber最高使用积分抵扣


    private String isShow;//truenumber   是否上下架（0.上架 1.下架）


    private String recommend;//truenumber    商品推荐（1.人气推荐）


    private String isCheck;//truenumber 是否审核（0.待审核 1.审核通过 2.审核失败）


    private String failReason;//truestring审核失败原因


    private String isDelete;//truenumber    是否删除（0.否 1.是）
    private String collectId;

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getPersonBuy() {
        return personBuy;
    }

    public void setPersonBuy(String personBuy) {
        this.personBuy = personBuy;
    }

    public String getTerminalBuy() {
        return terminalBuy;
    }

    public void setTerminalBuy(String terminalBuy) {
        this.terminalBuy = terminalBuy;
    }

    public String getChannelBuy() {
        return channelBuy;
    }

    public void setChannelBuy(String channelBuy) {
        this.channelBuy = channelBuy;
    }

    public String getFranchiseeBuy() {
        return franchiseeBuy;
    }

    public void setFranchiseeBuy(String franchiseeBuy) {
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

    public String getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(String salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    private String isRecommend;//truenumber    是否推荐（0.否 1.是）


    private String freight;//truestring运费


    private String createDate;//truenumber创建时间


    private String salesVolume;//truestring


    private String goodsTypeName;//truestring




}
