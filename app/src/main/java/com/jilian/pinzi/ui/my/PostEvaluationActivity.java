package com.jilian.pinzi.ui.my;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.MyCommentPhotoAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.dialog.BaseNiceDialog;
import com.jilian.pinzi.dialog.NiceDialog;
import com.jilian.pinzi.dialog.ViewConvertListener;
import com.jilian.pinzi.dialog.ViewHolder;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.my.viewmdel.MyViewModel;
import com.jilian.pinzi.ui.viewmodel.UserViewModel;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.PermissionsObserver;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.utils.selectphoto.SelectPhotoUtils;
import com.jilian.pinzi.views.CustomerGridLayManager;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.jilian.pinzi.Constant.FINALVALUE.FILE_PROVIDER;

public class PostEvaluationActivity extends BaseActivity implements CustomItemClickListener, MyCommentPhotoAdapter.ClosePhotonListener {
    private RecyclerView recyclerView;
    private CustomerGridLayManager gridLayoutManager;
    private List<String> datas;
    private MyCommentPhotoAdapter adapter;
    //相机
    private final int FROM_CAPTURE = 10001;
    //相册
    private final int FROM_ALBUM = 10002;

    private ImageView ivOne;
    private ImageView tvTwo;
    private ImageView tvThree;
    private ImageView tvFour;
    private ImageView tvFive;
    private EditText tvContent;
    private ImageView ivIsHidden;
    private int isAnonymity;//是否匿名（0.否 1.是）
    private int grade;//评分
    private TextView tvOk;
    private TextView tvCount;
    private MyViewModel viewModel;
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
        return R.layout.activity_postevaluation;
    }

    @Override
    public void initView() {
        setNormalTitle("发布评价", v -> finish());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        gridLayoutManager = new CustomerGridLayManager(this, 3);
        gridLayoutManager.setCanScrollVertically(false);
        //解决卡顿 滑动
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        gridLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, DisplayUtil.dip2px(this, 10));//右间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION, DisplayUtil.dip2px(this, 10));//下间距
        recyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        recyclerView.setLayoutManager(gridLayoutManager);
        datas = new ArrayList<>();
        datas.add("1");
        adapter = new MyCommentPhotoAdapter(this, datas, this, this);
        recyclerView.setAdapter(adapter);
        //
        ivOne = (ImageView) findViewById(R.id.iv_one);
        tvTwo = (ImageView) findViewById(R.id.tv_two);
        tvThree = (ImageView) findViewById(R.id.tv_three);
        tvFour = (ImageView) findViewById(R.id.tv_four);
        tvFive = (ImageView) findViewById(R.id.tv_five);
        tvContent = (EditText) findViewById(R.id.tv_content);
        ivIsHidden = (ImageView) findViewById(R.id.iv_is_hidden);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        tvCount = (TextView) findViewById(R.id.tv_count);
    }

    @Override
    public void initData() {

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
                    tvCount.setText("0/100");
                } else if (tvContent.getText().toString().length() <= 100) {
                    tvCount.setText(tvContent.getText().toString().length() + "/100");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ivIsHidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAnonymity == 0) {
                    isAnonymity = 1;
                    ivIsHidden.setImageResource(R.drawable.image_chat_open);
                } else {
                    ivIsHidden.setImageResource(R.drawable.image_chat_turn_on);
                    isAnonymity = 0;
                }
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(tvContent.getText().toString())) {
                    ToastUitl.showImageToastFail("请输入评价内容");
                    return;
                }
                if(grade==0){
                    ToastUitl.showImageToastFail("请对商品评分");
                    return;
                }
                addEvaluate();
            }
        });
        ivOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grade = 1;
                ivOne.setImageResource(R.drawable.image_my_star_select);
                tvTwo.setImageResource(R.drawable.image_my_star_active);
                tvThree.setImageResource(R.drawable.image_my_star_active);
                tvFour.setImageResource(R.drawable.image_my_star_active);
                tvFive.setImageResource(R.drawable.image_my_star_active);
            }
        });
        tvTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grade = 2;
                ivOne.setImageResource(R.drawable.image_my_star_select);
                tvTwo.setImageResource(R.drawable.image_my_star_select);
                tvThree.setImageResource(R.drawable.image_my_star_active);
                tvFour.setImageResource(R.drawable.image_my_star_active);
                tvFive.setImageResource(R.drawable.image_my_star_active);
            }
        });
        tvThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grade = 3;
                ivOne.setImageResource(R.drawable.image_my_star_select);
                tvTwo.setImageResource(R.drawable.image_my_star_select);
                tvThree.setImageResource(R.drawable.image_my_star_select);
                tvFour.setImageResource(R.drawable.image_my_star_active);
                tvFive.setImageResource(R.drawable.image_my_star_active);
            }
        });
        tvFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grade = 4;
                ivOne.setImageResource(R.drawable.image_my_star_select);
                tvTwo.setImageResource(R.drawable.image_my_star_select);
                tvThree.setImageResource(R.drawable.image_my_star_select);
                tvFour.setImageResource(R.drawable.image_my_star_select);
                tvFive.setImageResource(R.drawable.image_my_star_active);
            }
        });
        tvFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grade = 5;
                ivOne.setImageResource(R.drawable.image_my_star_select);
                tvTwo.setImageResource(R.drawable.image_my_star_select);
                tvThree.setImageResource(R.drawable.image_my_star_select);
                tvFour.setImageResource(R.drawable.image_my_star_select);
                tvFive.setImageResource(R.drawable.image_my_star_select);
            }
        });

    }

    /**
     * 评价
     */
    private void addEvaluate() {
        if (datas.size() > 1) {
            //上传照片
            uploadFile();
        } else {
            addToComment(null);
        }

    }

    /**
     * 评价接口
     */
    private void addToComment(String imgUrl) {
        showLoadingDialog();
        viewModel.addEvaluate(getUserId(), getIntent().getStringExtra("orderId"), grade, tvContent.getText().toString(), imgUrl, isAnonymity);
        viewModel.getAddEvaluate().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                hideLoadingDialog();
                if (stringBaseDto.isSuccess()) {
                    ToastUitl.showImageToastSuccess("评价成功");
                    PinziApplication.clearSpecifyActivities(new Class[]{MyOrderFinishNoCommentDetailActivity.class});
                    finish();
                } else {
                    ToastUitl.showImageToastSuccess(stringBaseDto.getMsg());
                }
            }
        });
    }

    /**
     * 上传图片
     */
    private void uploadFile() {
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
                    //完善信息
                    addToComment(stringBaseDto.getData());
                } else {
                    getLoadingDialog().dismiss();
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        if (position == datas.size() - 1) {
            if (datas.size() == 10) {
                return;
            } else {
                showSelectPhotoTypeDialog();
            }
        }

    }

    @Override
    public void close(int position) {
        datas.remove(position);
        adapter = new MyCommentPhotoAdapter(this, datas, this, this);
        recyclerView.setAdapter(adapter);
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
                                RxPermissions.getInstance(PostEvaluationActivity.this).request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new PermissionsObserver() {
                                    @Override
                                    protected void onGetPermissionsSuccess() {
                                        SelectPhotoUtils.fromCapture(PostEvaluationActivity.this, FILE_PROVIDER, FROM_CAPTURE);
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

                                RxPermissions.getInstance(PostEvaluationActivity.this).request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new PermissionsObserver() {
                                    @Override
                                    protected void onGetPermissionsSuccess() {
                                        SelectPhotoUtils.fromAlbum(PostEvaluationActivity.this, FILE_PROVIDER, 10 - datas.size(), FROM_ALBUM);
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
