package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class NewsTypeDto implements Serializable {
    private String id;//商品的分类Id
    private String name;//": "酒类",名称
    private String type;//类型
    private boolean isSelected;

    public NewsTypeDto(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
