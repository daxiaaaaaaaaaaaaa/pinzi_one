package com.jilian.pinzi.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.jilian.pinzi.adapter.MainPagerAdapter;
import com.jilian.pinzi.adapter.ViewPagerIndicator;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
import com.jilian.pinzi.common.dto.AddressDto;
import com.jilian.pinzi.common.dto.ScoreBuyGoodsDto;
import com.jilian.pinzi.common.dto.StartPageDto;
import com.jilian.pinzi.listener.CustomItemClickListener;
import com.jilian.pinzi.listener.ViewPagerItemClickListener;
import com.jilian.pinzi.ui.LoginActivity;
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

/**
 * 积分商城
 */
public class IntegralMallActivity extends BaseActivity implements CustomItemClickListener, ViewPagerItemClickListener {
    private ViewPager viewPager;
    private RecyclerView recyclerView;
    private MainPagerAdapter commonPagerAdapter;
    private LinearLayout llIndcator;
    private IntegralMallAdapter adapter;
    private List<ScoreBuyGoodsDto> datas;
    private GridLayoutManager gridLayoutManager;
    private MainViewModel viewModel;
    private SmartRefreshLayout srHasData;
    private SmartRefreshLayout srNoData;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PinziApplication.addActivity(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getStartPage();
        getScoreBuyGoods();
    }

    private int pageNo = 1;//
    private int pageSize = 20;//

    /**
     * 获取商品信息
     */
    private void getScoreBuyGoods() {
        viewModel.getScoreBuyGoods(pageNo, pageSize, getLoginDto() == null ? 0 : getLoginDto().getType(), 1);
        viewModel.getScoreBuyGoodsliveData().observe(this, new Observer<BaseDto<List<ScoreBuyGoodsDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<ScoreBuyGoodsDto>> addressDtoBaseDto) {
                getLoadingDialog().dismiss();
                srNoData.finishRefresh();
                srHasData.finishRefresh();
                srHasData.finishLoadMore();
                //有数据
                if (EmptyUtils.isNotEmpty(addressDtoBaseDto.getData())) {
                    srNoData.setVisibility(View.GONE);
                    srHasData.setVisibility(View.VISIBLE);
                    if (pageNo == 1) {
                        datas.clear();
                    }
                    datas.addAll(addressDtoBaseDto.getData());
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

    private List<StartPageDto> list;

    /**
     * 获取轮播图
     */
    private void getStartPage() {
        viewModel.StartPage(7);
        viewModel.getStartPageliveData().observe(this, new Observer<BaseDto<List<StartPageDto>>>() {
            @Override
            public void onChanged(@Nullable BaseDto<List<StartPageDto>> listBaseDto) {
                list.clear();
                if (EmptyUtils.isNotEmpty(listBaseDto.getData())) {
                    list.addAll(listBaseDto.getData());
                    viewPager.addOnPageChangeListener(new ViewPagerIndicator(IntegralMallActivity.this, viewPager, llIndcator, list.size()));
                    commonPagerAdapter = new MainPagerAdapter(list, IntegralMallActivity.this, IntegralMallActivity.this);
                    viewPager.setAdapter(commonPagerAdapter);
                }

            }
        });
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
        return R.layout.activity_integralmall;
    }

    @Override
    public void initView() {
        setNormalTitle("积分商城", v -> finish());
        setrightImage(R.drawable.image_shop, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(IntegralMallActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                Intent intent = new Intent(IntegralMallActivity.this, MainActivity.class);
                intent.putExtra("index", 3);
                intent.putExtra("back", 2);
                startActivity(intent);
            }
        });
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
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
        adapter = new IntegralMallAdapter(this, datas, this);
        recyclerView.setAdapter(adapter);
        srNoData.setEnableLoadMore(false);
        list = new ArrayList<>();

    }


    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        srHasData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getScoreBuyGoods();
            }
        });
        srNoData.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getScoreBuyGoods();
            }
        });
        srHasData.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getScoreBuyGoods();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, GoodsDetailActivity.class);
        intent.putExtra("classes", getClasses());
        intent.putExtra("goodsId", datas.get(position).getId());
        intent.putExtra("shopType", 2);//积分商城
        startActivity(intent);

    }

    @Override
    public void onViewPageItemClick(View view, int position) {
        if (list.get(position).getJumpType() == 1) {
            String linkUrl = list.get(position).getLinkUrl();
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("linkUrl", linkUrl);
            startActivity(intent);
        }
        if (list.get(position).getJumpType() == 2) {
            Intent intent = new Intent(this, GoodsDetailActivity.class);
            intent.putExtra("classes", getClasses());
            intent.putExtra("goodsId", list.get(position).getGoodId());
            startActivity(intent);
        }

    }

    /**
     * 获取类别
     * @return
     */
    public int getClasses() {
        //未登录 或者是  用户为普通用户 1
        if (PinziApplication.getInstance().getLoginDto() == null
                || PinziApplication.getInstance().getLoginDto().getType() == 1) {
            return 1;
        } else {
            //非普通用户
            return 2;
        }

    }

}
