package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class ShipperDto implements Serializable {
    private static final long serialVersionUID = -9113312585270666657L;
    private String uId;//发货人ID
    private String name;//发货人姓名
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
