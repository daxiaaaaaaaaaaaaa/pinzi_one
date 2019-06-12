package com.jilian.pinzi.common.dto;

import java.io.Serializable;

/**
 * 秒杀时间段
 */
public class TblKillTimeDto implements Serializable {
    private static final long serialVersionUID = 7148754028572709509L;
    private String id;//;// 1,
    private String sessionName;// "04:00", 时段名称
    private long startTime;// 0, 开始时间
    private long endTime;// 14400000, 结束时间
    private Integer isUse;// 0, 是否启用（0.启用 1.禁用）
    private Integer isDelete;// 0,
    private long createDate;// 1541238387000
    private long newTime;//下一场开始时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

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

    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getNewTime() {
        return newTime;
    }

    public void setNewTime(long newTime) {
        this.newTime = newTime;
    }
}
