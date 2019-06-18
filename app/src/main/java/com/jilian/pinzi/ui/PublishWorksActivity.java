package com.jilian.pinzi.ui;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.PublishPhotoAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.msg.FriendMsg;
import com.jilian.pinzi.common.msg.RxBus;
import com.jilian.pinzi.dialog.BaseNiceDialog;
import com.jilian.pinzi.dialog.NiceDialog;
import com.jilian.pinzi.dialog.ViewConvertListener;
import com.jilian.pinzi.dialog.ViewHolder;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.ViewPhotosActivity;
import com.jilian.pinzi.ui.viewmodel.FriendViewModel;
import com.jilian.pinzi.ui.viewmodel.UserViewModel;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.InputBoardUtils;
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

/**
 * 发布作品
 */
public class PublishWorksActivity extends BaseActivity implements CustomItemClickListener, PublishPhotoAdapter.ClosePhotonListener {

     private CustomerGridLayManager gridLayoutManager;
    //相机
    private final int FROM_CAPTURE = 10001;
    //相册
    private final int FROM_ALBUM = 10002;
    private RecyclerView recyclerView;
    private List<String> datas;
    private UserViewModel userViewModel;
    private FriendViewModel viewModel;
    private EditText etPublishFriendsContent;
     private PublishPhotoAdapter adapter;
    private ImageView ivLeftText;


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
        viewModel = ViewModelProviders.of(this).get(FriendViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_publish_works;
    }

    @Override
    public void initView() {
        setNormalTitle("我的作品详情", v -> finish());

        ivLeftText = (ImageView) findViewById(R.id.iv_left_text);
        ivLeftText.setVisibility(View.GONE);
        etPublishFriendsContent = (EditText) findViewById(R.id.et_publish_friends_content);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        setleftTitle("取消", "#FFFFFF", true, v -> {
            InputBoardUtils.hideInputKeyBoard(etPublishFriendsContent);
            etPublishFriendsContent.postDelayed(this::finish, 200);
        });
        setrightTitle("提交", "#FFFFFF", v -> {
            // TODO
            publish();
        });

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
        datas.add("");
        adapter = new PublishPhotoAdapter(this, datas, this, this);
        recyclerView.setAdapter(adapter);


    }

    private void publish() {
        //有图片
        if (EmptyUtils.isNotEmpty(datas)&&datas.size()>1) {
            showLoadingDialog();
            uploadFile();
        }
        //无图片
        else {
            //无文字
            if (TextUtils.isEmpty(etPublishFriendsContent.getText().toString())) {
                return;
            }
            showLoadingDialog();
            FriendCircleIssue(PinziApplication.getInstance().getLoginDto().getId(), etPublishFriendsContent.getText().toString(), null, null);

        }
    }

    /**
     * 发布朋友
     *
     * @param uId       用户ID
     * @param content   内容
     * @param imgUrl    图片
     * @param photoSize 图片尺寸
     */
    public void FriendCircleIssue(String uId, String content, String imgUrl, String photoSize) {
        viewModel.FriendCircleIssue(uId, content, imgUrl, photoSize);
        viewModel.getPublish().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                hideLoadingDialog();
                if (stringBaseDto.isSuccess()) {
                    //通过事件总线 把 前面两个Activity的数据也刷新
                    FriendMsg eventMsg = new FriendMsg();
                    eventMsg.setCode(200);
                    RxBus.getInstance().post(eventMsg);
                    ToastUitl.showImageToastSuccess(stringBaseDto.getMsg());
                    finish();
                } else {
                    ToastUitl.showImageToastFail(stringBaseDto.getMsg());
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }


    @Override
    public void close(int position) {
        datas.remove(position);
        adapter = new PublishPhotoAdapter(this, datas, this, this);
        recyclerView.setAdapter(adapter);
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
        else{
            String url = "";
            for (int i = 0; i <datas.size()-1 ; i++) {
                if(i==0){
                    url += datas.get(i);
                }
                else{
                    url+=","+datas.get(i);
                }
            }
            Intent intent = new Intent(this, ViewPhotosActivity.class);
            intent.putExtra("url",url);
            intent.putExtra("position",position);
            startActivity(intent);
        }
    }

    /**.
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
                getLoadingDialog().dismiss();
                if (stringBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                    //完善信息
                    FriendCircleIssue(PinziApplication.getInstance().getLoginDto().getId(), etPublishFriendsContent.getText().toString(), stringBaseDto.getData(), null);
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
                                RxPermissions.getInstance(PublishWorksActivity.this).request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new PermissionsObserver() {
                                    @Override
                                    protected void onGetPermissionsSuccess() {
                                        SelectPhotoUtils.fromCapture(PublishWorksActivity.this, FILE_PROVIDER, FROM_CAPTURE);
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

                                RxPermissions.getInstance(PublishWorksActivity.this).request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new PermissionsObserver() {
                                    @Override
                                    protected void onGetPermissionsSuccess() {
                                        SelectPhotoUtils.fromAlbum(PublishWorksActivity.this, FILE_PROVIDER, 10 - datas.size(), FROM_ALBUM);
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
