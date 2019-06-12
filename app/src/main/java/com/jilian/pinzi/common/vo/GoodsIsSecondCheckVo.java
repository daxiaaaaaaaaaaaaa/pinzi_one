package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class GoodsIsSecondCheckVo extends BaseVo {
    private Integer identity;//true number1.普通用户 2.终端 3.渠道 4.总经销商
    private String goods;//true string    商品Id(多个用逗号隔开)
    private String classes;//

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }
}
