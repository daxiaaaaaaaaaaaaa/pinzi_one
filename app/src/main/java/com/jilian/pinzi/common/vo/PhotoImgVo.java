package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.BaseVo;

public class PhotoImgVo extends BaseVo {
    private Integer type;//类型 1图片 2GIF 3视屏

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
