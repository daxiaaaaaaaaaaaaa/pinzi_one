package com.jilian.pinzi.common.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 店铺展示
 */
public class SeckillPrefectureDto implements Serializable {
    private static final long serialVersionUID = 1698496904739118593L;
    private TblKillTimeDto tblKillTime;
    private String id;//": 112,
    private String name;//": "（秒杀）测试",
    private String file;//": "http://39.108.14.94:9011/donghui_oa/IMG/20190722112700.jpg,http://39.108.14.94:9011/donghui_oa/IMG/20190722112706.jpg",
    private String cutFile;//": "http://39.108.14.94:9011/donghui_oa/IMG/20190722112700.jpg",
    private double personBuy;//": 200.0,
    private double terminalBuy;//": 300.0,
    private double channelBuy;//": 400.0,
    private double franchiseeBuy;//": 496.0,
    private double seckillPrice;//": 99.0,
    private int seckillQuantity;//": 100,
    private int residueQuantity;//": 100,

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public TblKillTimeDto getTblKillTime() {
        return tblKillTime;
    }

    public void setTblKillTime(TblKillTimeDto tblKillTime) {
        this.tblKillTime = tblKillTime;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getCutFile() {
        return cutFile;
    }

    public void setCutFile(String cutFile) {
        this.cutFile = cutFile;
    }

    public double getPersonBuy() {
        return personBuy;
    }

    public void setPersonBuy(double personBuy) {
        this.personBuy = personBuy;
    }

    public double getTerminalBuy() {
        return terminalBuy;
    }

    public void setTerminalBuy(double terminalBuy) {
        this.terminalBuy = terminalBuy;
    }

    public double getChannelBuy() {
        return channelBuy;
    }

    public void setChannelBuy(double channelBuy) {
        this.channelBuy = channelBuy;
    }

    public double getFranchiseeBuy() {
        return franchiseeBuy;
    }

    public void setFranchiseeBuy(double franchiseeBuy) {
        this.franchiseeBuy = franchiseeBuy;
    }

    public double getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(double seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    public double getSeckillQuantity() {
        return seckillQuantity;
    }

    public void setSeckillQuantity(int seckillQuantity) {
        this.seckillQuantity = seckillQuantity;
    }

    public int getResidueQuantity() {
        return residueQuantity;
    }

    public void setResidueQuantity(int residueQuantity) {
        this.residueQuantity = residueQuantity;
    }
}
