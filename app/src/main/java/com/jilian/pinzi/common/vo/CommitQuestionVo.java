package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

import java.util.List;

public class CommitQuestionVo extends BaseVo {
    private String uId;
    private String questionId;
    private List<CommitQuestionItemVo> results;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public List<CommitQuestionItemVo> getResults() {
        return results;
    }

    public void setResults(List<CommitQuestionItemVo> results) {
        this.results = results;
    }
}
