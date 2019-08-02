package com.jilian.pinzi.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.vo.SmsVo;
import com.jilian.pinzi.ui.viewmodel.UserViewModel;
import com.jilian.pinzi.utils.RxTimerUtil;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.ClearEditText;

/**
 * @author ningpan
 * @since 2018/10/30 16:59 <br>
 * description: 忘记密码/重置密码 Activity
 */
public class ResetPwdActivity extends BaseActivity {

    private ClearEditText etPhone;
    private ClearEditText etCode;
    private TextView tvGetmsg;
    private ClearEditText etPwd;
    private ClearEditText etNewPwd;
    private TextView tvOk;
    private UserViewModel userViewModel;
    private ImageView ivEyeOne;
    private ImageView ivEyeTwo;
    private RelativeLayout rlEyeOne;
    private RelativeLayout rlEyeTwo;
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
        return R.layout.activity_reset_pwd;
    }

    @Override
    public void initView() {
        setCenterTitle("重置密码", "#FFFFFF");
        setleftImage(R.drawable.image_back, true, null);
        setTitleBg(R.color.color_black);
        etPhone = (ClearEditText) findViewById(R.id.et_phone);
        etCode = (ClearEditText) findViewById(R.id.et_code);
        tvGetmsg = (TextView) findViewById(R.id.tv_getmsg);
        etPwd = (ClearEditText) findViewById(R.id.et_pwd);
        etNewPwd = (ClearEditText) findViewById(R.id.et_new_pwd);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        ivEyeOne = (ImageView) findViewById(R.id.iv_eye_one);
        ivEyeTwo = (ImageView) findViewById(R.id.iv_eye_two);
        rlEyeOne = (RelativeLayout) findViewById(R.id.rl_eye_one);
        rlEyeTwo = (RelativeLayout) findViewById(R.id.rl_eye_two);
    }

    @Override
    public void initData() {

    }

    private int time = 60;
    private boolean eyeOne;
    private boolean eyeTwo;

    @Override
    public void initListener() {
        rlEyeOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTheOneEyes();
            }
        });
        rlEyeTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTheTwoEyes();

            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPwd();
            }
        });
        tvGetmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMsgCode();
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
        etNewPwd.addTextChangedListener(new TextWatcher() {
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
    }

    private void changeTheTwoEyes() {
        if (!eyeTwo) {
            // display password text, for example "123456"
            etNewPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ivEyeTwo.setImageResource(R.drawable.image_login_open);
        } else {
            // hide password, display "."
            etNewPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ivEyeTwo.setImageResource(R.drawable.image_login_close);
        }
        etNewPwd.setSelection(etNewPwd.getText().toString().length());//将光标移至文字末尾
        eyeTwo = !eyeTwo;
        etNewPwd.postInvalidate();
    }

    private void changeTheOneEyes() {
        if (!eyeOne) {
            // display password text, for example "123456"
            etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ivEyeOne.setImageResource(R.drawable.image_login_open);
        } else {
            // hide password, display "."
            etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ivEyeOne.setImageResource(R.drawable.image_login_close);
        }
        etPwd.setSelection(etPwd.getText().toString().length());//将光标移至文字末尾
        eyeOne = !eyeOne;
        etPwd.postInvalidate();
    }



    /**
     * 重置密码
     */
    private void resetPwd() {
        if (!etPwd.getText().toString().equals(etNewPwd.getText().toString())) {
            ToastUitl.showImageToastFail("两次输入密码不一致");
            return;
        }
        userViewModel.resetPwd(etPhone.getText().toString(), etCode.getText().toString(), etNewPwd.getText().toString());
        userViewModel.getResetPwdliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                if (stringBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                    ToastUitl.showImageToastSuccess("重置密码成功");
                    finish();
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }
            }
        });
    }

    /**
     * 获取验证码
     */
    private void getMsgCode() {
        RxTimerUtil.cancel();
        getLoadingDialog().showDialog();
        SmsVo vo = new SmsVo();
        vo.setType(2);
        vo.setPhone(etPhone.getText().toString());
        userViewModel.sendMsg(vo);
        userViewModel.getSmsliveData().observe(ResetPwdActivity.this, new Observer<BaseDto<String>>() {
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
                                //清除图标
                                etPhone.setClearIconVisible(true);
                            } else {
                                time--;
                                tvGetmsg.setText(String.valueOf(time + "s"));
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
     * 更新按钮的可用状态
     */
    private void updateEnableStatus() {
        if (!TextUtils.isEmpty(etPhone.getText().toString())
                && !TextUtils.isEmpty(etPwd.getText().toString())
                && !TextUtils.isEmpty(etCode.getText().toString())
                && !TextUtils.isEmpty(etNewPwd.getText().toString())) {
            tvOk.setEnabled(true);
            tvOk.setBackgroundResource(R.drawable.shape_btn_login_normal);
        } else {
            tvOk.setEnabled(false);
            tvOk.setBackgroundResource(R.drawable.shape_btn_login_dark);
        }
    }
}
