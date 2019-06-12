package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class HotWordListDto implements Serializable {
    private String id;//": 1,
    private String word;//": "111",
    private String number;//": 51,
    private long createDate;//": 1541677627000

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
