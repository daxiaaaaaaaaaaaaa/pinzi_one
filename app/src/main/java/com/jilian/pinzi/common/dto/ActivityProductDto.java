package com.jilian.pinzi.common.dto;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * 作品
 */
public class ActivityProductDto implements Serializable {

    private String headImg;//  true     string  头像
    private int voteNum;//   true  number     投票数
    private String pathUrl;//    true    string             图片路径
    private String id;//  true   number        作品Id
    private String userName;//   true     string     用户名
    private String content;//   true     string      内容
    private int isVote;//      true    number   是否已投票（0.为投票 >0.已投票）
    private long createDate;//      true     string    创建时间
    private String video;//     true  string     视频路径
    private Bitmap bitmap;
    private String uId;//用户 Id
    private String activityId;//活动ID
    private int isCheck;//是否通过审核
    private int isCanVote;//0 不能投票 1 能投票

    public int getIsCanVote() {
        return isCanVote;
    }

    public void setIsCanVote(int isCanVote) {
        this.isCanVote = isCanVote;
    }

    public int getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public int getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(int voteNum) {
        this.voteNum = voteNum;
    }

    public String getPathUrl() {
        return pathUrl;
    }

    public void setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIsVote() {
        return isVote;
    }

    public void setIsVote(int isVote) {
        this.isVote = isVote;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
