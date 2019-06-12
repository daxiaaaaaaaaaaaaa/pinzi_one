package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class AddCourierInfoVo extends BaseVo {


    private String id;//
    private String courierName;//
    private String courierNo;//

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public String getCourierNo() {
        return courierNo;
    }

    public void setCourierNo(String courierNo) {
        this.courierNo = courierNo;
    }
}
