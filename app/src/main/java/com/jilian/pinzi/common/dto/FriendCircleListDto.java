package com.jilian.pinzi.common.dto;

import java.io.Serializable;
import java.util.List;

public class FriendCircleListDto implements Serializable {
    private static final long serialVersionUID = -9055643970973449905L;
    private String id;//":63,
    private String uId;//":23,
    private String content;//":"Test 竖条条",
    private String imgUrl;//":"http://120.79.210.114:9006/donghui_app/IMG/2019010212090720190102120907[0].jpg",
    private String createDate;//":1546402148000,
    private String status;//";//:null,
    private String name;//":"well",
    private String headImg;//";//:"http://120.79.210.114:9006/donghui_app/IMG/2018121003350620181210153506[0].jpg",
    private String photoSize;//":"0.562701",
    private String day;//按日期分组
    private boolean flag;
    private List<FriendlLikeDto> tblLikeList;// true array[object];//  点赞信息
    private List<FriendTblCommentDto>  tblCommentList;// true array[object]朋友圈评论
//    private JSONArray tblCommentList;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    private List<FriendCircleListDto> datas;

    public List<FriendCircleListDto> getDatas() {
        return datas;
    }

    public void setDatas(List<FriendCircleListDto> datas) {
        this.datas = datas;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getPhotoSize() {
        return photoSize;
    }

    public void setPhotoSize(String photoSize) {
        this.photoSize = photoSize;
    }

    public List<FriendlLikeDto> getTblLikeList() {
        return tblLikeList;
    }

    public void setTblLikeList(List<FriendlLikeDto> tblLikeList) {
        this.tblLikeList = tblLikeList;
    }

    public List<FriendTblCommentDto> getTblCommentList() {
        return tblCommentList;
    }

    public void setTblCommentList(List<FriendTblCommentDto> tblCommentList) {
        this.tblCommentList = tblCommentList;
    }
}
