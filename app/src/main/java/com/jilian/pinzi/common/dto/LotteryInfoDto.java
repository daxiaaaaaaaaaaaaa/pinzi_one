package com.jilian.pinzi.common.dto;

import java.io.Serializable;

/**
 * @author ningpan
 * @since 2018/12/6 20:42 <br>
 * description: 奖品数据 Dto
 */
public class LotteryInfoDto implements Serializable {

    /**
     * id : 1
     * name : 100万
     * rate : 0.01
     * createDate : 1542189653000
     */

    private String id;
    private String name;
    private double rate;
    private long createDate;
    private double freight;//运费


    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
