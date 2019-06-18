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

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * 更多店铺
 */
public class MoreShopsActivity extends BaseActivity implements CustomItemClickListener, ViewPagerItemClickListener, BDLocationListener {

    private RecyclerView recyclerView;
    private MoreShopsAdapter adapter;
    private List<StoreShowDto> datas;
    private GridLayoutManager gridLayoutManager;
    private SmartRefreshLayout srHasData;
    private SmartRefreshLayout srNoData;
    private MainViewModel viewModel;
    private LinearLayout llNormal;
    private LinearLayout llMap;
    private MapView mapView;
    //地图对象
    private BaiduMap baiduMap;


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

        llNormal = (LinearLayout) findViewById(R.id.ll_normal);
        llMap = (LinearLayout) findViewById(R.id.ll_map);
        mapView = (MapView) findViewById(R.id.mapView);
        baiduMap = mapView.getMap();
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        setrightImageTwo(R.drawable.image_shop, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PinziApplication.getInstance().getLoginDto() == null) {
                    Intent intent = new Intent(MoreShopsActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                Intent intent = new Intent(MoreShopsActivity.this, MainActivity.class);
                intent.putExtra("index", 3);
                intent.putExtra("back", 2);
                startActivity(intent);
            }
        });
        setrightImageOne(R.drawable.image_shop_map, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(llNormal.getVisibility()  == View.VISIBLE){
                    llNormal.setVisibility(View.GONE);
                    llMap.setVisibility(View.VISIBLE);
                    iv_right_one.setImageResource(R.drawable.image_shop_text);
                }
                else{
                    llNormal.setVisibility(View.VISIBLE);
                    llMap.setVisibility(View.GONE);
                    iv_right_one.setImageResource(R.drawable.image_shop_map);
                }
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
        srNoData.setEnableRefresh(false);
        srNoData.setEnableLoadMore(false);
        adapter = new MoreShopsAdapter(this, datas, this);
        recyclerView.setAdapter(adapter);


    }


    @Override
    public void initData() {
        //获取店铺展示的数据
        getStoreShowData();

        //定位
        startLocationCilent();
        //113.979419,22.539444 世界之窗
        mPointList.add(new LatLng(2.539444,113.979419));
        //113.830228,22.638482 宝安机场
        mPointList.add(new LatLng(22.638482,113.830228));
        //113.944349,22.534905 深圳大学
        mPointList.add(new LatLng(22.534905,113.944349));
        //114.003709,22.528495 深圳湾公园
        mPointList.add(new LatLng(22.528495,114.003709));
        showPointsInBaiduMap(mPointList);

    }

    /**
     * 开启定位
     */
    private void startLocationCilent() {

        PinziApplication.getInstance().mLocationClient.start();
        PinziApplication.getInstance().mLocationClient.registerLocationListener(this);
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

    @Override
    public void initListener() {

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
        ShopDetailActivity.startActivity(this, datas.get(position).getId(), 2);
    }

    @Override
    public void onViewPageItemClick(View view, int position) {

    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        initMapLocation(bdLocation.getLatitude(), bdLocation.getLongitude());
        PinziApplication.getInstance().mLocationClient.stop();
    }

    List<LatLng> mPointList = new ArrayList<>();

    /**
     * 在地图上展示各个点
     *
     * @param pointList
     */
    private void showPointsInBaiduMap(List<LatLng> pointList) {

        //创建OverlayOptions的集合
        List<OverlayOptions> options = new ArrayList<>();
        for (int i = 0; i < pointList.size(); i++) {
            //构建MarkerOption，用于在地图上添加Marker
            MarkerOptions option = new MarkerOptions();
            option.position(pointList.get(i));
            //构建Marker图标
            option.icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.image_location_target));
            //在地图上添加Marker，并显示
            options.add(option);
            //定义文字所显示的坐标点
        }
        //在地图上批量添加
        baiduMap.addOverlays(options);
        baiduMap.addOverlays(options);

    }


    /**
     * 定位到某个坐标点
     *
     * @param latitude
     * @param longitude
     */
    private void initMapLocation(double latitude, double longitude) {
        // 开启定位图层
        baiduMap.setMyLocationEnabled(true);
        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(200)
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(latitude)
                .longitude(longitude).build();
        // 设置定位数据
        baiduMap.setMyLocationData(locData);
        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.image_now_adress);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, mCurrentMarker);
        baiduMap.setMyLocationConfiguration(config);
        // 当不需要定位图层时关闭定位图层
        baiduMap.setMyLocationEnabled(false);

    }
}
