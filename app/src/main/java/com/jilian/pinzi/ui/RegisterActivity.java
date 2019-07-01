package com.jilian.pinzi.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.common.dto.RegisterDto;
import com.jilian.pinzi.ui.viewmodel.UserViewModel;
import com.jilian.pinzi.utils.PhoneUtils;
import com.jilian.pinzi.utils.RxTimerUtil;
import com.jilian.pinzi.utils.SPUtil;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.ClearEditText;
import com.jilian.pinzi.common.vo.RegisterVo;
import com.jilian.pinzi.common.vo.SmsVo;

public class RegisterActivity extends BaseActivity {
    private RelativeLayout rlCancel;
    private LinearLayout llOneUser;
    private ImageView ivOneUser;
    private LinearLayout llTwoUser;
    private ImageView ivTwoUser;
    private LinearLayout llThreeUser;
    private ImageView ivThreeUser;
    private ClearEditText etPhone;
    private ClearEditText etCode;
    private ClearEditText etPwd;
    private ClearEditText etInviteCode;
    private LinearLayout llGreed;
    private TextView tvRegisterUserService;
    private TextView tvOk;
    private Integer type = 1;//用户类型（1.普通用户 2.终端 3.渠道 4.总经销商）
    private ImageView ivAgree;
    private boolean agreeCheck;
    private UserViewModel userViewModel;
    private TextView tvGetmsg;
    private ImageView ivEye;
    private RelativeLayout rlEyeOne;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        PinziApplication.removeActivity(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PinziApplication.addActivity(this);
    }

    @Override
    protected void createViewModel() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_white), 0);
        ivAgree = (ImageView) findViewById(R.id.iv_agree);
        rlCancel = (RelativeLayout) findViewById(R.id.rl_cancel);
        llOneUser = (LinearLayout) findViewById(R.id.ll_one_user);
        ivOneUser = (ImageView) findViewById(R.id.iv_one_user);
        llTwoUser = (LinearLayout) findViewById(R.id.ll_two_user);
        ivTwoUser = (ImageView) findViewById(R.id.iv_two_user);
        llThreeUser = (LinearLayout) findViewById(R.id.ll_three_user);
        ivThreeUser = (ImageView) findViewById(R.id.iv_three_user);
        etPhone = (ClearEditText) findViewById(R.id.et_phone);
        etCode = (ClearEditText) findViewById(R.id.et_code);
        etPwd = (ClearEditText) findViewById(R.id.et_pwd);
        etInviteCode = (ClearEditText) findViewById(R.id.et_invite_code);
        llGreed = (LinearLayout) findViewById(R.id.ll_greed);
        tvRegisterUserService = (TextView) findViewById(R.id.tv_register_user_service);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        tvGetmsg = (TextView) findViewById(R.id.tv_getmsg);
        ivEye = (ImageView) findViewById(R.id.iv_eye);
        rlEyeOne = (RelativeLayout) findViewById(R.id.rl_eye_one);


    }

    @Override
    public void initData() {

    }

    private int time = 60;
    private boolean mbDisplayFlg;

    @Override
    public void initListener() {
        rlEyeOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mbDisplayFlg) {
                    // display password text, for example "123456"
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ivEye.setImageResource(R.drawable.image_login_open);
                } else {
                    // hide password, display "."
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ivEye.setImageResource(R.drawable.image_login_close);
                }
                etPwd.setSelection(etPwd.getText().toString().length());//将光标移至文字末尾
                mbDisplayFlg = !mbDisplayFlg;
                etPwd.postInvalidate();
            }
        });
        tvGetmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!PhoneUtils.checkPhoneNo(etPhone.getText().toString())) {
                    ToastUitl.showImageToastFail("请输入正确的手机号码");
                    return;
                }
                getMsgCode();
            }
        });
        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(etPhone.getText().toString())) {
                    tvGetmsg.setEnabled(false);
                    tvGetmsg.setTextColor(getResources().getColor(R.color.color_text_dark));
                } else {
                    tvGetmsg.setEnabled(true);
                    tvGetmsg.setTextColor(getResources().getColor(R.color.color_main_select));
                }
                updateEnableStatus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateEnableStatus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                updateEnableStatus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etInviteCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateEnableStatus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(RegisterActivity.this, PerfectInformationActivity.class));
                if (etPwd.getText().toString().length() < 6) {
                    ToastUitl.showImageToastFail("密码至少6位数");
                    return;
                }
                if (!PhoneUtils.checkPhoneNo(etPhone.getText().toString())) {
                    ToastUitl.showImageToastFail("请输入正确的手机号码");
                    return;
                }
                register();
            }
        });
        tvRegisterUserService.setOnClickListener(v -> startActivity(new Intent(this, UserServiceActivity.class)));
        llOneUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivOneUser.setImageResource(R.drawable.image_checked);
                ivTwoUser.setImageResource(R.drawable.image_uncheck);
                ivThreeUser.setImageResource(R.drawable.image_uncheck);
                type = 1;
            }
        });
        llTwoUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivTwoUser.setImageResource(R.drawable.image_checked);
                ivOneUser.setImageResource(R.drawable.image_uncheck);
                ivThreeUser.setImageResource(R.drawable.image_uncheck);
                type = 2;
            }
        });
        llThreeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivThreeUser.setImageResource(R.drawable.image_checked);
                ivTwoUser.setImageResource(R.drawable.image_uncheck);
                ivOneUser.setImageResource(R.drawable.image_uncheck);
                type = 3;
            }
        });
        llGreed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agreeCheck = !agreeCheck;
                if (agreeCheck) {
                    ivAgree.setImageResource(R.drawable.image_checked);
                } else {
                    ivAgree.setImageResource(R.drawable.image_uncheck);
                }
                updateEnableStatus();
            }
        });
    }

    /**
     * 获取验证码
     */
    private void getMsgCode() {
        getLoadingDialog().showDialog();
        SmsVo vo = new SmsVo();
        vo.setType(1);
        vo.setPhone(etPhone.getText().toString());
        userViewModel.sendMsg(vo);
        userViewModel.getSmsliveData().observe(RegisterActivity.this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                getLoadingDialog().dismiss();
                if (stringBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                    //ToastUitl.showImageToastSuccess(stringBaseDto.getData());
                    tvGetmsg.setText(String.valueOf(time + "s"));
                    tvGetmsg.setEnabled(false);
                    tvGetmsg.setTextColor(getResources().getColor(R.color.color_text_dark));
                    RxTimerUtil.interval(1000, new RxTimerUtil.IRxNext() {
                        @Override
                        public void doNext() {
                            if (time == 1) {
                                tvGetmsg.setTextColor(getResources().getColor(R.color.color_main_select));
                                tvGetmsg.setEnabled(true);
                                tvGetmsg.setText("获取验证码");
                                RxTimerUtil.cancel();
                                time = 60;
                                etPhone.setEnabled(true);
                            } else {
                                time--;
                                tvGetmsg.setText(String.valueOf(time + "s"));
                                etPhone.setEnabled(false);
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
     * 用户注册
     */
    private void register() {
        if(TextUtils.isEmpty(getIntent().getStringExtra("loginId"))){
            getLoadingDialog().showDialog();
            //注册
            RegisterVo vo = new RegisterVo();
            vo.setType(type);
            vo.setInvitationCode(etInviteCode.getText().toString());
            vo.setMsgCode(etCode.getText().toString());
            vo.setPassword(etPwd.getText().toString());
            vo.setPhone(etPhone.getText().toString());
            userViewModel.register(vo);
            userViewModel.getRegisterliveData().observe(this, new Observer<BaseDto<RegisterDto>>() {
                @Override
                public void onChanged(@Nullable BaseDto<RegisterDto> registerDtoBaseDto) {
                    getLoadingDialog().dismiss();
                    if (registerDtoBaseDto.isSuccess()) {
                        ToastUitl.showImageToastSuccess("注册成功");
                        login();

                    } else {
                        ToastUitl.showImageToastFail(registerDtoBaseDto.getMsg());
                    }
                }
            });
        }
        else{
            threeLogin();
        }



    }

    private void threeLogin() {
        getLoadingDialog().showDialog();
        userViewModel.ThirdUserLogin(getIntent().getStringExtra("loginId"),getIntent().getStringExtra("loginType"),getIntent().getStringExtra("headImg"),getIntent().getStringExtra("uName"),String.valueOf(type),etPhone.getText().toString(),etCode.getText().toString(),etInviteCode.getText().toString(),etPwd.getText().toString());
        userViewModel.getThreeliveData().observe(RegisterActivity.this, new Observer<BaseDto<LoginDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<LoginDto> loginDtoBaseDto) {
                hideLoadingDialog();
                if (loginDtoBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                    SPUtil.putData(Constant.SP_VALUE.SP,Constant.SP_VALUE.LOGIN_DTO,loginDtoBaseDto.getData());
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    ToastUitl.showImageToastSuccess(loginDtoBaseDto.getMsg());
                    finish();
                    PinziApplication.clearAllActivitys();
                }

                if(loginDtoBaseDto.getCode() == Constant.Server.NOPERFORM_CODE){
                    //按照 后台的人说 把 登录状态  保存到前端
                    SPUtil.putData(Constant.SP_VALUE.SP,Constant.SP_VALUE.LOGIN_DTO,loginDtoBaseDto.getData());
                    startActivity(new Intent(RegisterActivity.this, PerfectInformationActivity.class));
                    finish();
                }
                if(loginDtoBaseDto.getCode() == Constant.Server.CHECKING_CODE){
                    SPUtil.putData(Constant.SP_VALUE.SP,Constant.SP_VALUE.LOGIN_DTO,loginDtoBaseDto.getData());
                    startActivity(new Intent(RegisterActivity.this, UserCheckActivity.class));
                    finish();
                }

                if(loginDtoBaseDto.getCode() == Constant.Server.CHECKFAILUER_CODE){
                    SPUtil.putData(Constant.SP_VALUE.SP,Constant.SP_VALUE.LOGIN_DTO,loginDtoBaseDto.getData());
                    startActivity(new Intent(RegisterActivity.this, UserCheckActivity.class));
                    finish();
                }

                else {
                    ToastUitl.showImageToastFail(loginDtoBaseDto.getMsg());
                }
            }
        });
    }

    /**
     * 登陆
     */
    private void login() {
        getLoadingDialog().showDialog();
        userViewModel.login(etPhone.getText().toString(), etPwd.getText().toString());
        userViewModel.getLoginliveData().observe(this, new Observer<BaseDto<LoginDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<LoginDto> loginDtoBaseDto) {
                getLoadingDialog().dismiss();
                if (loginDtoBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                    SPUtil.putData(Constant.SP_VALUE.SP,Constant.SP_VALUE.LOGIN_DTO,loginDtoBaseDto.getData());
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    ToastUitl.showImageToastSuccess(loginDtoBaseDto.getMsg());
                    finish();
                    PinziApplication.clearSpecifyActivities(new Class[]{LoginActivity.class});
                }
                if(loginDtoBaseDto.getCode() == Constant.Server.NOPERFORM_CODE){
                    //按照 后台的人说 把 登录状态  保存到前端
                    SPUtil.putData(Constant.SP_VALUE.SP,Constant.SP_VALUE.LOGIN_DTO,loginDtoBaseDto.getData());
                    startActivity(new Intent(RegisterActivity.this, PerfectInformationActivity.class));

                }
                if(loginDtoBaseDto.getCode() == Constant.Server.CHECKING_CODE){
                    SPUtil.putData(Constant.SP_VALUE.SP,Constant.SP_VALUE.LOGIN_DTO,loginDtoBaseDto.getData());
                    startActivity(new Intent(RegisterActivity.this, UserCheckActivity.class));

                }

                if(loginDtoBaseDto.getCode() == Constant.Server.CHECKFAILUER_CODE){
                    SPUtil.putData(Constant.SP_VALUE.SP,Constant.SP_VALUE.LOGIN_DTO,loginDtoBaseDto.getData());
                    startActivity(new Intent(RegisterActivity.this, UserCheckActivity.class));

                }

                else {
                    ToastUitl.showImageToastFail(loginDtoBaseDto.getMsg());
                }

            }
        });
    }

    /**
     * 更新按钮的可用状态
     */
    private void updateEnableStatus() {
        if (!TextUtils.isEmpty(etPhone.getText().toString())
                && !TextUtils.isEmpty(etPwd.getText().toString())
                && !TextUtils.isEmpty(etCode.getText().toString())
                && agreeCheck) {
            tvOk.setEnabled(true);
            tvOk.setBackgroundResource(R.drawable.shape_btn_login_normal);
        } else {
            tvOk.setEnabled(false);
            tvOk.setBackgroundResource(R.drawable.shape_btn_login_dark);
        }
    }
}
