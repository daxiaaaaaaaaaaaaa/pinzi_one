package com.jilian.pinzi.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.CommonPagerAdapter;
import com.jilian.pinzi.adapter.MoreShopsAdapter;
import com.jilian.pinzi.adapter.ViewPagerIndicator;
import com.jilian.pinzi.adapter.common.BannerAdapter;
import com.jilian.pinzi.adapter.common.BannerViewAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.BannerDto;
import com.jilian.pinzi.common.dto.StartPageDto;
import com.jilian.pinzi.common.dto.StoreShowDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.listener.ViewPagerItemClickListener;
import com.jilian.pinzi.ui.MainActivity;
import com.jilian.pinzi.ui.main.viewmodel.MainViewModel;
import com.jilian.pinzi.utils.DisplayUtil;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * 更多店铺
 */
public class MoreShopsActivity extends BaseActivity implements CustomItemClickListener,ViewPagerItemClickListener {
    private ViewPager viewPager;
    private RecyclerView recyclerView;
    private List<View> views;
    private CommonPagerAdapter commonPagerAdapter;
    private LinearLayout llIndcator;
    private MoreShopsAdapter adapter;
    private List<StoreShowDto> datas;
    private GridLayoutManager gridLayoutManager;
    private SmartRefreshLayout srHasData;
    private SmartRefreshLayout srNoData;
    private MainViewModel viewModel;
    private ImageView ivRightTwo;


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
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_moreshops;
    }

    @Override
    public void initView() {
        setNormalTitle("更多店铺", v -> finish());
        ivRightTwo = (ImageView) findViewById(R.id.iv_right_two);
        ivRightTwo.setImageResource(R.drawable.image_search);
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        setrightImage(R.drawable.image_shop, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MoreShopsActivity.this, MainActivity.class);
                intent.putExtra("index", 3);
                startActivity(intent);
            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        llIndcator = (LinearLayout) findViewById(R.id.ll_indcator);
        datas = new ArrayList<>();
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, DisplayUtil.dip2px(this,15));//右间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,  DisplayUtil.dip2px(this,15));//下间距
        recyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        srNoData.setEnableRefresh(false);
        srNoData.setEnableLoadMore(false);
        adapter = new MoreShopsAdapter(this, datas, this);
        recyclerView.setAdapter(adapter);


    }


    @Override
    public void initData() {
        //获取轮播图
        getStartPage();
        //获取店铺展示的数据
        getStoreShowData();
    }
    private int pageNo = 1;//
    private int pageSize = 20;//
    private void getStoreShowData() {
        viewModel.StoreShow(pageNo, pageSize);
        viewModel.getStoreShowliveData().observe(this, new Observer<BaseDto<List<StoreShowDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<StoreShowDto>> dto) {
                getLoadingDialog().dismiss();
                srNoData.finishRefresh();
                srHasData.finishRefresh();
                srHasData.finishLoadMore();
                //有数据
                if (EmptyUtils.isNotEmpty(dto.getData())) {
                    srNoData.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    if (pageNo == 1) {
                        datas.clear();
                    }
                    datas.addAll(dto.getData());
                    adapter.notifyDataSetChanged();
                } else {
                    //说明是上拉加载
                    if (pageNo > 1) {
                        pageNo--;
                    } else {
                        //第一次就没数据
                        srNoData.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }
            }
        });

    }
    private BannerViewAdapter bannerAdapter;
    private List<BannerDto> bannerDtoList;
    private void getStartPage() {
        viewModel.StartPage(4);
        viewModel.getStartPageliveData().observe(this, new Observer<BaseDto<List<StartPageDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<StartPageDto>> listBaseDto) {
                views = new ArrayList<>();
                if (EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                    bannerDtoList = new ArrayList<>();
                    for (int i = 0; i < listBaseDto.getData().size(); i++) {
                        BannerDto dto = new BannerDto();
                        dto.setPath(listBaseDto.getData().get(i).getImgUrl());
                        bannerDtoList.add(dto);
                        bannerAdapter = new BannerViewAdapter(MoreShopsActivity.this);
                        bannerAdapter.update(bannerDtoList);
                        viewPager.setAdapter(bannerAdapter);
                    }

                }

            }
        });
    }

    @Override
    public void initListener() {
        ivRightTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MoreShopsActivity.this,MoreShopSearchActivity.class));
            }
        });
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getStoreShowData();
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getStoreShowData();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        ShopDetailActivity.startActivity(this, datas.get(position).getId(),2);
    }

    @Override
    public void onViewPageItemClick(View view, int position) {

    }
}
