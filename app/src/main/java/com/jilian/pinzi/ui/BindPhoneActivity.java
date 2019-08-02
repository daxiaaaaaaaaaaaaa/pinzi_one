package com.jilian.pinzi.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.CommonActivity;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.common.vo.SmsVo;
import com.jilian.pinzi.ui.viewmodel.UserViewModel;
import com.jilian.pinzi.utils.PhoneUtils;
import com.jilian.pinzi.utils.RxTimerUtil;
import com.jilian.pinzi.utils.SPUtil;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.ClearEditText;

/**
 * @author ningpan
 * @since 2018/10/30 17:14 <br>
 * description: 绑定手机号码 Activity
 */
public class BindPhoneActivity extends CommonActivity {
    private ClearEditText etPhone;
    private ClearEditText etCode;
    private TextView tvGetCode;
    private TextView tvOk;

    private UserViewModel userViewModel;

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
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_bind_phone;
    }

    @Override
    public void initView() {
        setCenterTitle("绑定手机号码", "#FFFFFF");
        setleftImage(R.drawable.image_back, true, null);
        setTitleBg(R.color.color_black);
        etPhone = (ClearEditText) findViewById(R.id.et_phone);
        etCode = (ClearEditText) findViewById(R.id.et_code);
        tvGetCode = (TextView) findViewById(R.id.tv_get_code);
        tvOk = (TextView) findViewById(R.id.tv_ok);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                initEnable();
            }
        });
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                initEnable();
            }
        });
        tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PhoneUtils.checkPhoneNo(etPhone.getText().toString())) {
                    ToastUitl.showImageToastFail("请输入正确的手机号码");
                    return;
                }
                getMsgCode();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threeLogin();
            }
        });

    }

    private int time = 60;

    /**
     * 获取验证码
     */
    private void getMsgCode() {
        RxTimerUtil.cancel();
        getLoadingDialog().showDialog();
        SmsVo vo = new SmsVo();
        vo.setType(0);
        vo.setPhone(etPhone.getText().toString());
        userViewModel.sendMsg(vo);
        userViewModel.getSmsliveData().observe(BindPhoneActivity.this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                getLoadingDialog().dismiss();
                if (stringBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                    ToastUitl.showImageToastSuccess("获取验证码成功");
                    tvGetCode.setText(String.valueOf(time + "s"));
                    tvGetCode.setEnabled(false);
                    tvGetCode.setTextColor(getResources().getColor(R.color.color_text_dark));
                    RxTimerUtil.interval(1000, new RxTimerUtil.IRxNext() {
                        @Override
                        public void doNext() {
                            if (time == 1) {
                                tvGetCode.setTextColor(getResources().getColor(R.color.color_main_select));
                                tvGetCode.setEnabled(true);
                                tvGetCode.setText("获取验证码");
                                RxTimerUtil.cancel();
                                time = 60;
                                etPhone.setEnabled(true);
                                //清除图标
                                etPhone.setClearIconVisible(true);
                            } else {
                                time--;
                                tvGetCode.setText(String.valueOf(time + "s"));
                                etPhone.setEnabled(false);
                                //清除图标
                                etPhone.setClearIconVisible(false);
                            }

                        }
                    });
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }
            }
        });
    }

    /**
     * 初始化可用状态
     */
    private void initEnable() {
        if (TextUtils.isEmpty(etCode.getText().toString()) || TextUtils.isEmpty(etPhone.getText().toString())) {
            tvOk.setEnabled(false);
            tvOk.setBackgroundResource(R.drawable.shape_btn_login_dark);
        } else {
            tvOk.setEnabled(true);
            tvOk.setBackgroundResource(R.drawable.shape_btn_login_normal);
        }
    }

    private int type = 0;

    private void threeLogin() {
        getLoadingDialog().showDialog();
        userViewModel.ThirdUserLogin(getIntent().getStringExtra("loginId"), getIntent().getStringExtra("loginType"), getIntent().getStringExtra("headImg"), getIntent().getStringExtra("uName"), String.valueOf(type), etPhone.getText().toString(), etCode.getText().toString(), null, null);
        userViewModel.getThreeliveData().observe(BindPhoneActivity.this, new Observer<BaseDto<LoginDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<LoginDto> loginDtoBaseDto) {
                hideLoadingDialog();
                if (loginDtoBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                    SPUtil.putData(Constant.SP_VALUE.SP, Constant.SP_VALUE.LOGIN_DTO, loginDtoBaseDto.getData());
                    startActivity(new Intent(BindPhoneActivity.this, MainActivity.class));
                    ToastUitl.showImageToastSuccess(loginDtoBaseDto.getMsg());
                    finish();
                    PinziApplication.clearAllActivitys();
                }
                //未注册
                else if(loginDtoBaseDto.getCode() == Constant.Server.REGISTER_CODE){
                    //按照 后台的人说 把 登录状态  保存到前端
                    Intent intent  = new Intent(BindPhoneActivity.this, RegisterActivity.class);
                    intent.putExtra("phone",etPhone.getText().toString());
                    intent.putExtra("loginId",getIntent().getStringExtra("loginId"));
                    intent.putExtra("loginType", getIntent().getStringExtra("loginType"));
                    intent.putExtra("headImg",getIntent().getStringExtra("headImg"));
                    intent.putExtra("uName",getIntent().getStringExtra("uName"));
                    finish();
                    startActivity(intent);

                }
                //已注册 需要绑定手机号码
                else if(loginDtoBaseDto.getCode() == Constant.Server.BIND_CODE){
                    //按照 后台的人说 把 登录状态  保存到前端
                    Intent intent  = new Intent(BindPhoneActivity.this, BindPhoneActivity.class);
                    intent.putExtra("loginId",getIntent().getStringExtra("loginId"));
                    intent.putExtra("loginType", getIntent().getStringExtra("loginType"));
                    intent.putExtra("headImg",getIntent().getStringExtra("headImg"));
                    intent.putExtra("uName",getIntent().getStringExtra("uName"));
                    startActivity(intent);

                }
                else if (loginDtoBaseDto.getCode() == Constant.Server.NOPERFORM_CODE) {
                    //按照 后台的人说 把 登录状态  保存到前端
                    Intent intent = new Intent(BindPhoneActivity.this, PerfectInformationActivity.class);
                    intent.putExtra("userId", loginDtoBaseDto.getData().getId());
                    startActivity(intent);
                    finish();
                } else if (loginDtoBaseDto.getCode() == Constant.Server.CHECKING_CODE) {
                    startActivity(new Intent(BindPhoneActivity.this, UserCheckActivity.class));
                    finish();
                } else if (loginDtoBaseDto.getCode() == Constant.Server.CHECKFAILUER_CODE) {
                    startActivity(new Intent(BindPhoneActivity.this, UserCheckActivity.class));
                    finish();
                } else {
                    ToastUitl.showImageToastFail(loginDtoBaseDto.getMsg());
                }
            }
        });
    }
}
