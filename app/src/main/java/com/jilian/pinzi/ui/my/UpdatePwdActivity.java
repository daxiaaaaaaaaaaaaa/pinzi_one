package com.jilian.pinzi.ui.my;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.ToastUitl;

/**
 * 修改密码
 */
public class UpdatePwdActivity extends BaseActivity {
    private MyViewModel viewModel;
    private EditText tvPwd;
    private EditText tvNewPwd;
    private EditText tvNewsPwd;

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
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_update_pwd;
    }

    @Override
    public void initView() {
        setNormalTitle("修改密码", v -> finish());
        tvPwd = (EditText) findViewById(R.id.tv_pwd);
        tvNewPwd = (EditText) findViewById(R.id.tv_new_pwd);
        tvNewsPwd = (EditText) findViewById(R.id.tv_news_pwd);

        setrightTitle("完成", "#FFFFFF", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(tvPwd.getText().toString())
                        ||TextUtils.isEmpty(tvNewPwd.getText().toString())
                        ||TextUtils.isEmpty(tvNewsPwd.getText().toString())){
                    return;
                }
                if(!tvNewPwd.getText().toString().equals(tvNewsPwd.getText().toString())){
                    ToastUitl.showImageToastFail("两次密码输入不一致");
                    return;
                }
                if(tvNewPwd.getText().toString().length()<6||tvNewsPwd.getText().toString().length()<6){
                    ToastUitl.showImageToastFail("密码长度大于等于6");
                    return;
                }
                if(tvNewPwd.getText().toString().equals(tvPwd.getText().toString())){
                    ToastUitl.showImageToastFail("新旧密码不能一致");
                    return;
                }
                updatePwd();
            }
        });


    }

    /**
     * 修改密码
     */
    private void updatePwd() {
        getLoadingDialog().showDialog();
        viewModel.updatePassword(tvPwd.getText().toString(),tvNewPwd.getText().toString(),getLoginDto().getId());
        viewModel.getUpdatePasswordliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                getLoadingDialog().dismiss();
                if(stringBaseDto.getCode()==Constant.Server.SUCCESS_CODE){
                    ToastUitl.showImageToastSuccess("修改成功");
                    finish();
                }
                else{
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
