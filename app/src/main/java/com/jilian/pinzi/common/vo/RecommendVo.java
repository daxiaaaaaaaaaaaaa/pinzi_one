package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class RecommendVo extends BaseVo {
    private String recommend;//true string 1 商品推荐（1.人气推荐）

    private int startNum;//true string

    private int pageSize;//true string

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
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
