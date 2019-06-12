package com.jilian.pinzi.ui.friends;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.PublishFriendsAdapter;
import com.jilian.pinzi.adapter.SelectImageAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.common.Image;
import com.jilian.pinzi.utils.ToastUitl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ningpan
 * @since 2018/11/22 18:13 <br>
 * description: 选择照片 Activity
 */
public class SelectImageActivity extends BaseActivity {

    public static void startActivityForResult(Context context, int selectedImageSize, int requestCodde) {
        Intent intent = new Intent(context, SelectImageActivity.class);
        intent.putExtra("selectedImageSize", selectedImageSize);
        ((Activity) context).startActivityForResult(intent, requestCodde);
    }

    private static final int LOADER_ALL = 0;
    private static final int LOADER_CATEGORY = 1;

    private TextView tvNum;
    private Button btnConfrim;
    private RecyclerView recyclerView;
    private SelectImageAdapter imageAdapter;
    private List<Image> imageList = new ArrayList<>();

    /** 已选择了图片个数：最多选择9个 */
    private int selectedImageSize;
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

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_select_image;
    }

    @Override
    public void initView() {
        setNormalTitle("选择图片", v -> finish(), "取消", v -> finish());
        recyclerView = findViewById(R.id.rv_select_image);
        tvNum = findViewById(R.id.tv_select_image_num);
        btnConfrim = findViewById(R.id.btn_select_image_confirm);

        initRecyclerView();
    }

    private void initRecyclerView() {
        imageAdapter = new SelectImageAdapter(this, R.layout.item_select_image, imageList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setAdapter(imageAdapter);
        imageAdapter.setOnItemClickListener(new SelectImageAdapter.OnItemClickListener() {
            @Override
            public void onImageClick(View view, int position) {
                // TODO 点击图片
            }

            @Override
            public void onCheckClick(View view, int position) {
                // TODO 点击选择按钮
                // 计算已经选择了多少张张片
                int count = getSelectedImage().size();
                if (count >= PublishFriendsAdapter.IMAGE_SIZE - selectedImageSize
                        && !imageList.get(position).isCheck()) {
                    ToastUitl.showLong("你最多只能选择9张片");
                    return;
                }
                imageList.get(position).setCheck(!imageList.get(position).isCheck());
                imageAdapter.notifyDataSetChanged();
                refreshNum(getSelectedImage().size());
            }
        });
    }

    /** 获取到已选择的张片集合 */
    public List<Image> getSelectedImage() {
        List<Image> images = new ArrayList<>();
        for (Image image : imageList) {
            if (image.isCheck()) images.add(image);
        }
        return images;
    }

    @Override
    public void initData() {
        selectedImageSize = getIntent().getIntExtra("selectedImageSize", 0);
        refreshNum(0);
        // 加载相册数据
        getSupportLoaderManager().restartLoader(LOADER_ALL, null, mLoaderCallback);
    }

    private void refreshNum(int num) {
        tvNum.setText(String.valueOf("已选择" + num + "/" + (PublishFriendsAdapter.IMAGE_SIZE - selectedImageSize)));
    }

    @Override
    public void initListener() {
        btnConfrim.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) getSelectedImage());
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {

        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media._ID };

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            CursorLoader cursorLoader = null;
            if(id == LOADER_ALL) {
                cursorLoader = new CursorLoader(SelectImageActivity.this,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                        IMAGE_PROJECTION[4]+">0 AND "+IMAGE_PROJECTION[3]+"=? OR "+IMAGE_PROJECTION[3]+"=? ",
                        new String[]{"image/jpeg", "image/png"}, IMAGE_PROJECTION[2] + " DESC");
            }else if(id == LOADER_CATEGORY){
                cursorLoader = new CursorLoader(SelectImageActivity.this,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                        IMAGE_PROJECTION[4]+">0 AND "+IMAGE_PROJECTION[0]+" like '%"+args.getString("path")+"%'",
                        null, IMAGE_PROJECTION[2] + " DESC");
            }
            return cursorLoader;
        }

        private boolean fileExist(String path){
            if(!TextUtils.isEmpty(path)){
                return new File(path).exists();
            }
            return false;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (data != null) {
                if (data.getCount() > 0) {
                    List<Image> images = new ArrayList<>();
                    data.moveToFirst();
                    do{
                        String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                        String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                        long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                        if(!fileExist(path)){continue;}
                        Image image = null;
                        if (!TextUtils.isEmpty(name)) {
                            image = new Image(path, name, dateTime);
                            images.add(image);
                        }

                    } while (data.moveToNext());
                    imageList.clear();
                    imageList.addAll(images);
                    imageAdapter.notifyDataSetChanged();
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };
}
