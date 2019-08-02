package com.jilian.pinzi.ui.main.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.ShopPhotpAdapter;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.ActivityProductDto;
import com.jilian.pinzi.common.dto.ShopDetailDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.utils.BitmapUtils;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.PinziDialogUtils;
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
    private LinearLayout llCall;


    public void initDataView(ShopDetailDto mShopDetail) {
        //地址
        tvShopDetailAddress.setText(mShopDetail.getCity() + mShopDetail.getArea() + mShopDetail.getAddress());
        //电话
        tvShopDetailPhone.setText(mShopDetail.getPhone());
        //评分
        tvComment.setText(mShopDetail.getStoreGrade()+"分");
        //营业时间
        tvTime.setText(mShopDetail.getOpenHour());
        datas.clear();
        if (!TextUtils.isEmpty(mShopDetail.getPathUrl())) {
            datas.clear();
            if (mShopDetail.getPathUrl().contains(",")) {
                String[] array = mShopDetail.getPathUrl().split(",");
                datas.addAll(Arrays.asList(array));

            } else {
                datas.add(mShopDetail.getPathUrl());
            }
            adapter.notifyDataSetChanged();
        }

        initVideo(mShopDetail);

    }

    /**
     * 初始化视频
     *
     * @param detailDto
     */
    private void initVideo(ShopDetailDto detailDto) {
        if (TextUtils.isEmpty(detailDto.getVideoUrl())) {
            return;
        }
        //开启子线程
        new Thread() {
            @Override
            public void run() {
                super.run();
                //对视频封面处理 耗时操作

                Bitmap bitmap = BitmapUtils.getNetVideoBitmap(detailDto.getVideoUrl());
                detailDto.setBitmap(bitmap);
                Message msg = Message.obtain();
                msg.what = 1000;
                msg.obj = detailDto;
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
                ShopDetailDto detailDto = (ShopDetailDto) msg.obj;
                ivVideo.setVisibility(View.VISIBLE);
                ivVideo.thumbImageView.setImageBitmap(detailDto.getBitmap());
                ivVideo.setUp(detailDto.getVideoUrl(), "",JzvdStd.SCREEN_WINDOW_NORMAL);
                JzvdStd.setJzUserAction(null);
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        JzvdStd.goOnPlayOnPause();
    }

    private void showTwoTipsDialog(String phone) {
        if(TextUtils.isEmpty(phone)){
            return;
        }
        Dialog dialog = PinziDialogUtils.getDialogNotTouchOutside(getmActivity(), R.layout.dialog_confirm);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) dialog.findViewById(R.id.tv_content);
        tvTitle.setText("拨打电话");
        tvContent.setText(phone);
        TextView tvNo = (TextView) dialog.findViewById(R.id.tv_no);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tv_ok);
        tvOk.setText("拨打");
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + phone);
                intent.setData(data);
                startActivity(intent);


            }
        });
        dialog.show();
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

        llCall = (LinearLayout) view.findViewById(R.id.ll_call);
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
        llCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTwoTipsDialog(tvShopDetailPhone.getText().toString());
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
