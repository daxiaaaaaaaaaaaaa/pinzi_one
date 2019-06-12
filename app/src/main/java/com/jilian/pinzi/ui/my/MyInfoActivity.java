package com.jilian.pinzi.ui.my;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.common.dto.PersonalDto;
import com.jilian.pinzi.common.dto.UpdatePersonInfoDto;
import com.jilian.pinzi.dialog.BaseNiceDialog;
import com.jilian.pinzi.dialog.NiceDialog;
import com.jilian.pinzi.dialog.ViewConvertListener;
import com.jilian.pinzi.dialog.ViewHolder;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.ui.viewmodel.UserViewModel;
import com.jilian.pinzi.utils.DateUtil;
import com.jilian.pinzi.utils.PermissionsObserver;
import com.jilian.pinzi.utils.SPUtil;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.utils.selectphoto.SelectPhotoUtils;
import com.jilian.pinzi.views.CircularImageView;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.jilian.pinzi.Constant.FINALVALUE.FILE_PROVIDER;

/**
 * 个人信息
 */
public class MyInfoActivity extends BaseActivity {
    private MyViewModel viewModel;
    private CircularImageView ivHead;
    private EditText etNickName;
    private ImageView ivBoy;
    private ImageView ivGirl;
    private TextView birthday;
    private TextView tvSelectCity;
    private EditText etJob;
    private TextView phone;
    private String province = "";
    private String city = "";
    private String area = "";
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
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_myinfo;
    }

    @Override
    public void initView() {
        setNormalTitle("个人信息", v -> finish());
        ivHead = (CircularImageView) findViewById(R.id.iv_head);
        etNickName = (EditText) findViewById(R.id.et_nick_name);
        ivBoy = (ImageView) findViewById(R.id.iv_boy);
        ivGirl = (ImageView) findViewById(R.id.iv_girl);
        birthday = (TextView) findViewById(R.id.birthday);
        tvSelectCity = (TextView) findViewById(R.id.tv_select_city);
        etJob = (EditText) findViewById(R.id.et_job);
        phone = (TextView) findViewById(R.id.phone);
        setrightTitle("保存", "#FFFFFF", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveInfoData();
            }
        });
        if(!TextUtils.isEmpty(etNickName.getText().toString())){
            etNickName.setSelection(etNickName.getText().toString().length());//将光标移至文字末尾
        }

        if(!TextUtils.isEmpty(etJob.getText().toString())){
            etJob.setSelection(etJob.getText().toString().length());//将光标移至文字末尾
        }


    }

    /**
     * 保存信息
     */
    private void saveInfoData() {
        getLoadingDialog().showDialog();
        viewModel.updatePersonalMessage(headImg, etNickName.getText().toString(), sex, etJob.getText().toString(), province, city, getLoginDto().getId(), birthday.getText().toString(), area);
        viewModel.getUpdatePersonalliveData().observe(this, new Observer<BaseDto<UpdatePersonInfoDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<UpdatePersonInfoDto> stringBaseDto) {
                getLoadingDialog().dismiss();
                if (stringBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                    ToastUitl.showImageToastSuccess(stringBaseDto.getMsg());
                    //finish();
                    updateLoginDto();
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }


            }
        });
    }

    /**
     * 更新登录信息
     */
    private void updateLoginDto() {
        LoginDto dto = getLoginDto();
        if (!TextUtils.isEmpty(etNickName.getText().toString())) {
            dto.setName(etNickName.getText().toString());
        }
        if (!TextUtils.isEmpty(headImg)) {
            dto.setHeadImg(headImg);
        }
        SPUtil.putData(Constant.SP_VALUE.SP, Constant.SP_VALUE.LOGIN_DTO, dto);

    }

    @Override
    public void initData() {
        getMyInfoData();
        initCustomTimePicker();
    }

    TimePickerView pvCustomTime;

    /**
     * 初始化时间数据
     */
    private void initCustomTimePicker() {
        /**
         * @description
         *
         * 注意事项：
         * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针.
         * 具体可参考demo 里面的两个自定义layout布局。
         * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1918, 1, 23);
        Calendar endDate = Calendar.getInstance();

        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                birthday.setText(DateUtil.dateToString(DateUtil.DATE_FORMAT_, date));
            }
        })
                /*.setType(TimePickerView.Type.ALL)//default is all
                .setCancelText("Cancel")
                .setSubmitText("Sure")
                .setContentTextSize(18)
                .setTitleSize(20)
                .setTitleText("Title")
                .setTitleColor(Color.BLACK)
               /*.setDividerColor(Color.WHITE)//设置分割线的颜色
                .setTextColorCenter(Color.LTGRAY)//设置选中项的颜色
                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
                .setTitleBgColor(Color.DKGRAY)//标题背景颜色 Night mode
                .setBgColor(Color.BLACK)//滚轮背景颜色 Night mode
                .setSubmitColor(Color.WHITE)
                .setCancelColor(Color.WHITE)*/
                /*.animGravity(Gravity.RIGHT)// default is center*/
                .setDate(selectedDate)
                .setRangDate(startDate, selectedDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        final TextView tvCancel = (TextView) v.findViewById(R.id.tv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setContentTextSize(18)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(40, 0, -40, 0, 0, -0)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFFe0e0e0)
                .setLineSpacingMultiplier(2f)
                .build();
    }

    /**
     * 获取个人信息
     */
    private void getMyInfoData() {
        viewModel.queryMyInfo(getLoginDto().getId());
        viewModel.getPersonalliveData().observe(this, new Observer<BaseDto<PersonalDto>>() {
            @Override
            public void onChanged(@Nullable BaseDto<PersonalDto> personalDtoBaseDto) {
                if (personalDtoBaseDto.getCode() == Constant.Server.SUCCESS_CODE)
                {
                    if (!TextUtils.isEmpty(personalDtoBaseDto.getData().getName())) {
                        etNickName.setText(personalDtoBaseDto.getData().getName());
                    }
                    if (!TextUtils.isEmpty(personalDtoBaseDto.getData().getBirthday())) {
                        long time = Long.parseLong(personalDtoBaseDto.getData().getBirthday());
                        birthday.setText(DateUtil.dateToString(DateUtil.DATE_FORMAT_, new Date(time)));
                    }
                    if (!TextUtils.isEmpty(personalDtoBaseDto.getData().getProvince()) && !TextUtils.isEmpty(personalDtoBaseDto.getData().getCity())) {
                        tvSelectCity.setText(personalDtoBaseDto.getData().getProvince() + personalDtoBaseDto.getData().getCity()+personalDtoBaseDto.getData().getArea());
                    }
                    if (!TextUtils.isEmpty(personalDtoBaseDto.getData().getProfession())) {

                        etJob.setText(personalDtoBaseDto.getData().getProfession());
                    }

                    if (!TextUtils.isEmpty(getLoginDto().getPhone())) {
                        phone.setText(getLoginDto().getPhone());
                    }


                    if ("0".equals(personalDtoBaseDto.getData().getSex())) {

                        ivBoy.setImageResource(R.drawable.image_checked);
                        ivGirl.setImageResource(R.drawable.image_uncheck);
                        sex = 0;
                    } else if ("1".equals(personalDtoBaseDto.getData().getSex())) {
                        ivBoy.setImageResource(R.drawable.image_uncheck);
                        ivGirl.setImageResource(R.drawable.image_checked);
                        sex = 1;
                    }
                    Glide
                            .with(MyInfoActivity.this)
                            .load(personalDtoBaseDto.getData().getHeadImg())
                            .into(ivHead);
                    if(!TextUtils.isEmpty(etNickName.getText().toString())){
                        etNickName.setSelection(etNickName.getText().toString().length());//将光标移至文字末尾
                    }

                    if(!TextUtils.isEmpty(etJob.getText().toString())){
                        etJob.setSelection(etJob.getText().toString().length());//将光标移至文字末尾
                    }


                }
            }
        });
    }

    private Integer sex = 0;

    @Override
    public void initListener() {
        ivGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivBoy.setImageResource(R.drawable.image_uncheck);
                ivGirl.setImageResource(R.drawable.image_checked);
                sex = 1;
            }
        });
        ivBoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivBoy.setImageResource(R.drawable.image_checked);
                ivGirl.setImageResource(R.drawable.image_uncheck);
                sex = 0;
            }
        });
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBirthDay();
            }
        });
        ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPhotoTypeDialog();
            }
        });
        tvSelectCity.setOnClickListener(new View.OnClickListener() {
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
    }

    /**
     * 选择生日
     */
    private void selectBirthDay() {

        pvCustomTime.show(); //弹出自定义时间选择器


    }

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
                                MyInfoActivity.this.province = province.getName();
                            }

                            //城市
                            if (city != null) {
                                MyInfoActivity.this.city = city.getName();
                            }

                            //地区
                            if (district != null) {
                                MyInfoActivity.this.area = district.getName();
                            }
                            tvSelectCity.setText(province.getName() + city.getName() + district.getName());

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
     * 选中照片
     */
    private void showSelectPhotoTypeDialog() {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_photo_select)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        dialog.setOutCancel(false);
                        Button btnTakingPictures = (Button) holder.getView(R.id.btn_taking_pictures);
                        Button btnPhotoAlbum = (Button) holder.getView(R.id.btn_photo_album);
                        Button btnCancel = (Button) holder.getView(R.id.btn_cancel);
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        //拍照
                        btnTakingPictures.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                RxPermissions.getInstance(MyInfoActivity.this).request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new PermissionsObserver() {
                                    @Override
                                    protected void onGetPermissionsSuccess() {
                                        SelectPhotoUtils.fromCapture(MyInfoActivity.this, FILE_PROVIDER, FROM_CAPTURE);
                                    }

                                    @Override
                                    protected void onGetPermissionsSuccessFailuer() {
                                        ToastUitl.showImageToastFail("相机权限被拒绝，无法使用拍照功能");
                                    }
                                });

                            }
                        });
                        //相册
                        btnPhotoAlbum.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();

                                RxPermissions.getInstance(MyInfoActivity.this).request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new PermissionsObserver() {
                                    @Override
                                    protected void onGetPermissionsSuccess() {
                                        SelectPhotoUtils.fromAlbum(MyInfoActivity.this, FILE_PROVIDER, 1, FROM_ALBUM);
                                    }

                                    @Override
                                    protected void onGetPermissionsSuccessFailuer() {
                                        ToastUitl.showImageToastFail("相机权限被拒绝，无法使用拍照功能");
                                    }
                                });

                            }

                        });

                    }
                })
                .setShowBottom(true)
                .show(getSupportFragmentManager());
    }

    private String path;
    private String headImg;
    //相机
    private final int FROM_CAPTURE = 10001;
    //相册
    private final int FROM_ALBUM = 10002;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //图库
                case FROM_ALBUM:
                    List<Uri> uriList = SelectPhotoUtils.albumResult(data);
                    Uri uri = uriList.get(0);
                    path = SelectPhotoUtils.getRealPathFromURI(this, uri);
                    uploadFile();
                    break;
                //相机
                case FROM_CAPTURE:
                    path = SelectPhotoUtils.capturePathResult();
                    //ivHead.setImageBitmap(BitmapFactory.decodeFile(path));
                    uploadFile();
                    break;
            }

        }
    }

    /**
     * 上传图片
     */
    private void uploadFile() {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        List<File> fileList = new ArrayList<>();
        fileList.add(new File(path));
        getLoadingDialog().showDialog();
        userViewModel.photoImg(1, fileList);
        userViewModel.getPhotoImageliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                getLoadingDialog().dismiss();
                if (stringBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                    ivHead.setImageBitmap(BitmapFactory.decodeFile(path));
                    headImg = stringBaseDto.getData();
                    saveInfoData();
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }
            }
        });
    }
}
