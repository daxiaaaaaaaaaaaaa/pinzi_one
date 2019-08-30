package com.jilian.pinzi.ui.my;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.ToastUitl;

import java.beans.IndexedPropertyChangeEvent;

/**
 * 提现
 */
public class WithdrawalActivity extends BaseActivity {

    private EditText etMoney;
    private EditText etName;
    private EditText etAccount;
    private TextView tvOk;
    private MyViewModel viewModel;
    private TextView tvOne;
    private TextView tvTwo;
    private View vOne;
    private View vTwo;
    private int classify =1;
    private View vCenter;
    private EditText etBankName;




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
        return R.layout.activity_withdrawal;
    }

    @Override
    public void initView() {
        setNormalTitle("提现", v -> finish());
        tvOk = (TextView) findViewById(R.id.tv_ok);
        etMoney = (EditText) findViewById(R.id.et_money);
        etName = (EditText) findViewById(R.id.et_name);
        etAccount = (EditText) findViewById(R.id.et_account);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        tvOne = (TextView) findViewById(R.id.tv_one);
        tvTwo = (TextView) findViewById(R.id.tv_two);
        vOne = (View) findViewById(R.id.v_one);
        vTwo = (View) findViewById(R.id.v_two);
        vCenter = (View) findViewById(R.id.v_center);
        etBankName = (EditText) findViewById(R.id.et_bank_name);


    }

    @Override
    public void initData() {
        type = getIntent().getIntExtra("type", 0);
    }

    @Override
    public void initListener() {
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Double.parseDouble(etMoney.getText().toString())<=0){
                        ToastUitl.showImageToastFail("提现金额要大于0");
                        return;
                }
                if(classify==2&&TextUtils.isEmpty(etBankName.getText().toString())){
                    ToastUitl.showImageToastFail("请输入银行名称");
                    return;
                }
                getMoney();
            }
        });
        etAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                initBtnStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                initBtnStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                initBtnStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(classify==1){
                    return;
                }
                vOne.setVisibility(View.VISIBLE);
                vTwo.setVisibility(View.INVISIBLE);
                classify =1 ;
                etAccount.setHint("请输入支付宝账号");
                vCenter.setVisibility(View.GONE);
                etBankName.setVisibility(View.GONE);
                etAccount.setText(null);
                etBankName.setText(null);
                initBtnStatus();
            }
        });
        tvTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(classify==2){
                    return;
                }
                vOne.setVisibility(View.INVISIBLE);
                vTwo.setVisibility(View.VISIBLE);
                classify =2 ;
                etAccount.setHint("请输入银行卡账号");
                vCenter.setVisibility(View.VISIBLE);
                etBankName.setVisibility(View.VISIBLE);
                etAccount.setText(null);
                etBankName.setText(null);
                initBtnStatus();
            }
        });

    }

    private Integer type;

    /**
     * 提现
     */
    private void getMoney() {
        getLoadingDialog().showDialog();
        viewModel.getWithdrawDeposit(getLoginDto().getId(), etMoney.getText().toString(), etBankName.getText().toString(),etName.getText().toString(), etAccount.getText().toString(), type,classify);
        viewModel.getWithdrawDepositListliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                getLoadingDialog().dismiss();
                if (stringBaseDto.isSuccess()) {
                    if(stringBaseDto.getMsg().contains("不足")){
                        ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                        return;
                    }
                    startActivity(new Intent(WithdrawalActivity.this, WithdrawalAuditActivity.class));
                    finish();
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }
            }
        });
    }

    private void initBtnStatus() {
        if (TextUtils.isEmpty(etAccount.getText().toString()) ||
                TextUtils.isEmpty(etName.getText().toString()) ||
                TextUtils.isEmpty(etMoney.getText().toString())) {
            tvOk.setEnabled(false);
            tvOk.setBackgroundResource(R.drawable.shape_btn_login_dark);

        } else {
            tvOk.setEnabled(true);
            tvOk.setBackgroundResource(R.drawable.shape_btn_login_normal);
        }
    }
}
