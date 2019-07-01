package com.jilian.pinzi;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.ssl.SslContextFactory;
import com.jilian.pinzi.utils.SPUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLSocketFactory;

import io.rong.imkit.RongIM;

//import android.content.res.Configuration;
//import android.content.res.Configuration;
/**
 * 全局application
 *
 * @author weishixiong
 * @Time 2018-03-16
 */
public class PinziApplication extends MultiDexApplication {
    private static final String TAG = "CcsApplication";
    private static PinziApplication instance;

    /**
     * 存放所有的activity
     */
    public static List<Activity> runActivities = new ArrayList<>();
    /**
     * 上下文
     */
    private static Context context;

    public static Context getContext() {
        return context;
    }

    private Integer wxPayType;//微信支付 类型 1.商品 2.优惠券

    public Integer getWxPayType() {
        return wxPayType;
    }

    public void setWxPayType(Integer wxPayType) {
        this.wxPayType = wxPayType;
    }



    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;

    public IWXAPI getApi() {
        return api;
    }

    public void setApi(IWXAPI api) {
        this.api = api;
    }
    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID, true);

        // 将应用的appId注册到微信
        api.registerApp(Constant.APP_ID);

    }
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        instance = this;
       initYOUMENG();
        initBaidu();
        context = getApplicationContext();
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        //初始化 bugly 崩溃收集  13129554264
        CrashReport.initCrashReport(getApplicationContext(), "ee27ce9482", false);
        //
        Logger.addLogAdapter(new AndroidLogAdapter());
        // 初始化融云
        RongIM.init(this);
        initWxPay();
        regToWx();

    }

    private void initYOUMENG() {
        /**
         * 初始化common库
         * 参数1:上下文，必须的参数，不能为空
         * 参数2:友盟 app key，非必须参数，如果Manifest文件中已配置app key，该参数可以传空，则使用Manifest中配置的app key，否则该参数必须传入
         * 参数3:友盟 channel，非必须参数，如果Manifest文件中已配置channel，该参数可以传空，则使用Manifest中配置的channel，否则该参数必须传入，channel命名请详见channel渠道命名规范
         * 参数4:设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机
         * 参数5:Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空
         */
        UMConfigure.init(this,"5d19bc014ca3574b380002bc","umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        /**
         * 友盟相关平台配置。注意友盟官方新文档中没有这项配置，但是如果不配置会吊不起来相关平台的授权界面
         */
        PlatformConfig.setWeixin(Constant.APP_ID, Constant.WXAPP_SECRET);//微信APPID和AppSecret
        PlatformConfig.setQQZone("你的QQAPPID", "你的QQAppSecret");//QQAPPID和AppSecret
        PlatformConfig.setSinaWeibo("你的微博APPID", "你的微博APPSecret","微博的后台配置回调地址");//微博
    }


    public LocationClient mLocationClient = null;

    public LocationClient getmLocationClient() {
        return mLocationClient;
    }

    public void setmLocationClient(LocationClient mLocationClient) {
        this.mLocationClient = mLocationClient;
    }

    /**
     * 初始化百度地图
     */
    private void initBaidu() {
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        mLocationClient = new LocationClient(this);     //声明LocationClient类
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(true);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
    /**
     * 注册微信支付
     */
    private void initWxPay() {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
        // 将该app注册到微信
        msgApi.registerApp("wxd930ea5d5a258f4f");

    }

    //如果应用屏幕固定了某个方向不旋转的话(比如qq和微信),下面可不写.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public LoginDto getLoginDto(){
        return SPUtil.getData(Constant.SP_VALUE.SP,Constant.SP_VALUE.LOGIN_DTO,LoginDto.class,null);
    }

    /**
     * 获取https证书
     *
     * @return
     */
    public static SSLSocketFactory getSslSocket() {
        return SslContextFactory.getSSLSocketFactory(getInstance());
    }

    /**
     * 添加一个正在运行的Activity进入容器
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        runActivities.add(activity);
    }

    /**
     * 移除被销毁的Activiiy
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        runActivities.remove(activity);
    }

    public static PinziApplication getInstance() {
        return instance;
    }

    /**
     * 释放所有正在运行的Activity
     */
    public static void clearAllActivitys() {
        for (int i = 0; i < runActivities.size(); i++) {
            runActivities.get(i).finish();
        }
        runActivities.clear();
    }

    /**
     * 释放指定的activity
     *
     * @param exclude 要finish的Activity
     */
    public static void clearSpecifyActivities(Class<Activity> exclude[]) {
        for (int i = 0; i < runActivities.size(); i++) {
            Activity a = runActivities.get(i);
            if (a != null && Arrays.asList(exclude).contains(a.getClass())) {
                a.finish();
                runActivities.remove(a);
            }
        }
    }



    //是否有下方虚拟栏
    public static boolean isNavigationBarAvailable() {
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);
        return (!(hasBackKey && hasHomeKey));
    }

    /**
     *
     获取下方虚拟栏高度  
     * @return
     */
    public static int getNavigationBarHeight() {
        if (isNavigationBarAvailable()) {
            Resources resources = PinziApplication.getInstance().getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                return resources.getDimensionPixelSize(resourceId);
            }
        }
        return 0;
    }


}