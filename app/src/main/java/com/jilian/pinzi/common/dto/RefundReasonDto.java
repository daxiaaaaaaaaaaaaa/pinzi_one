package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class RefundReasonDto implements Serializable {
    private static final long serialVersionUID = 1259024749199110914L;
    private String id;//": 1,
        private String content;//": "质量问题",

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
}
