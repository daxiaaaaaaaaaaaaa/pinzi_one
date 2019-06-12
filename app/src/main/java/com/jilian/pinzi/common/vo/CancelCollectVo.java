package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class CancelCollectVo extends BaseVo {
    private static final long serialVersionUID = 8527597680292273688L;
    private String cId;//收藏Id

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }
}
