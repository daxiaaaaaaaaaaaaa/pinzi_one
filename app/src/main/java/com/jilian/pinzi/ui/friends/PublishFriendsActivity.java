package com.jilian.pinzi.ui.friends;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
import com.jilian.pinzi.ui.VideoPlayerActivity;
import com.jilian.pinzi.ui.main.ViewPhotosActivity;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.ui.viewmodel.FriendViewModel;
import com.jilian.pinzi.ui.viewmodel.UserViewModel;
import com.jilian.pinzi.utils.BitmapUtils;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.InputBoardUtils;
import com.jilian.pinzi.utils.PermissionsObserver;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.utils.selectphoto.SelectPhotoUtils;
import com.jilian.pinzi.views.CustomerGridLayManager;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.jilian.pinzi.Constant.FINALVALUE.FILE_PROVIDER;

/**
 * 发布作品
 */
public class PublishFriendsActivity extends BaseActivity implements CustomItemClickListener, PublishPhotoAdapter.ClosePhotonListener {

    private CustomerGridLayManager gridLayoutManager;
    //相机
    private final int FROM_CAPTURE = 10001;
    //相册
    private final int FROM_ALBUM = 10002;

    //视频
    private final int FROM_VIDEO = 10003;

    private RecyclerView recyclerView;
    private List<String> datas;
    private UserViewModel userViewModel;
    private FriendViewModel viewModel;
    private EditText etPublishFriendsContent;
    private PublishPhotoAdapter adapter;
    private ImageView ivLeftText;
    private ImageView ivVideo;
    private RelativeLayout rlVideo;
    private MainViewModel mainViewModel;


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
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_publish_works;
    }

    @Override
    public void initView() {
        setNormalTitle("发布", v -> finish());
        ivVideo = (ImageView) findViewById(R.id.iv_video);
        ivLeftText = (ImageView) findViewById(R.id.iv_left_text);
        ivLeftText.setVisibility(View.GONE);
        etPublishFriendsContent = (EditText) findViewById(R.id.et_publish_friends_content);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        rlVideo = (RelativeLayout) findViewById(R.id.rl_video);
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
        if (EmptyUtils.isNotEmpty(datas) && datas.size() > 1) {
            showLoadingDialog();
            uploadFile(1);
        }
        //视频
        else if (!TextUtils.isEmpty(videoPath)) {
            showLoadingDialog();
            uploadVideo(3,new File(videoPath));
        }
        //无图片
        else {
            //无文字
            if (TextUtils.isEmpty(etPublishFriendsContent.getText().toString())) {
                return;
            }
            showLoadingDialog();
            FriendCircleIssue(PinziApplication.getInstance().getLoginDto().getId(), etPublishFriendsContent.getText().toString(), null, null,null);


        }
    }

    /**
     * 上传视频
     * @param type
     */
    private void uploadVideo(int type,File file) {
        showLoadingDialog();
        //先获取token
        mainViewModel.uptoken();
        mainViewModel.getSevenTokenData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> baseDto) {
                hideLoadingDialog();
                if(baseDto.isSuccess()&&EmptyUtils.isNotEmpty(baseDto.getData())){
                    uploadToSeven(baseDto.getData(),file);
                }
                else{
                    ToastUitl.showImageToastFail(baseDto.getMsg());
                }
            }
        });
    }

    /**
     * 上传文件到七牛
     *
     * @param token
     */
    private void uploadToSeven(String token,File file) {
        showLoadingDialog();
        mainViewModel.uploadVideoToSeven(file,file.getName(),token);
        mainViewModel.getUploadVideoData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String key) {
                hideLoadingDialog();
                if(!TextUtils.isEmpty(key)){
                    FriendCircleIssue(PinziApplication.getInstance().getLoginDto().getId(), etPublishFriendsContent.getText().toString(),null, null, Constant.Server.SEVEN_URL+"/"+key);
                }
                else{
                    ToastUitl.showImageToastFail("上传视频到七牛异常");
                }
            }
        });
    }


    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        rlVideo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublishFriendsActivity.this, VideoPlayerActivity.class);
                intent.putExtra("url", videoPath);
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100,baos);
                byte [] bitmapByte =baos.toByteArray();
                intent.putExtra("bitmap",bitmapByte);

                // 添加跳转动画
                startActivity(intent,
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                                PublishFriendsActivity.this,
                                ivVideo,
                                PublishFriendsActivity.this.getString(R.string.share_str))
                                .toBundle());


            }
        });
        rlVideo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDeleteDialog();
                return false;
            }
        });
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
        } else {
            String url = "";
            for (int i = 0; i < datas.size() - 1; i++) {
                if (i == 0) {
                    url += datas.get(i);
                } else {
                    url += "," + datas.get(i);
                }
            }
            Intent intent = new Intent(this, ViewPhotosActivity.class);
            intent.putExtra("url", url);
            intent.putExtra("position", position);
            startActivity(intent);
        }
    }

    /**
     * 上传文件 类型 1图片 2GIF 3视屏
     *
     * @param type
     */
    private void uploadFile(Integer type) {
        if (type == null) {
            return;
        }
        List<File> fileList = new ArrayList<>();
        if (type == 1) {
            if (datas.size() == 1) {
                ToastUitl.showImageToastFail("请拍照或者从相册中选择图片上传");
                return;
            }
            for (int i = 0; i < datas.size() - 1; i++) {
                fileList.add(new File(datas.get(i)));
            }
        } else {
            if (TextUtils.isEmpty(videoPath)) {
                ToastUitl.showImageToastFail("请选择视频上传");
                return;
            }
            fileList.add(new File(videoPath));
        }
        showLoadingDialog();


        userViewModel.photoImg(type, fileList);
        userViewModel.getPhotoImageliveData().observe(this, new Observer<BaseDto<String>>() {
            @Override
            public void onChanged(@Nullable BaseDto<String> stringBaseDto) {
                getLoadingDialog().dismiss();
                if (stringBaseDto.getCode() == Constant.Server.SUCCESS_CODE) {
                    //发到朋友圈
                    FriendCircleIssue(PinziApplication.getInstance().getLoginDto().getId(), etPublishFriendsContent.getText().toString(), stringBaseDto.getData(), null,null);
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
                .setLayoutId(R.layout.dialog_photo_video_select)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        dialog.setOutCancel(false);
                        Button btnTakingPictures = (Button) holder.getView(R.id.btn_taking_pictures);
                        Button btnPhotoAlbum = (Button) holder.getView(R.id.btn_photo_album);
                        Button btnCancel = (Button) holder.getView(R.id.btn_cancel);
                        Button btnVideoAlbum = (Button) holder.getView(R.id.btn_video_album);
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
                                recyclerView.setVisibility(View.VISIBLE);
                                rlVideo.setVisibility(View.GONE);
                                RxPermissions.getInstance(PublishFriendsActivity.this).request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new PermissionsObserver() {
                                    @Override
                                    protected void onGetPermissionsSuccess() {
                                        SelectPhotoUtils.fromCapture(PublishFriendsActivity.this, FILE_PROVIDER, FROM_CAPTURE);
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
                                recyclerView.setVisibility(View.VISIBLE);
                                rlVideo.setVisibility(View.GONE);
                                RxPermissions.getInstance(PublishFriendsActivity.this).request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new PermissionsObserver() {
                                    @Override
                                    protected void onGetPermissionsSuccess() {
                                        SelectPhotoUtils.fromAlbum(PublishFriendsActivity.this, FILE_PROVIDER, 10 - datas.size(), FROM_ALBUM);
                                    }

                                    @Override
                                    protected void onGetPermissionsSuccessFailuer() {
                                        ToastUitl.showImageToastFail("相机权限被拒绝，无法使用拍照功能");
                                    }
                                });

                            }

                        });
                        //视频
                        btnVideoAlbum.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent, FROM_VIDEO);
                            }
                        });

                    }
                })
                .setShowBottom(true)
                .show(getSupportFragmentManager());
    }

    private String path;
    private String videoPath;
    private Bitmap bitmap;

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

                //相机
                case FROM_VIDEO:

                    Uri uri = data.getData();
                    ContentResolver cr = this.getContentResolver();
                    /** 数据库查询操作。
                     * 第一个参数 uri：为要查询的数据库+表的名称。
                     * 第二个参数 projection ： 要查询的列。
                     * 第三个参数 selection ： 查询的条件，相当于SQL where。
                     * 第三个参数 selectionArgs ： 查询条件的参数，相当于 ？。
                     * 第四个参数 sortOrder ： 结果排序。
                     */
                    Cursor cursor = cr.query(uri, null, null, null, null);
                    if (cursor != null) {
                        if (cursor.moveToFirst()) {
                            // 视频ID:MediaStore.Audio.Media._ID
                            int videoId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                            // 视频名称：MediaStore.Audio.Media.TITLE
                            String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                            // 视频路径：MediaStore.Audio.Media.DATA
                            videoPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                            // 视频时长：MediaStore.Audio.Media.DURATION
                            try {
                                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                            } catch (Exception e) {
                                ToastUitl.showImageToastFail("请选择视频");
                                return;

                            }
                            // 视频大小：MediaStore.Audio.Media.SIZE
                            long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));

                            // 视频缩略图路径：MediaStore.Images.Media.DATA
                            String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                            // 缩略图ID:MediaStore.Audio.Media._ID
                            int imageId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                            // 方法一 Thumbnails 利用createVideoThumbnail 通过路径得到缩略图，保持为视频的默认比例
                            // 第一个参数为 ContentResolver，第二个参数为视频缩略图ID， 第三个参数kind有两种为：MICRO_KIND和MINI_KIND 字面意思理解为微型和迷你两种缩略模式，前者分辨率更低一些。
                            bitmap = MediaStore.Video.Thumbnails.getThumbnail(cr, imageId, MediaStore.Video.Thumbnails.MICRO_KIND, null);

                            // 方法二 ThumbnailUtils 利用createVideoThumbnail 通过路径得到缩略图，保持为视频的默认比例
                            // 第一个参数为 视频/缩略图的位置，第二个依旧是分辨率相关的kind
                            Bitmap bitmap2 = ThumbnailUtils.createVideoThumbnail(imagePath, MediaStore.Video.Thumbnails.MICRO_KIND);
                            // 如果追求更好的话可以利用 ThumbnailUtils.extractThumbnail 把缩略图转化为的制定大小
//                        ThumbnailUtils.extractThumbnail(bitmap, width,height ,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
                            recyclerView.setVisibility(View.GONE);
                            rlVideo.setVisibility(View.VISIBLE);
                            ivVideo.setImageBitmap(bitmap);

                        }
                        cursor.close();
                    }
                    break;

            }

        }
    }

    /**
     * 删除视频
     */
    public void showDeleteDialog() {
        Dialog dialog = new Dialog(this, R.style.dialogFullscreen);
        dialog.setContentView(R.layout.dialog_bottom_layout);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.5f;
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(layoutParams);

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        dialog.findViewById(R.id.btn_dialog_bottom_del).setOnClickListener(v1 -> {
            // TODO 删除
            dialog.dismiss();
            recyclerView.setVisibility(View.VISIBLE);
            rlVideo.setVisibility(View.GONE);
            ivVideo.setImageBitmap(null);
            videoPath = null;
        });
        dialog.findViewById(R.id.btn_dialog_bottom_cancel).setOnClickListener(v1 -> {
            dialog.dismiss();

        });
    }

    /**
     * 发布朋友
     *
     * @param uId       用户ID
     * @param content   内容
     * @param imgUrl    图片
     * @param photoSize 图片尺寸
     */
    public void FriendCircleIssue(String uId, String content, String imgUrl, String photoSize,String video) {
        viewModel.FriendCircleIssue(uId, content, imgUrl, photoSize,video);
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

}
