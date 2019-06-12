package com.jilian.pinzi.ui.my;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.contrarywind.view.WheelView;
import com.jilian.pinzi.Constant;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.MyCommentPhotoAdapter;
import com.jilian.pinzi.adapter.RefundReasonArrayWheelAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.MyOrderGoodsInfoDto;
import com.jilian.pinzi.common.dto.RefundReasonDto;
import com.jilian.pinzi.dialog.BaseNiceDialog;
import com.jilian.pinzi.dialog.NiceDialog;
import com.jilian.pinzi.dialog.ViewConvertListener;
import com.jilian.pinzi.dialog.ViewHolder;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.ui.viewmodel.UserViewModel;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.PermissionsObserver;
import com.jilian.pinzi.utils.PhoneUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.utils.selectphoto.SelectPhotoUtils;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static com.jilian.pinzi.Constant.FINALVALUE.FILE_PROVIDER;
public class ApplyServiceActivity extends BaseActivity implements CustomItemClickListener, MyCommentPhotoAdapter.ClosePhotonListener{
    private RelativeLayout rlSelect;
    private EditText tvContent;
    private TextView tvCount;
    private RecyclerView recyclerView;
    private EditText etName;
    private EditText etPhone;
    private TextView tvOk;
    private MyViewModel viewModel;
    private  List<RefundReasonDto> reasonDtoList;
    private TextView tvReason;
    private GridLayoutManager gridLayoutManager;
    private List<String> datas;
    private MyCommentPhotoAdapter adapter;
    //相机
    private final int FROM_CAPTURE = 10001;
    //相册
    private final int FROM_ALBUM = 10002;

    private UserViewModel userViewModel;
    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_applyservice;
    }

    @Override
    public void initView() {
        rlSelect = (RelativeLayout) findViewById(R.id.rl_select);
        tvContent = (EditText) findViewById(R.id.tv_content);
        tvCount = (TextView) findViewById(R.id.tv_count);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        etName = (EditText) findViewById(R.id.et_name);
        etPhone = (EditText) findViewById(R.id.et_phone);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        tvReason = (TextView) findViewById(R.id.tv_reason);
        setNormalTitle("申请退货", v -> finish());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        gridLayoutManager = new GridLayoutManager(this, 3);
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, DisplayUtil.dip2px(this, 10));//右间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION, DisplayUtil.dip2px(this, 10));//下间距
        recyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        recyclerView.setLayoutManager(gridLayoutManager);
        datas = new ArrayList<>();
        datas.add("1");
        adapter = new MyCommentPhotoAdapter(this, datas, this, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {
        showLoadingDialog();
        viewModel.getRefundReason();
        viewModel.getRefundReasonliveData().observe(this, new Observer<BaseDto<List<RefundReasonDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<RefundReasonDto>> listBaseDto) {
                hideLoadingDialog();
                if(listBaseDto.isSuccess()){
                    reasonDtoList = listBaseDto.getData();
                }
                else{
                    ToastUitl.showImageToastFail(listBaseDto.getMsg());
                }
            }
        });
    }

    @Override
    public void initListener() {
        tvContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(tvContent.getText().toString())) {
                    tvCount.setText("0/500");
                } else if (tvContent.getText().toString().length() <= 500) {
                    tvCount.setText(tvContent.getText().toString().length() + "/500");
                } else if (tvContent.getText().toString().length() > 500) {
                    tvContent.setText(tvContent.getText().toString().substring(0, 501));
                    tvCount.setText(tvContent.getText().toString().length() + "/500");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(id)){
                    ToastUitl.showImageToastFail("请先选择退货原因");
                    return;
                }
                if(TextUtils.isEmpty(tvContent.getText().toString())){
                    ToastUitl.showImageToastFail("请输入问题描述");
                    return;
                }
                if(TextUtils.isEmpty(etName.getText().toString())){
                    ToastUitl.showImageToastFail("请输入联系人姓名");
                    return;
                }
                if(TextUtils.isEmpty(etPhone.getText().toString())){
                    ToastUitl.showImageToastFail("请输入联系人电话");
                    return;
                }
                MyOrderGoodsInfoDto dto = (MyOrderGoodsInfoDto) getIntent().getSerializableExtra("dto");
                if(!EmptyUtils.isNotEmpty(dto)){
                    ToastUitl.showImageToastFail("未获取到订单信息");
                    return;
                }
                if(!PhoneUtils.checkPhoneNo(etPhone.getText().toString())){
                    ToastUitl.showImageToastFail("请输入正确的手机号码");
                    return;
                }
                addApplyRefund(dto);


            }
        });
        rlSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRefundReasonDialog();
            }
        });
    }
    private String id;
    /**
     * 退货原因对话框
     */
    private void showRefundReasonDialog() {
        if(!EmptyUtils.isNotEmpty(reasonDtoList)){
            ToastUitl.showImageToastFail("未获取到退货理由");
            return;
        }
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_select_courier)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        dialog.setOutCancel(false);
                        WheelView wheelview = (WheelView) holder.getView(R.id.wheelview);
                        wheelview.setCyclic(false);
                        TextView tvCancel = (TextView) holder.getView(R.id.tv_cancel);
                        TextView tvFinish = (TextView) holder.getView(R.id.tv_finish);
                        wheelview.setAdapter(new RefundReasonArrayWheelAdapter(reasonDtoList));

                        tvFinish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                tvReason.setText(reasonDtoList.get(wheelview.getCurrentItem()).getContent());
                               id = reasonDtoList.get(wheelview.getCurrentItem()).getId();
                                dialog.dismiss();
                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        wheelview.setCurrentItem(0);
                    }
                })
                .setShowBottom(true)
                .show(getSupportFragmentManager());
    }

    @Override
    public void close(int position) {
        datas.remove(position);
        adapter = new MyCommentPhotoAdapter(this, datas, this, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        if (position == datas.size() - 1) {
            if (datas.size() == 4) {
                return;
            } else {
                showSelectPhotoTypeDialog();
            }
        }

    }

    /**
     * 评价
     */
    private void addApplyRefund(MyOrderGoodsInfoDto dto) {
        if (datas.size() > 1) {
            //上传照片
            uploadFile(dto);
        } else {
            applyRefund(dto,null);
        }

    }

    /**
     * 申请退货
     * @param imgUrl
     */
    private void applyRefund(MyOrderGoodsInfoDto dto,String imgUrl) {

        showLoadingDialog();
        viewModel.applyRefund(dto.getOrderId(),String.valueOf(dto.getGoodsId()),dto.getQuantity(),
                id,tvContent.getText().toString(),imgUrl,etName.getText().toString(),etPhone.getText().toString());
        viewModel.getApplyRefundLiveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                hideLoadingDialog();
                if(stringBaseDto.isSuccess()){
                    ToastUitl.showImageToastSuccess("申请成功");
                    finish();
                }
                else{
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }
            }
        });

    }

    /**
     * 上传图片
     */
    private void uploadFile(MyOrderGoodsInfoDto dto) {
        if (datas.size() == 1) {
            ToastUitl.showImageToastFail("请拍照或者从相册中选择图片上传");
            return;
        }
        List<File> fileList = new ArrayList<>();
        for (int i = 0; i < datas.size() - 1; i++) {
            fileList.add(new File(datas.get(i)));
        }
        getLoadingDialog().showDialog();
        userViewModel.photoImg(1, fileList);
        userViewModel.getPhotoImageliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                hideLoadingDialog();
                if (stringBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                    //提交
                    applyRefund(dto,stringBaseDto.getData());
                } else {
                    getLoadingDialog().dismiss();
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
                                RxPermissions.getInstance(ApplyServiceActivity.this).request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new PermissionsObserver() {
                                    @Override
                                    protected void onGetPermissionsSuccess() {
                                        SelectPhotoUtils.fromCapture(ApplyServiceActivity.this, FILE_PROVIDER, FROM_CAPTURE);
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

                                RxPermissions.getInstance(ApplyServiceActivity.this).request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new PermissionsObserver() {
                                    @Override
                                    protected void onGetPermissionsSuccess() {
                                        SelectPhotoUtils.fromAlbum(ApplyServiceActivity.this, FILE_PROVIDER, 4 - datas.size(), FROM_ALBUM);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //图库
                case FROM_ALBUM:
                    List<Uri> uriList = SelectPhotoUtils.albumResult(data);
                    for (int i = 0; i < uriList.size(); i++) {
                        Uri uri = uriList.get(i);
                        path = SelectPhotoUtils.getRealPathFromURI(this, uri);
                        datas.add(0, path);
                    }
                    adapter.notifyDataSetChanged();
                    break;
                //相机
                case FROM_CAPTURE:
                    path = SelectPhotoUtils.capturePathResult();
                    datas.add(0, path);
                    adapter.notifyDataSetChanged();
                    break;
            }

        }
    }
}
