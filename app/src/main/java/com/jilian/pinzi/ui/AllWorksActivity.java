package com.jilian.pinzi.ui;

import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.AllWorkAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.UserViewInfo;
import com.jilian.pinzi.common.dto.ActivityDto;
import com.jilian.pinzi.common.dto.ActivityProductDto;
import com.jilian.pinzi.common.dto.BannerDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.ui.user.UserFragment;
import com.jilian.pinzi.ui.user.VideoPlayerDetailedActivity;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.loader.VideoClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jzvd.JzvdStd;

public class AllWorksActivity extends BaseActivity implements CustomItemClickListener, AllWorkAdapter.ClickVideoListener {
    private SmartRefreshLayout srHasData;
    private RecyclerView recyclerView;
    private SmartRefreshLayout srNoData;
    private LinearLayoutManager linearLayoutManager;
    private AllWorkAdapter allWorkAdapter;
    private List<ActivityProductDto> datas;
    private ActivityDto data;//活动
    private MainViewModel viewModel;
    private ArrayList<UserViewInfo> mThumbViewInfoList = new ArrayList<>();

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_allworks;
    }

    @Override
    public void initView() {
        setNormalTitle("全部作品", v -> finish());
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        srHasData.setEnableLoadMore(false);
        srNoData.setEnableLoadMore(false);
        linearLayoutManager = new LinearLayoutManager(this);
        datas = new ArrayList<>();

        allWorkAdapter = new AllWorkAdapter(this, datas, this, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(allWorkAdapter);
        srNoData.setEnableLoadMore(false);
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION, DisplayUtil.dip2px(this, 10));//下间距
        recyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
    }

    @Override
    public void initData() {
        data = (ActivityDto) getIntent().getSerializableExtra("data");

        getActivityProductList(getLoginDto().getId(), data.getId());
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            getLoadingDialog().dismiss();
            if (msg.what == 1000) {
                List<ActivityProductDto> list = (List<ActivityProductDto>) msg.obj;
                datas.clear();
                datas.addAll(list);
                allWorkAdapter.notifyDataSetChanged();

            }
        }
    };

    /**
     * 用户ID
     *
     * @param uId
     * @param aId 活动ID
     */
    private void getActivityProductList(String uId, String aId) {
        showLoadingDialog();
        viewModel.getActivityProductList(uId, aId);
        viewModel.getProductData().observe(this, new Observer<BaseDto<List<ActivityProductDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<ActivityProductDto>> listBaseDto) {
                hideLoadingDialog();
                if (listBaseDto.isSuccess()) {
                    if (EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                        datas.addAll(listBaseDto.getData());
                        datas.get(0).setVideo("http://lmp4.vjshi.com/2017-09-13/f55a900d89679ac1c9837d5b5aaf632a.mp4");
                        allWorkAdapter.notifyDataSetChanged();
                        //开启子线程
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                //对视频封面处理 耗时操作
                                for (int i = 0; i < listBaseDto.getData().size(); i++) {
                                    //视频地址不为空
                                    if (EmptyUtils.isNotEmpty(listBaseDto.getData().get(i).getVideo())) {
                                        listBaseDto.getData().get(i).setBitmap(getNetVideoBitmap(listBaseDto.getData().get(i).getVideo()));
                                    }
                                }
                                Message msg = Message.obtain();
                                msg.what = 1000;
                                msg.obj = listBaseDto.getData();
                                handler.sendMessage(msg);

                            }
                        }.start();

                    } else {

                    }
                } else {
                    ToastUitl.showImageToastFail(listBaseDto.getMsg());
                }
            }
        });
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
    public void initListener() {
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getActivityProductList(getLoginDto().getId(), data.getId());
            }
        });

        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getActivityProductList(getLoginDto().getId(), data.getId());
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void clickVideo(int position) {

        Intent intent = new Intent(this, VideoPlayerDetailedActivity.class);
        intent.putExtra("url", datas.get(position).getVideo());
        Bitmap bitmap = datas.get(position).getBitmap();
        intent.putExtra("bitmap", bitmap);

        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(AllWorksActivity.this).toBundle());

    }

}
