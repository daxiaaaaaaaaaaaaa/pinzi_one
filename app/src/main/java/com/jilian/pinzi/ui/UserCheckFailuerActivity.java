package com.jilian.pinzi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.utils.SPUtil;

/**
 * @author ningpan
 * @since 2018/10/30 16:46 <br>
 * description: 审核失败界面
 */
public class UserCheckFailuerActivity extends BaseActivity {
    private TextView tvOk;
    private TextView tvBack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PinziApplication.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PinziApplication.removeActivity(this);
        SPUtil.clearData(Constant.SP_VALUE.SP);
    }

    @Override
    protected void createViewModel() {

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_user_check_failuer;
    }

    @Override
    public void initView() {
        setNormalTitle("审核", v -> finish());
        tvOk = (TextView) findViewById(R.id.tv_ok);
        tvBack = (TextView) findViewById(R.id.tv_back);

    }

    @Override
    public void initData() {


    }

    @Override
    public void initListener() {
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 继续完善信息
                startActivity(new Intent(UserCheckFailuerActivity.this, PerfectInformationActivity.class));
            }
        });
    }
}
