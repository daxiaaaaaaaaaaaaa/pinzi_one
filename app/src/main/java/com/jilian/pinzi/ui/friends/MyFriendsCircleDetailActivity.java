package com.jilian.pinzi.ui.friends;

import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.common.dto.FriendCircleListDto;

public class MyFriendsCircleDetailActivity extends BaseActivity {
    private FriendCircleListDto data;
    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_minefriendscircledetail;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        data= (FriendCircleListDto) getIntent().getSerializableExtra("data");
    }

    @Override
    public void initListener() {

    }
}
