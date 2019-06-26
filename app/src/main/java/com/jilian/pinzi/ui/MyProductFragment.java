package com.jilian.pinzi.ui;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.AllWorkAdapter;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.base.BaseFragment;
import com.jilian.pinzi.common.dto.ActivityProductDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyProductFragment extends BaseFragment implements CustomItemClickListener, AllWorkAdapter.ClickListener {
    private SmartRefreshLayout srHasData;
    private RecyclerView recyclerview;
    private SmartRefreshLayout srNoData;
    private List<ActivityProductDto> datas;
    private LinearLayoutManager linearLayoutManager;
    private AllWorkAdapter adapter;

    @Override
    protected void loadData() {

    }

    @Override
    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_myproduct;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        srHasData = (SmartRefreshLayout) view.findViewById(R.id.sr_has_data);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        srNoData = (SmartRefreshLayout) view.findViewById(R.id.sr_no_data);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(linearLayoutManager);
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION, DisplayUtil.dip2px(getActivity(), 10));//下间距
        recyclerview.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        datas = new ArrayList<>();
        adapter = new AllWorkAdapter(getActivity(), datas, this, this);
        recyclerview.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        getMyProduct();
    }

    private int pageNo = 1;//
    private int pageSize = 20;//
    private MainViewModel viewModel;

    private void getMyProduct() {
        viewModel.getMyProduct(PinziApplication.getInstance().getLoginDto().getId(), pageNo, pageSize);
        viewModel.getMyProductData().observe(this, new Observer<BaseDto<List<ActivityProductDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<ActivityProductDto>> listBaseDto) {
                getLoadingDialog().dismiss();
                srNoData.finishRefresh();
                srHasData.finishRefresh();
                srHasData.finishLoadMore();
                //有数据
                if (EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                    srNoData.setVisibility(View.GONE);
                    srHasData.setVisibility(View.VISIBLE);
                    if (pageNo == 1) {
                        datas.clear();
                    }
                    datas.addAll(listBaseDto.getData());
                    adapter.notifyDataSetChanged();
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
                    //说明是上拉加载
                    if (pageNo > 1) {
                        pageNo--;
                    } else {
                        //第一次就没数据
                        srNoData.setVisibility(View.VISIBLE);
                        srHasData.setVisibility(View.GONE);
                    }
                }
            }
        });
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
                adapter.notifyDataSetChanged();

            }
        }
    };
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
    protected void initListener() {
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getMyProduct();
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getMyProduct();
            }
        });
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getMyProduct();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void clickVideo(int position) {
        Intent intent = new Intent(getActivity(),VideoPlayerActivity.class);
        intent.putExtra("url", datas.get(position).getVideo());
        Bitmap bitmap = datas.get(position).getBitmap();
        //intent.putExtra("bitmap", bitmap);

        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
    }

    @Override
    public void vote(int position) {
//        getLoadingDialog().show();
//        viewModel.voteActivityProduct(getLoginDto().getId(), datas.get(position).getId(), datas.get(position).getIsVote() == 0 ? 1 : 2);
//        viewModel.getVoteData().observe(this, new Observer<BaseDto>() {
//            @Override
//            public void onChanged(@Nullable BaseDto baseDto) {
//             getLoadingDialog().dismiss();
//                if (baseDto.isSuccess()) {
//                    ToastUitl.showImageToastSuccess("操作成功");
//                    pageNo = 1;
//                    getMyProduct();
//                } else {
//                    ToastUitl.showImageToastFail(baseDto.getMsg());
//                }
//            }
//        });

    }
}
