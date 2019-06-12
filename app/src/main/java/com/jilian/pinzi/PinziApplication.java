package com.jilian.pinzi;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.StrictMode;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLSocketFactory;

import io.rong.imkit.RongIM;
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


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

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