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
import com.jilian.pinzi.adapter.IntegralMallAdapter;
import com.jilian.pinzi.adapter.SentimentRecommendedAdapter;
import com.jilian.pinzi.adapter.ViewPagerIndicator;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.MainRecommendDto;
import com.jilian.pinzi.common.dto.ScoreBuyGoodsDto;
import com.jilian.pinzi.common.dto.StartPageDto;
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
import java.util.HashMap;
import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * 人气推荐
 */
public class SentimentRecommendedActivity extends BaseActivity implements CustomItemClickListener, ViewPagerItemClickListener {
    private ViewPager viewPager;
    private RecyclerView recyclerView;
    private List<View> views;
    private CommonPagerAdapter commonPagerAdapter;
    private LinearLayout llIndcator;
    private SentimentRecommendedAdapter adapter;
    private List<MainRecommendDto> datas;
    private GridLayoutManager gridLayoutManager;
    private SmartRefreshLayout srHasData;
    private SmartRefreshLayout srNoData;
    private MainViewModel viewModel;


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
        return R.layout.activity_sentimentrecommended;
    }

    @Override
    public void initView() {
        setNormalTitle("更多推荐", v -> finish());
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        setrightImage(R.drawable.image_shop, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SentimentRecommendedActivity.this, MainActivity.class);
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
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, DisplayUtil.dip2px(this, 15));//右间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION, DisplayUtil.dip2px(this, 15));//下间距
        recyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        adapter = new SentimentRecommendedAdapter(this, datas, this,getClasses());
        recyclerView.setAdapter(adapter);
        srNoData.setEnableRefresh(false);
        srNoData.setEnableLoadMore(false);


    }
    public int getClasses() {
        return getIntent().getIntExtra("classes",1);
    }


    @Override
    public void initData() {
        //获取人气推荐数据
        getRecommenPersondData();
        //获取轮播图
        getStartPage();
    }

    /**
     * 获取轮播图
     */
    private void getStartPage() {
        viewModel.StartPage(6);
        viewModel.getStartPageliveData().observe(this, new Observer<BaseDto<List<StartPageDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<StartPageDto>> listBaseDto) {
                views = new ArrayList<>();
                if (EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                    for (int i = 0; i < listBaseDto.getData().size(); i++) {
                        View view = LayoutInflater.from(SentimentRecommendedActivity.this).inflate(R.layout.item_no_radius_photo, null);
                        final ImageView iv_advertise = (ImageView) view.findViewById(R.id.iv_advertise);
                        Glide.with(SentimentRecommendedActivity.this).load(listBaseDto.getData().get(i).getImgUrl()).into(iv_advertise);
                        views.add(view);
                    }
                    viewPager.addOnPageChangeListener(new ViewPagerIndicator(SentimentRecommendedActivity.this, viewPager, llIndcator, views.size()));
                    commonPagerAdapter = new CommonPagerAdapter(views, SentimentRecommendedActivity.this);
                    viewPager.setAdapter(commonPagerAdapter);
                }

            }
        });
    }

    private int pageNo = 1;//
    private int pageSize = 20;//

    /**
     * 获取人气推荐数据
     */
    private void getRecommenPersondData() {
        viewModel.RecommendPerson("1", pageNo, pageSize);
        viewModel.getRecommendPersonliveData().observe(this, new Observer<BaseDto<List<MainRecommendDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<MainRecommendDto>> dto) {
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

    @Override
    public void initListener() {
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getRecommenPersondData();
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getRecommenPersondData();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, GoodsDetailActivity.class);
        intent.putExtra("goodsId", datas.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onViewPageItemClick(View view, int position) {

    }
}
