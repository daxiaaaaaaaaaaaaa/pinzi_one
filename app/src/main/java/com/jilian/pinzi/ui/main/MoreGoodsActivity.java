package com.jilian.pinzi.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.MoreGoodsAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.MainRecommendDto;
import com.jilian.pinzi.common.dto.MsgDto;
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
 * 更多商品
 */
public class MoreGoodsActivity extends BaseActivity implements CustomItemClickListener, ViewPagerItemClickListener {
    private RecyclerView recyclerView;
    private MoreGoodsAdapter adapter;
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
        return R.layout.activity_moregoods;
    }

    @Override
    public void initView() {
        setNormalTitle("更多商品", v -> finish());
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        setrightImage(R.drawable.image_shop, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MoreGoodsActivity.this, MainActivity.class);
                intent.putExtra("index", 3);
                startActivity(intent);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        datas = new ArrayList<>();
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, DisplayUtil.dip2px(this, 15));//右间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION, DisplayUtil.dip2px(this, 15));//下间距
        recyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        adapter = new MoreGoodsAdapter(this, datas, this,getClasses());
        recyclerView.setAdapter(adapter);
        srNoData.setEnableLoadMore(false);


    }


    @Override
    public void initData() {
        //获取返佣金专区数据
        getReturnCommissionData();
    }

    private int pageNo = 1;//
    private int pageSize = 20;//

    /**
     * 获取返佣金专区数据
     */
    private void getReturnCommissionData() {
        viewModel.ReturnCommission(pageNo, pageSize);
        viewModel.getReturnCommissionliveData().observe(this, new Observer<BaseDto<List<MainRecommendDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<MainRecommendDto>> listBaseDto) {
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

    @Override
    public void initListener() {
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getReturnCommissionData();
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getReturnCommissionData();
            }
        });
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getReturnCommissionData();
            }
        });
    }
    public int getClasses() {
        return getIntent().getIntExtra("classes",1);
    }
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, GoodsDetailActivity.class);
        intent.putExtra("goodsId", datas.get(position).getId());
        intent.putExtra("return",getIntent().getIntExtra("shopType", 1));
        intent.putExtra("classes", getClasses());
        startActivity(intent);
    }

    @Override
    public void onViewPageItemClick(View view, int position) {

    }
}
