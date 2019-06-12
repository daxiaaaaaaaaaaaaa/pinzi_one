package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

/**
 * ???
 *
 */
public class  FriendCircleLikeVo extends BaseVo {
    private static final long serialVersionUID = 7822879817639017142L;
    private String fcId;//true string 朋友圈ID
    private String uId;//true string用户ID

    public String getFcId() {
        return fcId;
    }

    public void setFcId(String fcId) {
        this.fcId = fcId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}
