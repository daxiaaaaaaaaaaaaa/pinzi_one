package com.jilian.pinzi.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.utils.SPUtil;

/**
 * @author ningpan
 * @since 2018/10/30 16:46 <br>
 * description: 用户信息审核 界面 Activity
 */
public class UserCheckActivity extends BaseActivity {
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
        return R.layout.activity_user_check;
    }

    @Override
    public void initView() {
        setCenterTitle("审核", "#FFFFFF");
        setleftImage(R.drawable.image_back, true, null);
        setTitleBg(R.color.color_black);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
