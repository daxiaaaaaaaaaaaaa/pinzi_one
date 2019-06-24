package com.jilian.pinzi.common.dto;

import java.io.Serializable;
import java.util.List;

public class QuestionDetailDto implements Serializable {

    private String id;//   true     number   问卷Id
    private String title;//   true     number  问卷标题
    private String questionId;//  true     number    创建时间
    private int isRadio;// 1.单选 2.多选
    private String answerOption;
    private List<QuestionItemDto> options;//

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public int getIsRadio() {
        return isRadio;
    }

    public void setIsRadio(int isRadio) {
        this.isRadio = isRadio;
    }

    public String getAnswerOption() {
        return answerOption;
    }

    public void setAnswerOption(String answerOption) {
        this.answerOption = answerOption;
    }

    public List<QuestionItemDto> getOptions() {
        return options;
    }

    public void setOptions(List<QuestionItemDto> options) {
        this.options = options;
    }
}
