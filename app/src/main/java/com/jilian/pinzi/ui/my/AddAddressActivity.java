package com.jilian.pinzi.ui.my;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.AddressDto;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.PhoneUtils;
import com.jilian.pinzi.utils.PinziDialogUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.citypickerview.CityPickerView;


/**
 * @author ningpan
 * @since 2018/11/6 11:02 <br>
 * description: 新增新收货人 Activity
 */
public class AddAddressActivity extends BaseActivity {
    private MyViewModel viewModel;
    private EditText etName;
    private EditText etPhone;
    private EditText etPostalcode;
    private TextView tvAdress;
    private EditText tvStreet;
    private TextView tvOk;
    private CheckBox cChecked;
    private String id;//地址id

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

    public static void startActivity(Context context, AddressDto addressDto) {
        Intent intent = new Intent(context, AddAddressActivity.class);
        intent.putExtra("address", addressDto);
        context.startActivity(intent);
    }

    private AddressDto address;

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_add_address;
    }

    @Override
    public void initView() {
        etName = (EditText) findViewById(R.id.et_name);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etPostalcode = (EditText) findViewById(R.id.et_postalcode);
        tvAdress = (TextView) findViewById(R.id.tv_adress);
        tvStreet = (EditText) findViewById(R.id.tv_street);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        cChecked = (CheckBox) findViewById(R.id.c_checked);
        address = (AddressDto) getIntent().getSerializableExtra("address");
        if (address == null) {
            setNormalTitle("新增收货地址", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else {
            tvOk.setEnabled(true);
            tvOk.setBackgroundResource(R.drawable.shape_btn_login_normal);
            id = address.getId();
            initEditData();
            setNormalTitle("编辑收货地址", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

//            setNormalTitle("编辑收货地址", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            }, "删除", v -> {
//                showDeleteOrderDialog(id);
//            });

        }
        findViewById(R.id.v_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishThis();
            }
        });


    }

    /**
     * 关闭当前界面
     */
    private void finishThis() {
        //数据是使用Intent返回
        Intent intent = new Intent();
        //设置返回数据
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initEditData() {
        etName.setText(address.getName());
        etPhone.setText(address.getPhone());
        etPostalcode.setText(address.getPostalCode());
        tvAdress.setText(address.getArea());
        tvStreet.setText(address.getAddress());
        cChecked.setChecked(address.getIsDefault() == 0 ? false : true);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        tvAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLoadingDialog().showDialog();
                new Thread() {
                    @Override
                    public void run() {
                        CityPickerView pickerView = getPickerInstance();
                        Message message = Message.obtain();
                        message.obj = pickerView;
                        message.what = 1001;
                        handler.sendMessage(message);
                    }
                }.start();
            }
        });
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateBtnStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateBtnStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etPostalcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateBtnStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvStreet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateBtnStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(id)) {

                    addAdress();

                } else {
                    editAdress();
                }

            }
        });
    }

    /**
     * 编辑收获地址
     */
    private void editAdress() {
        if (etPhone.getText().toString().length() < 11 || !etPhone.getText().toString().startsWith("1")) {
            ToastUitl.showImageToastFail("请输入正确的手机号码");
            return;
        }
        if (!PhoneUtils.checkPhoneNo(etPhone.getText().toString())) {
            ToastUitl.showImageToastFail("请输入正确的手机号码");
            return;
        }
        getLoadingDialog().showDialog();
        viewModel.editUserAddress(etName.getText().toString(), etPhone.getText().toString(), etPostalcode.getText().toString(), tvAdress.getText().toString(), tvStreet.getText().toString(), cChecked.isChecked() ? 1 : 0, getLoginDto().getId(), id);
        viewModel.getAddUserAddressliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                getLoadingDialog().dismiss();
                if (stringBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                    ToastUitl.showImageToastSuccess("编辑收货人成功");
                    finish();
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }

            }
        });
    }

    /**
     * 添加收获地址
     */
    private void addAdress() {
        if (etPhone.getText().toString().length() < 11 || !etPhone.getText().toString().startsWith("1")) {
            ToastUitl.showImageToastFail("请输入正确的手机号码");
            return;
        }
        if (!PhoneUtils.checkPhoneNo(etPhone.getText().toString())) {
            ToastUitl.showImageToastFail("请输入正确的手机号码");
            return;
        }
        getLoadingDialog().showDialog();
        viewModel.addUserAddress(etName.getText().toString(), etPhone.getText().toString(), etPostalcode.getText().toString(), tvAdress.getText().toString(), tvStreet.getText().toString(), cChecked.isChecked() ? 1 : 0, getLoginDto().getId());
        viewModel.getAddUserAddressliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                getLoadingDialog().dismiss();
                if (stringBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                    ToastUitl.showImageToastSuccess("添加收货人成功");
                    finishThis();
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());

                }

            }
        });
    }

    /**
     * 更新按钮的状态
     */
    private void updateBtnStatus() {
        if (TextUtils.isEmpty(etName.getText().toString()) ||
                TextUtils.isEmpty(etPhone.getText().toString()) ||
                TextUtils.isEmpty(etPostalcode.getText().toString()) ||
                TextUtils.isEmpty(tvAdress.getText().toString()) ||
                TextUtils.isEmpty(tvStreet.getText().toString())) {
            tvOk.setEnabled(false);
            tvOk.setBackgroundResource(R.drawable.shape_btn_login_dark);

        } else {
            tvOk.setEnabled(true);
            tvOk.setBackgroundResource(R.drawable.shape_btn_login_normal);
        }
    }

    private String province;
    private String city;
    private String area;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1001:
                    getLoadingDialog().dismiss();
                    CityPickerView pickerView = (CityPickerView) msg.obj;
                    pickerView.showCityPicker();
                    //监听选择点击事件及返回结果
                    pickerView.setOnCityItemClickListener(new OnCityItemClickListener() {
                        @Override
                        public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {

                            //省份
                            if (province != null) {
                                AddAddressActivity.this.province = province.getName();
                            }

                            //城市
                            if (city != null) {
                                AddAddressActivity.this.city = city.getName();
                            }

                            //地区
                            if (district != null) {
                                AddAddressActivity.this.area = district.getName();
                            }
                            tvAdress.setText(province.getName() + city.getName() + district.getName());

                        }

                        @Override
                        public void onCancel() {
                            ToastUitl.showImageToastFail("已取消");
                        }
                    });
                    break;
            }
        }
    };

    /**
     * 删除订单
     */
    public void showDeleteOrderDialog(String id)

    {
        Dialog dialog = PinziDialogUtils.getDialogNotTouchOutside(this, R.layout.dialog_delete_tips);
        TextView tvNo = (TextView) dialog.findViewById(R.id.tv_no);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tv_ok);

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                deleteAdress(id);

            }
        });
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 删除地址
     */
    private void deleteAdress(String id) {
        getLoadingDialog().showDialog();
        viewModel.deleteUserAddress(id);
        viewModel.getAddUserAddressliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                getLoadingDialog().dismiss();
                if (stringBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                    ToastUitl.showImageToastSuccess("删除成功");
                    getLoadingDialog().showDialog();
                    finish();
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }

            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishThis();
        }
        return super.onKeyDown(keyCode, event);
    }
}
