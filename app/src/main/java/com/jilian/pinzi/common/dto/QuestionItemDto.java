package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class QuestionItemDto implements Serializable {

    private String id;//true     number 选项Id
    private String optionContent;//     true   string  选项内容
    private String topicId;//  true  number  题目Id
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

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }
}
