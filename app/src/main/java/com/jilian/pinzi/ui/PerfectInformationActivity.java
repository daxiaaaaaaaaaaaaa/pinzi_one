package com.jilian.pinzi.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.PostEvaluationPhotoAdapter;
import com.jilian.pinzi.adapter.PublishFriendsAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.CommonActivity;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.common.dto.PhotoDto;
import com.jilian.pinzi.dialog.BaseNiceDialog;
import com.jilian.pinzi.dialog.NiceDialog;
import com.jilian.pinzi.dialog.ViewConvertListener;
import com.jilian.pinzi.dialog.ViewHolder;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.listener.PhotpItemClickListener;
import com.jilian.pinzi.ui.friends.PublishFriendsActivity;
import com.jilian.pinzi.ui.friends.SelectImageActivity;
import com.jilian.pinzi.ui.viewmodel.UserViewModel;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.ImageUtils;
import com.jilian.pinzi.utils.PermissionsObserver;
import com.jilian.pinzi.utils.PhoneUtils;
import com.jilian.pinzi.utils.SPUtil;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.utils.selectphoto.SelectPhotoUtils;
import com.jilian.pinzi.views.CustomerGridLayManager;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jilian.pinzi.Constant.FINALVALUE.FILE_PROVIDER;

/**
 * 完善信息
 */
public class PerfectInformationActivity extends BaseActivity implements PhotpItemClickListener {

    private TextView tvArea;
    private EditText tvName;
    private EditText tvContact;
    private RelativeLayout rlPerfectInfoBindPhone;
    private TextView tvLinkphone;
    private TextView tvOk;


    private CustomerGridLayManager gridOne;

    private CustomerGridLayManager gridTwo;
    //相机
    private final int FROM_CAPTURE = 10001;
    //相册
    private final int FROM_ALBUM = 10002;
    private RecyclerView rvOne;
    private RecyclerView rvTwo;


    private PostEvaluationPhotoAdapter oneAdapter;

    private PostEvaluationPhotoAdapter twoAdapter;

    private List<PhotoDto> oneDatas;
    private List<PhotoDto> twoDatas;

    private UserViewModel userViewModel;
    private String province;
    private String city;
    private String area;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PinziApplication.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PinziApplication.removeActivity(this);
       // SPUtil.clearData(Constant.SP_VALUE.SP);
    }

    @Override
    protected void createViewModel() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_perfectinformation;
    }

    @Override
    public void initView() {
        setCenterTitle("完善信息", "#FFFFFF");
        setleftImage(R.drawable.image_back, true, null);

        rvOne = (RecyclerView) findViewById(R.id.rv_one);
        rvTwo = (RecyclerView) findViewById(R.id.rv_two);
        setTitleBg(R.color.color_black);
        tvArea = (TextView) findViewById(R.id.tv_area);
        tvName = (EditText) findViewById(R.id.tv_name);
        tvContact = (EditText) findViewById(R.id.tv_contact);
        rlPerfectInfoBindPhone = (RelativeLayout) findViewById(R.id.rl_perfect_info_bind_phone);
        tvLinkphone = (TextView) findViewById(R.id.tv_linkphone);
        tvOk = (TextView) findViewById(R.id.tv_ok);

        gridOne = new CustomerGridLayManager(this, 3);
        gridTwo = new CustomerGridLayManager(this, 3);

        gridOne.setCanScrollVertically(false);
        gridTwo.setCanScrollVertically(false);

        //解决卡顿 滑动
        gridOne.setSmoothScrollbarEnabled(true);
        gridOne.setAutoMeasureEnabled(true);

        gridTwo.setSmoothScrollbarEnabled(true);
        gridTwo.setAutoMeasureEnabled(true);


        rvOne.setHasFixedSize(true);
        rvOne.setNestedScrollingEnabled(false);


        rvTwo.setHasFixedSize(true);
        rvTwo.setNestedScrollingEnabled(false);


        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, DisplayUtil.dip2px(this, 10));//右间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION, DisplayUtil.dip2px(this, 10));//下间距
        rvOne.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        rvOne.setLayoutManager(gridOne);

        rvTwo.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        rvTwo.setLayoutManager(gridTwo);

        //营业执照
        oneDatas = new ArrayList<>();
        oneDatas.add(new PhotoDto(1, ""));
        oneAdapter = new PostEvaluationPhotoAdapter(this, oneDatas, this);
        rvOne.setAdapter(oneAdapter);


        twoDatas = new ArrayList<>();
        twoDatas.add(new PhotoDto(2, ""));
        twoAdapter = new PostEvaluationPhotoAdapter(this, twoDatas, this);
        rvTwo.setAdapter(twoAdapter);


    }

    @Override
    public void initData() {


    }


    @Override
    public void initListener() {
        tvArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(tvArea.getText().toString())
                        || TextUtils.isEmpty(tvName.getText().toString())
                        || TextUtils.isEmpty(tvContact.getText().toString()) || TextUtils.isEmpty(tvLinkphone.getText().toString())) {
                    ToastUitl.showImageToastFail("请完善信息后在提交");
                    return;
                }
                if (tvLinkphone.getText().toString().length() < 11 || !tvLinkphone.getText().toString().startsWith("1")) {
                    ToastUitl.showImageToastFail("请输入正确的手机号码");
                    return;
                }
                if (!PhoneUtils.checkPhoneNo(tvLinkphone.getText().toString())) {
                    ToastUitl.showImageToastFail("请输入正确的手机号码");
                    return;
                }
                uploadOneFile();
            }
        });
    }

    private String imgUrl;//    true     file   图片地址（营业执照）
    private String storeImg;//true     string   门店照片



    /**
     * 上传营业执照
     */
    private void uploadOneFile() {
        if (oneDatas.size() == 1) {
            ToastUitl.showImageToastFail("请拍照或者从相册中选择图片上传");
            return;
        }
        List<File> fileList = new ArrayList<>();
        for (int i = 0; i < oneDatas.size() - 1; i++) {
            fileList.add(new File(oneDatas.get(i).getUrl()));
        }
        getLoadingDialog().showDialog();
        userViewModel.photoImg(1, fileList);
        userViewModel.getPhotoImageliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                getLoadingDialog().dismiss();
                if (stringBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                    //完善信息

                    uploadTwoFile(stringBaseDto.getData());
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }
            }
        });
    }

    /**
     * 上传门店照片
     *
     * @param url 营业执照的URL
     */
    private void uploadTwoFile(String url) {
        if (twoDatas.size() == 1) {
            ToastUitl.showImageToastFail("请拍照或者从相册中选择图片上传");
            return;
        }
        List<File> fileList = new ArrayList<>();
        for (int i = 0; i < twoDatas.size() - 1; i++) {
            fileList.add(new File(twoDatas.get(i).getUrl()));
        }
        getLoadingDialog().showDialog();
        userViewModel.photoImg(1, fileList);
        userViewModel.getPhotoImageliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                getLoadingDialog().dismiss();
                if (stringBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                    //完善信息
                    perfectInformation(url, stringBaseDto.getData());
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }
            }
        });
    }

    /**
     * 掉完善信息接口
     *
     * @param oneUrl 营业执照
     * @param twoUrl 门店
     */
    private void perfectInformation(String oneUrl, String twoUrl) {

        userViewModel.perfectInformation(province, city, area, tvName.getText().toString(), tvContact.getText().toString(), tvLinkphone.getText().toString(), getUserId(), oneUrl,twoUrl);
        userViewModel.getPerfectInformationliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                if (stringBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
//                    startActivity(new Intent(PerfectInformationActivity.this, UserCheckActivity.class));
                    ToastUitl.showImageToastSuccess("提交成功");
                    startActivity(new Intent(PerfectInformationActivity.this, MainActivity.class));
                    finish();
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }
            }
        });

    }


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
                                RxPermissions.getInstance(PerfectInformationActivity.this).request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new PermissionsObserver() {
                                    @Override
                                    protected void onGetPermissionsSuccess() {
                                        SelectPhotoUtils.fromCapture(PerfectInformationActivity.this, FILE_PROVIDER, FROM_CAPTURE);
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

                                RxPermissions.getInstance(PerfectInformationActivity.this).request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new PermissionsObserver() {
                                    @Override
                                    protected void onGetPermissionsSuccess() {
                                        if (type == 1) {
                                            SelectPhotoUtils.fromAlbum(PerfectInformationActivity.this, FILE_PROVIDER, 4 - oneDatas.size(), FROM_ALBUM);
                                        }
                                        if (type == 2) {
                                            SelectPhotoUtils.fromAlbum(PerfectInformationActivity.this, FILE_PROVIDER, 4 - twoDatas.size(), FROM_ALBUM);
                                        }

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
    private int type;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //图库
                case FROM_ALBUM:

                    List<Uri> uriList = SelectPhotoUtils.albumResult(data);
                    if (type == 1) {
                        for (int i = 0; i < uriList.size(); i++) {
                            Uri uri = uriList.get(i);
                            path = SelectPhotoUtils.getRealPathFromURI(this, uri);
                            oneDatas.add(0, new PhotoDto(this.type, path));
                        }
                        oneAdapter.notifyDataSetChanged();
                    }
                    if (type == 2) {
                        for (int i = 0; i < uriList.size(); i++) {
                            Uri uri = uriList.get(i);
                            path = SelectPhotoUtils.getRealPathFromURI(this, uri);
                            twoDatas.add(0, new PhotoDto(this.type, path));
                        }
                        twoAdapter.notifyDataSetChanged();
                    }

                    break;
                //相机
                case FROM_CAPTURE:
                    if (type == 1) {
                        path = SelectPhotoUtils.capturePathResult();
                        oneDatas.add(0, new PhotoDto(this.type, path));
                        oneAdapter.notifyDataSetChanged();
                    }
                    if (type == 2) {
                        path = SelectPhotoUtils.capturePathResult();
                        twoDatas.add(0, new PhotoDto(this.type, path));
                        twoAdapter.notifyDataSetChanged();
                    }

                    break;
            }

        }
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
                                PerfectInformationActivity.this.province = province.getName();
                            }

                            //城市
                            if (city != null) {
                                PerfectInformationActivity.this.city = city.getName();
                            }

                            //地区
                            if (district != null) {
                                PerfectInformationActivity.this.area = district.getName();
                            }
                            tvArea.setText(province.getName() + city.getName() + district.getName());

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


    //不要删这行代码

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
        //super.onSaveInstanceState(outState);
    }


    @Override
    public void onItemClick(View view, int position, int type) {
        this.type = type;
        if (type == 1) {
            if (position == oneDatas.size() - 1) {
                if (oneDatas.size() == 4) {
                    return;
                } else {

                    showSelectPhotoTypeDialog();
                }
            }
        }
        if (type == 2) {
            if (position == twoDatas.size() - 1) {
                if (twoDatas.size() == 4) {
                    return;
                } else {
                    showSelectPhotoTypeDialog();
                }
            }
        }

    }

    @Override
    public void close(int position, int type) {

        if (type == 1) {
            oneDatas.remove(position);
            oneAdapter = new PostEvaluationPhotoAdapter(this, oneDatas, this);
            rvOne.setAdapter(oneAdapter);
        }
        if (type == 2) {
            twoDatas.remove(position);
            twoAdapter = new PostEvaluationPhotoAdapter(this, twoDatas, this);
            rvTwo.setAdapter(twoAdapter);
        }
    }
}
