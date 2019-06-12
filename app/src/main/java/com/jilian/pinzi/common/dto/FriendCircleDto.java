package com.jilian.pinzi.common.dto;

import java.io.Serializable;
import java.util.List;

public class FriendCircleDto implements Serializable {
    private static final long serialVersionUID = -9055643970973449905L;


    private List<FriendCircleListDto> list;
    public List<FriendCircleListDto> getList() {
        return list;
    }

    public void setList(List<FriendCircleListDto> list) {
        this.list = list;
    }
}
