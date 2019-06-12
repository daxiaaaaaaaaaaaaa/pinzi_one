package com.jilian.pinzi.ui.shopcard;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.AddressDto;
import com.jilian.pinzi.common.vo.InvoiceVo;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.PhoneUtils;
import com.jilian.pinzi.utils.SPUtil;
import com.jilian.pinzi.utils.ToastUitl;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class InvoiceActivity extends BaseActivity {
    private LinearLayout rlOne;
    private LinearLayout rlTwo;
    private LinearLayout llOne;
    private LinearLayout llTwo;
    private ImageView ivOne;
    private ImageView ivTwo;
    private MainViewModel viewModel;
    private EditText tvInvoiceTitle;
    private EditText tvUnitAddress;
    private EditText tvPhone;
    private EditText tvBankAccount;
    private EditText tvOpenBank;
    private EditText tvDutyMark;
    private EditText tvTakerName;
    private EditText tvTakerPhone;
    private EditText tvTakerAddress;
    private TextView tvOk;
    private Integer type = 1;//  类型（1.增值税专用发票 2.增值税普通发票）
    private MyViewModel myViewModel;

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
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_invoice;
    }

    @Override
    public void initView() {
        setNormalTitle("发票信息", v -> finish());
        rlOne = (LinearLayout) findViewById(R.id.rl_one);
        rlTwo = (LinearLayout) findViewById(R.id.rl_two);
        llOne = (LinearLayout) findViewById(R.id.ll_one);
        llTwo = (LinearLayout) findViewById(R.id.ll_two);
        ivOne = (ImageView) findViewById(R.id.iv_one);
        ivTwo = (ImageView) findViewById(R.id.iv_two);
        tvInvoiceTitle = (EditText) findViewById(R.id.tv_invoiceTitle);
        tvUnitAddress = (EditText) findViewById(R.id.tv_unitAddress);
        tvPhone = (EditText) findViewById(R.id.tv_phone);
        tvBankAccount = (EditText) findViewById(R.id.tv_bankAccount);
        tvOpenBank = (EditText) findViewById(R.id.tv_openBank);
        tvDutyMark = (EditText) findViewById(R.id.tv_dutyMark);
        tvTakerName = (EditText) findViewById(R.id.tv_takerName);
        tvTakerPhone = (EditText) findViewById(R.id.tv_takerPhone);
        tvTakerAddress = (EditText) findViewById(R.id.tv_takerAddress);
        tvOk = (TextView) findViewById(R.id.tv_ok);

    }

    private int pageNo = 1;//
    private int pageSize = 1;//
    private AddressDto address;

    @Override
    public void initData() {
        getAddressList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initInputData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        save();
    }

    /**
     * 获取本地缓存的数据.
     */
    private void initInputData() {
        String str = SPUtil.getData(Constant.SP_VALUE.SP, Constant.SP_VALUE.INVOICE, String.class, null);
        if(EmptyUtils.isNotEmpty(str)){
            InvoiceVo vo = JSONObject.parseObject(str, InvoiceVo.class);
            if (EmptyUtils.isNotEmpty(vo)) {
                tvInvoiceTitle.setText(vo.getInvoiceTitle());
                tvUnitAddress.setText(vo.getUnitAddress());
                tvPhone.setText(vo.getPhone());
                tvBankAccount.setText(vo.getBankAccount());
                tvOpenBank.setText(vo.getOpenBank());
                tvDutyMark.setText(vo.getDutyMark());
                tvTakerName.setText(vo.getTakerName());
                tvTakerPhone.setText(vo.getTakerPhone());
                tvTakerAddress.setText(vo.getTakerAddress());
            }
        }


    }

    /**
     * 将输入的数据 保存到本地
     */
    private void save() {
        InvoiceVo vo = new InvoiceVo();
        vo.setInvoiceTitle(tvInvoiceTitle.getText().toString());
        vo.setTakerName(tvTakerName.getText().toString());
        vo.setTakerAddress(tvTakerAddress.getText().toString());
        vo.setTakerPhone(tvPhone.getText().toString());
        vo.setUnitAddress(tvUnitAddress.getText().toString());
        vo.setPhone(tvPhone.getText().toString());
        vo.setBankAccount(tvBankAccount.getText().toString());
        vo.setOpenBank(tvOpenBank.getText().toString());
        vo.setDutyMark(tvDutyMark.getText().toString());
        String str = JSONObject.toJSONString(vo);
        SPUtil.putData(Constant.SP_VALUE.SP, Constant.SP_VALUE.INVOICE, str);
    }
    /**
     * 获取地址列表
     */
    private void getAddressList() {
        myViewModel.getUserAddressList(pageNo, pageSize, getLoginDto().getId());
        myViewModel.getAddressListliveData().observe(this, new Observer<BaseDto<List<AddressDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<AddressDto>> addressDtoBaseDto) {
                hideLoadingDialog();
                //有数据
                if (EmptyUtils.isNotEmpty(addressDtoBaseDto.getData())) {
                    address = addressDtoBaseDto.getData().get(0);
                    initAdrdessView();
                }

            }
        });
    }

    //显示地址
    private void initAdrdessView() {
        tvTakerAddress.setText(address.getArea() + address.getAddress());
    }

    @Override
    public void initListener() {
        //
        viewModel.addInvoice(type,
                tvInvoiceTitle.getText().toString(),
                tvUnitAddress.getText().toString(),
                tvPhone.getText().toString(),
                tvBankAccount.getText().toString(),
                tvOpenBank.getText().toString(),
                tvDutyMark.getText().toString(),
                tvTakerName.getText().toString(),
                tvTakerPhone.getText().toString(),
                tvTakerAddress.getText().toString());
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(tvInvoiceTitle.getText().toString())) {
                    ToastUitl.showImageToastFail("请填写完整信息");
                    return;
                }
                if (TextUtils.isEmpty(tvTakerName.getText().toString())) {
                    ToastUitl.showImageToastFail("请填写完整信息");
                    return;
                }
                if (TextUtils.isEmpty(tvTakerPhone.getText().toString())) {
                    ToastUitl.showImageToastFail("请填写完整信息");
                    return;
                }
                if (TextUtils.isEmpty(tvTakerAddress.getText().toString())) {
                    ToastUitl.showImageToastFail("请填写完整信息");
                    return;
                }
                //校验电话
                if (!PhoneUtils.checkPhoneNo(tvTakerPhone.getText().toString())) {
                    ToastUitl.showImageToastFail("请输入正确的收票人电话");
                    return;
                }
                if (type == 1) {
                    if (TextUtils.isEmpty(tvUnitAddress.getText().toString())) {
                        ToastUitl.showImageToastFail("请填写完整信息");
                        return;
                    }
                    if (TextUtils.isEmpty(tvPhone.getText().toString())) {
                        ToastUitl.showImageToastFail("请填写完整信息");
                        return;
                    }
                    if (TextUtils.isEmpty(tvBankAccount.getText().toString())) {
                        ToastUitl.showImageToastFail("请填写完整信息");
                        return;
                    }
                    if (TextUtils.isEmpty(tvOpenBank.getText().toString())) {
                        ToastUitl.showImageToastFail("请填写完整信息");
                        return;
                    }
                    if (TextUtils.isEmpty(tvDutyMark.getText().toString())) {
                        ToastUitl.showImageToastFail("请填写完整信息");
                        return;
                    }
                    //校验电话
                    if (!PhoneUtils.checkPhoneNo(tvPhone.getText().toString())) {
                        ToastUitl.showImageToastFail("请输入正确的联系人电话");
                        return;
                    }
                    //校验银行卡号
                    if (!PhoneUtils.checkBankCard(tvBankAccount.getText().toString())) {
                        ToastUitl.showImageToastFail("请输入正确的请输入银行账户");
                        return;
                    }
                }
                showLoadingDialog();
                viewModel.addInvoice(type,
                        tvInvoiceTitle.getText().toString(),
                        tvUnitAddress.getText().toString(),
                        tvPhone.getText().toString(),
                        tvBankAccount.getText().toString(),
                        tvOpenBank.getText().toString(),
                        tvDutyMark.getText().toString(),
                        tvTakerName.getText().toString(),
                        tvTakerPhone.getText().toString(),
                        tvTakerAddress.getText().toString());
                viewModel.getInvoiceliveData().observe(InvoiceActivity.this, new Observer<BaseDto<String>>() {
                    @Override
                    public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                        hideLoadingDialog();
                        if (stringBaseDto.isSuccess()) {
                            ToastUitl.showImageToastSuccess("操作成功");
                            //设置返回数据
                            Intent intent = new Intent();
                            intent.putExtra("data", stringBaseDto.getData());
                            intent.putExtra("invoiceType", String.valueOf(type));
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                        }
                    }
                });
            }

        });
        rlOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llOne.setVisibility(View.VISIBLE);
                llTwo.setVisibility(View.VISIBLE);
                ivOne.setImageResource(R.drawable.image_checked);
                ivTwo.setImageResource(R.drawable.image_uncheck);
                type = 1;
            }
        });
        rlTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llOne.setVisibility(View.GONE);
                llTwo.setVisibility(View.GONE);
                ivOne.setImageResource(R.drawable.image_uncheck);
                ivTwo.setImageResource(R.drawable.image_checked);
                type = 2;
            }
        });
    }


}
