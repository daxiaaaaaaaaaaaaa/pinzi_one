package com.jilian.pinzi.common.dto;

import java.io.Serializable;
import java.util.List;

public class GoodsEvaluateDto implements Serializable {
    private static final long serialVersionUID = 3223986311493551603L;
    private int badCount;//true number差评

    private int mediumCount;// true number    中评


    private int goodCount;//true number 好评


    List<GoodsEvaluateItem> list;// true array[object]

    private int havePictures;// true number有图

    public int getBadCount() {
        return badCount;
    }

    public void setBadCount(int badCount) {
        this.badCount = badCount;
    }

    public int getMediumCount() {
        return mediumCount;
    }

    public void setMediumCount(int mediumCount) {
        this.mediumCount = mediumCount;
    }

    public int getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(int goodCount) {
        this.goodCount = goodCount;
    }

    public List<GoodsEvaluateItem> getList() {
        return list;
    }

    public void setList(List<GoodsEvaluateItem> list) {
        this.list = list;
    }

    public int getHavePictures() {
        return havePictures;
    }

    public void setHavePictures(int havePictures) {
        this.havePictures = havePictures;
    }
}
