package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

/**
 * 更新订单信息
 */
public class OrderStatusVo extends BaseVo {
    private static final long serialVersionUID = 8319045534960038810L;
    private Integer status;//true number1.取消订单 2.确认收货 3.删除订单
    private String  orderId;//    订单Id
    private int reason;//true number  取消原因（1.配送信息有误 2.发票信息有误 3.忘记使用优惠券、优币等 4.商品买错了 5.重复下单/误下单） 默认传0

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getReason() {
        return reason;
    }

    public void setReason(int reason) {
        this.reason = reason;
    }
}
