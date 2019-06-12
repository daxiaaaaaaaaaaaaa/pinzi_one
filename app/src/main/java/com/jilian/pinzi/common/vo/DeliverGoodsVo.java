package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

/**
 * 发货入参
 */
public class DeliverGoodsVo extends BaseVo {
    private String orderId;//true number      订单Id

    private String courierNumber;//true string  快递单号

    private String courierCompany;//true string  快递公司ID

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setCourierNumber(String courierNumber) {
        this.courierNumber = courierNumber;
    }

    public void setCourierCompany(String courierCompany) {
        this.courierCompany = courierCompany;
    }
}
