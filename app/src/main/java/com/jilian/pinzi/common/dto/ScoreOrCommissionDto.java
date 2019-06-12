package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class ScoreOrCommissionDto implements Serializable {
    private String balanceNum;//": 100,//余额
    private String deductionMoney;//": 0.1,//积分抵扣
    private String scoreNum;//": 35,//总积分
    private String commisionNum;//": 0总佣金

    public String getBalanceNum() {
        return balanceNum;
    }

    public void setBalanceNum(String balanceNum) {
        this.balanceNum = balanceNum;
    }

    public String getDeductionMoney() {
        return deductionMoney;
    }

    public void setDeductionMoney(String deductionMoney) {
        this.deductionMoney = deductionMoney;
    }

    public String getScoreNum() {
        return scoreNum;
    }

    public void setScoreNum(String scoreNum) {
        this.scoreNum = scoreNum;
    }

    public String getCommisionNum() {
        return commisionNum;
    }

    public void setCommisionNum(String commisionNum) {
        this.commisionNum = commisionNum;
    }
}
