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
import java.util.LinkedHashMap;
import java.util.List;

import javax.net.ssl.SSLSocketFactory;

import cn.jzvd.JZDataSource;
import cn.jzvd.JzvdStd;
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
        UMConfigure.init(this, "5d19bc014ca3574b380002bc", "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        /**
         * 友盟相关平台配置。注意友盟官方新文档中没有这项配置，但是如果不配置会吊不起来相关平台的授权界面
         */
        PlatformConfig.setWeixin(Constant.APP_ID, Constant.WXAPP_SECRET);//微信APPID和AppSecret

        PlatformConfig.setQQZone("1109240675", "GXQmVq9zyOPswdeC");//QQAPPID和AppSecret

        PlatformConfig.setSinaWeibo("1582232369", "74e0e851fe199ec2f32bac9b9dcb15d3", "http://39.108.14.94:9010");//微博
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

    public LoginDto getLoginDto() {
        return SPUtil.getData(Constant.SP_VALUE.SP, Constant.SP_VALUE.LOGIN_DTO, LoginDto.class, null);
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
     * 获取下方虚拟栏高度  
     *
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