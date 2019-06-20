package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class InformationtTypeDto implements Serializable {


    private String id;//     true    number   资讯分类Id
    private String name;//   true     string  分类名称
    private long createDate;//  true   number 创建时间
    private boolean isSelected;//是否选中

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
