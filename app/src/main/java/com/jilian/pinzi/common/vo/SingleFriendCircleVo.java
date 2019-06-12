package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

/**
 * 刷新单条朋友圈参数
 */
public class SingleFriendCircleVo extends BaseVo {

    private String uId;//true string  用户ID
    private String id;//true string   朋友圈ID

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
