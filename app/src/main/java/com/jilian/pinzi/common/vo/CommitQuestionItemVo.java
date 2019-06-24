package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class CommitQuestionItemVo extends BaseVo {

    private String topicId;//   true number 题目Id
    private String optionId;// true string     题目答案（对应选项Id）(多选用逗号隔开)

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }
}
