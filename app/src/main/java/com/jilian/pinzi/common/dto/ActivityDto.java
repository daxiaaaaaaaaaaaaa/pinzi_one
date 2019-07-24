package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class ActivityDto implements Serializable {

   private String imgUrl;//    true     string      图标
   private long endDate;//   true     string    活动结束时间
   private String id;//     true     number   活动Id
   private int alreadyPeopleNum;//   true     number      已报名人数
   private String title;//     true     string  标题
   private int peopleNum;//     true     number     报名名额
   private long startDate;//     true string   活动开始时间
   private int isVote;//  true     number     是否可以投票（0.是 1.否）
   private String descs;//    true     string     详情
   private long createDate;//      true     number      创建时间
   private int applyActivityId;// true  number     =0未报名 >0报名
   private String acitvityId;//活动ID

    public String getAcitvityId() {
        return acitvityId;
    }

    public void setAcitvityId(String acitvityId) {
        this.acitvityId = acitvityId;
    }

    public int getIsVote() {
        return isVote;
    }

    public void setIsVote(int isVote) {
        this.isVote = isVote;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public int getApplyActivityId() {
        return applyActivityId;
    }

    public void setApplyActivityId(int applyActivityId) {
        this.applyActivityId = applyActivityId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAlreadyPeopleNum() {
        return alreadyPeopleNum;
    }

    public void setAlreadyPeopleNum(int alreadyPeopleNum) {
        this.alreadyPeopleNum = alreadyPeopleNum;
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(int peopleNum) {
        this.peopleNum = peopleNum;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }
}
