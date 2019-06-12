package com.jilian.pinzi.common.dto;

import com.jilian.pinzi.base.BaseDto;

public class OrderDto extends BaseDto {
    private static final long serialVersionUID = -2702945177230697335L;
    private Integer status;// 1 待付款 2 待收货 3 已完成 4 已经评价  5 已经取消

    public OrderDto(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
