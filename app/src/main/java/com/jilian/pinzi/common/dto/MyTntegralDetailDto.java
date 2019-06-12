package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class MyTntegralDetailDto implements Serializable {

    private String id;//true number
    private String content;// true string  抽奖中心说明
    private String  scoreContent;// true string   积分说明

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getScoreContent() {
        return scoreContent;
    }

    public void setScoreContent(String scoreContent) {
        this.scoreContent = scoreContent;
    }
}
