package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class CommentTitleDto implements Serializable {
    private static final long serialVersionUID = 8305526285840402533L;
    private String title;//评价的标题
    private String count;//评价的数量
    private boolean isSelected;

    public CommentTitleDto(String title, String count, boolean isSelected) {
        this.title = title;
        this.count = count;
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
