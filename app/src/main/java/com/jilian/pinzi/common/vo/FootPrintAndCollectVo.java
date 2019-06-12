package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

/***
 * 我的收藏  我的足迹
 */
public class FootPrintAndCollectVo extends BaseVo {

    private String uId;//   用户ID

    private String type;//    类型（1.收藏商品 2.收藏店铺）

    private String classify;//    1.收藏 2.足迹

    private String  startNum;// 当前页数

    private int  pageSize;//每页个数

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

    public String getStartNum() {
        return startNum;
    }

    public void setStartNum(String startNum) {
        this.startNum = startNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
