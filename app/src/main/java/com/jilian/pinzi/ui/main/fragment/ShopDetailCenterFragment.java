package com.jilian.pinzi.ui.main.fragment;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.ShopPhotpAdapter;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.ActivityProductDto;
import com.jilian.pinzi.common.dto.ShopDetailDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import cn.jzvd.JzvdStd;

public class ShopDetailCenterFragment extends BaseFragment implements CustomItemClickListener {
    //商品评分
    private TextView tvComment;
    //营业时间
    private TextView tvTime;
    private TextView tvShopDetailAddress;
    private TextView tvShopDetailPhone;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ShopPhotpAdapter adapter;
    private List<String> datas;
    private JzvdStd ivVideo;


    public void initDataView(ShopDetailDto mShopDetail) {
        //地址
        tvShopDetailAddress.setText(mShopDetail.getCity() + mShopDetail.getArea() + mShopDetail.getAddress());
        //电话
        tvShopDetailPhone.setText(mShopDetail.getPhone());
        //
        datas.clear();
        if (TextUtils.isEmpty(mShopDetail.getPathUrl())) {
            datas.clear();
            if (mShopDetail.getPathUrl().contains(",")) {
                String[] array = mShopDetail.getPathUrl().split(",");
                datas.addAll(Arrays.asList(array));

            } else {
                datas.add(mShopDetail.getPathUrl());
            }
            adapter.notifyDataSetChanged();
        }
        initVideo(mShopDetail.getVideoUrl());

    }

    /**
     * 初始化视频
     *
     * @param videoUrl
     */
    private void initVideo(String videoUrl) {
        if (TextUtils.isEmpty(videoUrl)) {
            return;
        }
        //开启子线程
        new Thread() {
            @Override
            public void run() {
                super.run();
                //对视频封面处理 耗时操作

                Bitmap bitmap = getNetVideoBitmap(videoUrl);
                Message msg = Message.obtain();
                msg.what = 1000;
                msg.obj = bitmap;
                handler.sendMessage(msg);


            }
        }.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            getLoadingDialog().dismiss();
            if (msg.what == 1000) {
                Bitmap bitmap = (Bitmap) msg.obj;
                ivVideo.setVisibility(View.VISIBLE);
                ivVideo.thumbImageView.setImageBitmap(bitmap);

            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        JzvdStd.goOnPlayOnPause();
    }

    public Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }

        return bitmap;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void createViewModel() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shopdetailcenter;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ivVideo = (JzvdStd) view.findViewById(R.id.iv_video);
        tvComment = (TextView) view.findViewById(R.id.tv_comment);
        tvTime = (TextView) view.findViewById(R.id.tv_time);
        tvShopDetailAddress = (TextView) view.findViewById(R.id.tv_shop_detail_address);
        tvShopDetailPhone = (TextView) view.findViewById(R.id.tv_shop_detail_phone);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getmActivity());
        //调整RecyclerView的排列方向
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION, DisplayUtil.dip2px(getmActivity(), 15));//左间距
        recyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));

        datas = new ArrayList<>();
        adapter = new ShopPhotpAdapter(getmActivity(), datas, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
