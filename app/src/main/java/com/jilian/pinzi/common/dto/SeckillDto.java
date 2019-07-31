package com.jilian.pinzi.common.dto;

import java.io.Serializable;
import java.util.List;

public class SeckillDto implements Serializable {

    private long startTime;//": 7200000,
    private long endTime;//": 57000000,
    private List<SeckillPrefectureDto> timeKillGoods;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public List<SeckillPrefectureDto> getTimeKillGoods() {
        return timeKillGoods;
    }

    public void setTimeKillGoods(List<SeckillPrefectureDto> timeKillGoods) {
        this.timeKillGoods = timeKillGoods;
    }
}
