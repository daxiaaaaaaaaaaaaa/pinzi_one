package com.jilian.pinzi.common.dto;

import java.io.Serializable;
import java.util.List;

public class DiscountConpouDto implements Serializable {
    private static final long serialVersionUID = 5679395760737655037L;
    private List<SelectCardDto> usableList;//可用的优惠券
    private List<SelectCardDto> disabledList;//不可用的优惠券
    private int  usableCount;//可用优惠券张数

    public List<SelectCardDto> getUsableList() {
        return usableList;
    }

    public void setUsableList(List<SelectCardDto> usableList) {
        this.usableList = usableList;
    }

    public List<SelectCardDto> getDisabledList() {
        return disabledList;
    }

    public void setDisabledList(List<SelectCardDto> disabledList) {
        this.disabledList = disabledList;
    }

    public int getUsableCount() {
        return usableCount;
    }

    public void setUsableCount(int usableCount) {
        this.usableCount = usableCount;
    }
}
