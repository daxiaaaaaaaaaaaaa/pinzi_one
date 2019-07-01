package com.jilian.pinzi.ui;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.ui.viewmodel.UserViewModel;
import com.jilian.pinzi.utils.PermissionsObserver;
import com.jilian.pinzi.utils.SPUtil;
import com.jilian.pinzi.utils.ToastUitl;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class LoginActivity extends BaseActivity {
    private RelativeLayout rlCancel;
    private EditText etPhone;
    private EditText etPwd;
    private TextView tvLogin;
    private TextView tvCreatUser;
    private ImageView ivWeixin;
    private ImageView ivWeibo;
    private ImageView ivQq;
    private RelativeLayout rlEye;
    private ImageView ivEye;
    private TextView tvForgetPwd;
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
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public int intiLayout() {

        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_white), 0);
        rlCancel = (RelativeLayout) findViewById(R.id.rl_cancel);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        tvLogin = (TextView) findViewById(R.id.tv_login);
        tvCreatUser = (TextView) findViewById(R.id.tv_creat_user);
        ivWeixin = (ImageView) findViewById(R.id.iv_weixin);
        ivWeibo = (ImageView) findViewById(R.id.iv_weibo);
        ivQq = (ImageView) findViewById(R.id.iv_qq);
        rlEye = (RelativeLayout) findViewById(R.id.rl_eye);
        ivEye = (ImageView) findViewById(R.id.iv_eye);
        tvForgetPwd = findViewById(R.id.tv_login_forget_pwd);

    }

    @Override
    public void initData() {
        PinziApplication.clearSpecifyActivities(new Class[]{WelcomeActivity.class});
        // 获取权限
        requestPermission();
    }

    private boolean mbDisplayFlg;
    private UMShareAPI umShareAPI;
    private UMAuthListener umAuthListener;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
    private String loginType;
    private String type = "0";// false number 0,用户首次调用三方登录接口，二次调用需要携带以下参数且type类型的值必须为类型（1.普通用户 2.门店 3.二批商）
    @Override
    public void initListener() {
        umAuthListener = new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA platform) {}
            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                 Logger.e("openid: " + data.get("uid"));
                 Logger.e("昵称: " + data.get("name"));
                 Logger.e("头像: " + data.get("iconurl"));
                 Logger.e("性别: " + data.get("gender"));
                 showLoadingDialog();
                 userViewModel.ThirdUserLogin(data.get("uid"),loginType,data.get("iconurl"),data.get("name"),type,null,null,null,null);
                userViewModel.getThreeliveData().observe(LoginActivity.this, new Observer<BaseDto<LoginDto>>() {
                    @Override
                    public void onChanged(@Nullable BaseDto<LoginDto> loginDtoBaseDto) {
                        hideLoadingDialog();
                        if (loginDtoBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                            SPUtil.putData(Constant.SP_VALUE.SP,Constant.SP_VALUE.LOGIN_DTO,loginDtoBaseDto.getData());
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            ToastUitl.showImageToastSuccess(loginDtoBaseDto.getMsg());
                            finish();
                            PinziApplication.clearAllActivitys();
                        }
                        //未注册
                        if(loginDtoBaseDto.getCode() == Constant.Server.REGISTER_CODE){
                            //按照 后台的人说 把 登录状态  保存到前端
                           // SPUtil.putData(Constant.SP_VALUE.SP,Constant.SP_VALUE.LOGIN_DTO,loginDtoBaseDto.getData());
                            Intent intent  = new Intent(LoginActivity.this, RegisterActivity.class);
                            intent.putExtra("loginId",data.get("uid"));
                            intent.putExtra("loginType",loginType);
                            intent.putExtra("headImg",data.get("iconurl"));
                            intent.putExtra("uName",data.get("name"));

                            startActivity(intent);
                            finish();
                        }

                        if(loginDtoBaseDto.getCode() == Constant.Server.NOPERFORM_CODE){
                            //按照 后台的人说 把 登录状态  保存到前端
                            SPUtil.putData(Constant.SP_VALUE.SP,Constant.SP_VALUE.LOGIN_DTO,loginDtoBaseDto.getData());
                            startActivity(new Intent(LoginActivity.this, PerfectInformationActivity.class));
                            finish();
                        }
                        if(loginDtoBaseDto.getCode() == Constant.Server.CHECKING_CODE){
                            SPUtil.putData(Constant.SP_VALUE.SP,Constant.SP_VALUE.LOGIN_DTO,loginDtoBaseDto.getData());
                            startActivity(new Intent(LoginActivity.this, UserCheckActivity.class));
                            finish();
                        }

                        if(loginDtoBaseDto.getCode() == Constant.Server.CHECKFAILUER_CODE){
                            SPUtil.putData(Constant.SP_VALUE.SP,Constant.SP_VALUE.LOGIN_DTO,loginDtoBaseDto.getData());
                            startActivity(new Intent(LoginActivity.this, UserCheckActivity.class));
                            finish();
                        }

                        else {
                            ToastUitl.showImageToastFail(loginDtoBaseDto.getMsg());
                        }
                    }
                });
            }

            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            }

            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {

            }
        };
        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                PinziApplication.clearAllActivitys();
                finish();
            }
        });
        ivQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                umShareAPI = UMShareAPI.get(LoginActivity.this);
                umShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);//QQ登录
                //umShareAPI.deleteOauth(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);//撤销QQ授权


                startActivity(new Intent(LoginActivity.this, BindPhoneActivity.class));
            }
        });
        ivWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(LoginActivity.this, BindPhoneActivity.class));
                umShareAPI = UMShareAPI.get(LoginActivity.this);
                umShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.SINA, umAuthListener);//新浪登录
                //umShareAPI.deleteOauth(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);//撤销新浪授权

            }
        });
        ivWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginType = "0";
                umShareAPI = UMShareAPI.get(LoginActivity.this);

                umShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);//微信登录
                //umShareAPI.deleteOauth(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);//撤销微信授权


              //  startActivity(new Intent(LoginActivity.this, BindPhoneActivity.class));
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login();
            }
        });
        tvCreatUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(etPhone.getText().toString()) && !TextUtils.isEmpty(etPwd.getText().toString())) {
                    tvLogin.setBackgroundResource(R.drawable.shape_btn_login_normal);
                    tvLogin.setEnabled(true);
                } else {
                    tvLogin.setBackgroundResource(R.drawable.shape_btn_login_dark);
                    tvLogin.setEnabled(false);
                }
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
                if (!TextUtils.isEmpty(etPhone.getText().toString()) && !TextUtils.isEmpty(etPwd.getText().toString())) {
                    tvLogin.setBackgroundResource(R.drawable.shape_btn_login_normal);
                    tvLogin.setEnabled(true);
                } else {
                     tvLogin.setBackgroundResource(R.drawable.shape_btn_login_dark);
                       tvLogin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        rlEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        tvForgetPwd.setOnClickListener(v -> startActivity(new Intent(this, ResetPwdActivity.class)));

    }

    /**
     * 登录
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
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    ToastUitl.showImageToastSuccess(loginDtoBaseDto.getMsg());
                    finish();
                    PinziApplication.clearAllActivitys();
                }
                if(loginDtoBaseDto.getCode() == Constant.Server.NOPERFORM_CODE){
                    //按照 后台的人说 把 登录状态  保存到前端
                    SPUtil.putData(Constant.SP_VALUE.SP,Constant.SP_VALUE.LOGIN_DTO,loginDtoBaseDto.getData());
                    startActivity(new Intent(LoginActivity.this, PerfectInformationActivity.class));
                    finish();
                }
                if(loginDtoBaseDto.getCode() == Constant.Server.CHECKING_CODE){
                    SPUtil.putData(Constant.SP_VALUE.SP,Constant.SP_VALUE.LOGIN_DTO,loginDtoBaseDto.getData());
                    startActivity(new Intent(LoginActivity.this, UserCheckActivity.class));
                    finish();
                }

                if(loginDtoBaseDto.getCode() == Constant.Server.CHECKFAILUER_CODE){
                    SPUtil.putData(Constant.SP_VALUE.SP,Constant.SP_VALUE.LOGIN_DTO,loginDtoBaseDto.getData());
                    startActivity(new Intent(LoginActivity.this, UserCheckActivity.class));
                    finish();
                }

                else {
                    ToastUitl.showImageToastFail(loginDtoBaseDto.getMsg());
                }

            }
        });
    }

    /**
     * 申请权限
     */
    private void requestPermission() {
        RxPermissions.getInstance(this).request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,//写权限
                Manifest.permission.READ_EXTERNAL_STORAGE, //读权限
                Manifest.permission.CAMERA //相机权限

        ).subscribe(new PermissionsObserver() {
            @Override
            protected void onGetPermissionsSuccess() {
            }

            @Override
            protected void onGetPermissionsSuccessFailuer() {
            }
        });
    }
}
