package com.jilian.pinzi.common.dto;

import java.io.Serializable;

/**
 * @author ningpan
 * @since 2018/12/7 14:42 <br>
 * description: 中奖纪录
 */
public class LotteryRecordDto implements Serializable {


    /**
     * id : 8
     * uId : 1
     * content : 0.1元
     * createDate : 1542597799000
     */

    private int id;
    private int uId;
    private String content;
    private long createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUId() {
        return uId;
    }

    public void setUId(int uId) {
        this.uId = uId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
