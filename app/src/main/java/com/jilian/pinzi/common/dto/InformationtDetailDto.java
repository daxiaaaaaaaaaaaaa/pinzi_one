package com.jilian.pinzi.common.dto;

import java.io.Serializable;
import java.util.List;

public class InformationtDetailDto implements Serializable {


    private String id;//   true     number  资讯Id
    private String title;//   true   string 标题
    private String imgUrl;//  true   string  图标
    private String detail;//     true     string    简介
    private long createDate;//      true   number        创建时间
    private int collectId;//  true   number 0.未收藏 >0收藏Id
    private List<CommentListDto> commentList;//评论列表

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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public int getCollectId() {
        return collectId;
    }

    public void setCollectId(int collectId) {
        this.collectId = collectId;
    }

    public List<CommentListDto> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentListDto> commentList) {
        this.commentList = commentList;
    }
}
