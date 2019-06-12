package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class FriendCircleListDetailDto implements Serializable {
    private static final long serialVersionUID = -9055643970553449905L;
    private FriendCircleListDto FriendCircle;

    public FriendCircleListDto getFriendCircle() {
        return FriendCircle;
    }

    public void setFriendCircle(FriendCircleListDto friendCircle) {
        FriendCircle = friendCircle;
    }
}
