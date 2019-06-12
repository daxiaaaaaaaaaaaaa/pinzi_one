package com.jilian.pinzi.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;

public class MyCenterActivity extends BaseActivity {
    private RelativeLayout rlMyInfo;
    private RelativeLayout rlUpdatePwd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PinziApplication.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PinziApplication.removeActivity(this);
    }
    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_mycenter;
    }

    @Override
    public void initView() {
        setNormalTitle("个人中心", v -> finish());
        rlMyInfo = (RelativeLayout) findViewById(R.id.rl_my_info);
        rlUpdatePwd = (RelativeLayout) findViewById(R.id.rl_update_pwd);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        rlMyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyCenterActivity.this,MyInfoActivity.class));
            }
        });
        rlUpdatePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyCenterActivity.this,UpdatePwdActivity.class));
            }
        });
    }
}
