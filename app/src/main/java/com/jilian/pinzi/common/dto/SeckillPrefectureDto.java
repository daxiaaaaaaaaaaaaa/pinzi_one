package com.jilian.pinzi.common.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 店铺展示
 */
public class SeckillPrefectureDto implements Serializable {
    private static final long serialVersionUID = 1698496904739118593L;
    private TblKillTimeDto tblKillTime;
    private List<TimeKillGoodsDto> timeKillGoods;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public TblKillTimeDto getTblKillTime() {
        return tblKillTime;
    }

    public void setTblKillTime(TblKillTimeDto tblKillTime) {
        this.tblKillTime = tblKillTime;
    }

    public List<TimeKillGoodsDto> getTimeKillGoods() {
        return timeKillGoods;
    }

    public void setTimeKillGoods(List<TimeKillGoodsDto> timeKillGoods) {
        this.timeKillGoods = timeKillGoods;
    }
}
