package com.jilian.pinzi.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;


import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.R;
import com.jilian.pinzi.adapter.MoreShopsAdapter;
import com.jilian.pinzi.base.BaseActivity;
import com.jilian.pinzi.base.BaseDto;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.NonNull;

/**
 * 更多店铺
 */
public class MoreShopsActivity extends BaseActivity implements CustomItemClickListener, ViewPagerItemClickListener, AMapLocationListener {

    private RecyclerView recyclerView;
    private MoreShopsAdapter adapter;
    private List<StoreShowDto> datas;
    private GridLayoutManager gridLayoutManager;
    private SmartRefreshLayout srHasData;
    private SmartRefreshLayout srNoData;
    private MainViewModel viewModel;
    private LinearLayout llNormal;
    private LinearLayout llMap;

    private RelativeLayout rlArea;
    private RelativeLayout rlDistance;
    private RelativeLayout rlScore;
    private EditText etSearch;
    private ImageView ivDistance;
    private TextView tvArea;

    private MapView mapView;
    private AMap aMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        aMap = mapView.getMap();
        PinziApplication.addActivity(this);

        //获取店铺展示的数据
        showLoadingDialog();
        initGaode();
        //定位
        //启动定位
        mlocationClient.startLocation();

        aMap.moveCamera(CameraUpdateFactory.zoomTo(14));//方法设置地图的缩放级别，记住带在地图初始化的时候调用，而非定位成功后调用。
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

    }

    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    private void initGaode() {

        mlocationClient = new AMapLocationClient(this);
//初始化定位参数
        mLocationOption = new AMapLocationClientOption();
//设置定位监听
        mlocationClient.setLocationListener(this);
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
//设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
// 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
// 在定位结束后，在合适的生命周期调用onDestroy()方法
// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sd
//
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        // 定义 Marker 点击事件监听
        AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
            // marker 对象被点击时回调的接口
            // 返回 true 则表示接口已响应事件，否则返回false
            @Override
            public boolean onMarkerClick(Marker marker) {
                int index = marker.getPeriod();
                Log.e(TAG, "onMarkerClick: "+index );
                //获取点击的位置
                LatLng latLng = marker.getPosition();
                for (int i = 0; i < mPointList.size(); i++) {
                    //同一个对象
                    if (mPointList.get(i).equals(latLng)) {
                        StoreShowDto dto = datas.get(i);
                        //在这里跳转到店铺展示的界面
                        ShopDetailActivity.startActivity(MoreShopsActivity.this, dto.getId(), 2, latLng.latitude, latLng.longitude);
                        break;
                    }
                }
                return false;
            }
        };
// 绑定 Marker 被点击事件
        aMap.setOnMarkerClickListener(markerClickListener);


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
                    initMapPointView(datas);
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

    /**
     * 展示 地图各个点
     *
     * @param data
     */
    private void initMapPointView(List<StoreShowDto> data) {
        mPointList.clear();
        for (int i = 0; i < data.size(); i++) {

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
        ShopDetailActivity.startActivity(this, datas.get(position).getId(), 2, datas.get(position).getLatitude(), datas.get(position).getLongitude());
    }

    @Override
    public void onViewPageItemClick(View view, int position) {

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
        for (int i = 0; i < pointList.size(); i++) {
            MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(), R.drawable.image_map_location))).position(pointList.get(i)).title(datas.get(i).getName());
            aMap.addMarker(markerOption);
        }


    }


    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        mlocationClient.stopLocation();
        if (lat != 0 && lot != 0) {
            return;
        }
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                lat = amapLocation.getLatitude();//获取纬度
                lot = amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                getStoreShowData();


            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
                ToastUitl.showImageToastFail("定位失败");
                getStoreShowData();
            }
        } else {
            ToastUitl.showImageToastFail("定位失败");
            getStoreShowData();
        }


    }
}
