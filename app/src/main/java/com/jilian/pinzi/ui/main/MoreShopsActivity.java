package com.jilian.pinzi.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.jakewharton.rxbinding.widget.RxTextView;
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
import com.jilian.pinzi.utils.ToastUitl;
import com.jilian.pinzi.views.RecyclerViewSpacesItemDecoration;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private RelativeLayout rlArea;
    private RelativeLayout rlDistance;
    private RelativeLayout rlScore;
    private EditText etSearch;
    private ImageView ivDistance;
    private TextView tvArea;


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
        tvArea = (TextView) findViewById(R.id.tv_area);
        etSearch = (EditText) findViewById(R.id.et_search);
        llNormal = (LinearLayout) findViewById(R.id.ll_normal);
        llMap = (LinearLayout) findViewById(R.id.ll_map);
        mapView = (MapView) findViewById(R.id.mapView);
        baiduMap = mapView.getMap();
        srHasData = (SmartRefreshLayout) findViewById(R.id.sr_has_data);
        srNoData = (SmartRefreshLayout) findViewById(R.id.sr_no_data);
        rlArea = (RelativeLayout) findViewById(R.id.rl_area);
        rlDistance = (RelativeLayout) findViewById(R.id.rl_distance);
        rlScore = (RelativeLayout) findViewById(R.id.rl_score);
        ivDistance = (ImageView) findViewById(R.id.iv_distance);
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
                if (llNormal.getVisibility() == View.VISIBLE) {
                    llNormal.setVisibility(View.GONE);
                    llMap.setVisibility(View.VISIBLE);
                    iv_right_one.setImageResource(R.drawable.image_shop_text);
                } else {
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
        srNoData.setEnableLoadMore(false);
        adapter = new MoreShopsAdapter(this, datas, this);
        recyclerView.setAdapter(adapter);


    }


    @Override
    public void initData() {


        //定位
        startLocationCilent();

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
        viewModel.StoreShow(pageNo, pageSize, content, province, city, area, lat, lot, orderby, scoreBy);
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
                    srHasData.setVisibility(View.VISIBLE);
                    if (pageNo == 1) {
                        datas.clear();
                    }
                    datas.addAll(dto.getData());
                    adapter.notifyDataSetChanged();
                    initMapPointView(dto.getData());
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

    private void initMapPointView(List<StoreShowDto> data) {
        for (int i = 0; i <data.size() ; i++) {
            mPointList.add(new LatLng(data.get(i).getLatitude(), data.get(i).getLongitude()));
        }
        showPointsInBaiduMap(mPointList);

    }

    @Override
    public void initListener() {
        //RxJava过滤操作符的应用-实时搜索功能
        RxTextView.textChanges(etSearch)
                //监听输入完200毫秒之后发送事件
                .debounce(200, TimeUnit.MILLISECONDS)
                //跳过输入框EditText 初始化的的时候产生的事件。
                .skip(1)
                //把观察者切换到UI线程
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new rx.Observer<CharSequence>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(CharSequence charSequence) {
                        Log.e(TAG, "onNext: " + charSequence.toString());
                        content = etSearch.getText().toString();
                        pageNo = 1;
                        getStoreShowData();


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
        rlScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreBy = "1";
                pageNo = 1;
                showLoadingDialog();
                getStoreShowData();
            }
        });
        rlDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(orderby)) {
                    orderby = "2";
                    ivDistance.setImageResource(R.drawable.image_dis_bottom);
                } else {
                    orderby = "1";
                    ivDistance.setImageResource(R.drawable.image_dis_top);
                }
                pageNo = 1;
                showLoadingDialog();
                getStoreShowData();
            }
        });
        rlArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLoadingDialog().showDialog();
                new Thread() {
                    @Override
                    public void run() {
                        CityPickerView pickerView = getPickerInstance();

                        Message message = Message.obtain();
                        message.obj = pickerView;
                        message.what = 1001;
                        handler.sendMessage(message);
                    }
                }.start();
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
        this.lat = bdLocation.getLatitude();
        this.lot = bdLocation.getLongitude();
        PinziApplication.getInstance().mLocationClient.stop();
        //获取店铺展示的数据
        showLoadingDialog();
        getStoreShowData();
    }

    List<LatLng> mPointList = new ArrayList<>();
    private double lat;//false string  纬度（用户定位地址）
    private double lot;// false string 经度（用户定位地址）
    private String content;//    false string 搜索内容
    private String province;//   false string 省
    private String city;//false string 市
    private String area;//false string 区
    private String orderby = "1";//false string 1.距离从近到远 2.距离从远到近
    private String scoreBy;//true string 1.好评优先

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1001:
                    getLoadingDialog().dismiss();
                    CityPickerView pickerView = (CityPickerView) msg.obj;
                    pickerView.showCityPicker();
                    //监听选择点击事件及返回结果
                    pickerView.setOnCityItemClickListener(new OnCityItemClickListener() {
                        @Override
                        public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {

                            //省份
                            if (province != null) {
                                MoreShopsActivity.this.province = province.getName();
                            }

                            //城市
                            if (city != null) {
                                MoreShopsActivity.this.city = city.getName();
                            }

                            //地区
                            if (district != null) {
                                MoreShopsActivity.this.area = district.getName();
                            }
                            tvArea.setText(province.getName() + city.getName() + district.getName());
                            pageNo = 1;
                            showLoadingDialog();
                            getStoreShowData();

                        }

                        @Override
                        public void onCancel() {
                            ToastUitl.showImageToastFail("已取消");
                        }
                    });
                    break;
            }
        }
    };

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
