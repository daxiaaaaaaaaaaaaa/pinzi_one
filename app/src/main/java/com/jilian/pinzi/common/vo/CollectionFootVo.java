package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class CollectionFootVo extends BaseVo {


    private String uId;//truestring用户ID


    private String type;//truestring类型（1.收藏商品 2.收藏店铺）


    private String classify;//truestring  1.收藏 2.足迹


    private int startNum;//truestring当前页数


    private int pageSize;//truestring每页条数

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
