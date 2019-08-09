package com.jilian.pinzi.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;


import com.amap.api.maps2d.model.LatLng;

import java.util.List;

/**
 * 地图相关工具类
 */
public class MapUtils {
    private static final String TAG ="MapUtils" ;

    /**
     * 判断 地图是否安卓
     * @param packageName 包名
     * @param mContext 上下文
     * @return
     */
    public static  boolean isInstalled(String packageName, Context mContext) {
        PackageManager manager = mContext.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> installedPackages = manager.getInstalledPackages(0);
        if (installedPackages != null) {
            for (PackageInfo info : installedPackages) {
                if (info.packageName.equals(packageName))
                    return true;
            }
        }
        return false;
    }


    /**
     * 跳转百度地图
     * @param context 上下文
     * @param mLat 维度
     * @param mLng 精度
     * @param mAddressStr 地址
     */
    public static  void goToBaiduMap(Context context,Double mLat,Double mLng,String mAddressStr) {
//        LatLng latLng = GCJ2BD(new LatLng(mLat,mLng));
        Log.e(TAG, "goToBaiduMap: "+ mLat+":"+mLng+":"+mAddressStr);
        if (!isInstalled("com.baidu.BaiduMap",context)) {
            ToastUitl.showImageToastFail("请先安装百度地图客户端");
            return;
        }
        Intent intent = new Intent();
        intent.setData(Uri.parse("baidumap://map/direction?destination=latlng:"
                + mLat + ","
                + mLng + "|name:" + mAddressStr + // 终点
                "&mode=driving" + // 导航路线方式
                "&src=" + context.getPackageName()));
        context.startActivity(intent); // 启动调用
    }

    /**
     * 跳转高德地图
     * @param context
     * @param mLat
     * @param mLng
     * @param mAddressStr
     */
    public static  void goToGaodeMap(Context context,Double mLat,Double mLng,String mAddressStr) {
        Log.e(TAG, "goToGaodeMap: "+ mLat+":"+mLng+":"+mAddressStr);
        if (!isInstalled("com.autonavi.minimap",context)) {
            ToastUitl.showImageToastFail("请先安装高德地图客户端");
            return;
        }
        LatLng endPoint = new LatLng(mLat, mLng);//百度——>阿里云
        StringBuffer stringBuffer = new StringBuffer("androidamap://navi?sourceApplication=").append("amap");
        stringBuffer.append("&lat=").append(endPoint.latitude)
                .append("&lon=").append(endPoint.longitude).append("&keywords=" + mAddressStr)
                .append("&dev=").append(0)
                .append("&style=").append(2);
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }

    /**
     * 跳转腾讯地图
     * @param context
     * @param mLat
     * @param mLng
     * @param mAddressStr
     */
    public static  void goToTencentMap(Context context,Double mLat,Double mLng,String mAddressStr) {
        Log.e(TAG, "goToTencentMap: "+ mLat+":"+mLng+":"+mAddressStr);
        if (!isInstalled("com.tencent.map",context)) {
            ToastUitl.showImageToastFail( "请先安装腾讯地图客户端");
            return;
        }
        LatLng endPoint = BD2GCJ(new LatLng(mLat, mLng));//坐标转换
        StringBuffer stringBuffer = new StringBuffer("qqmap://map/routeplan?type=drive")
                .append("&tocoord=").append(endPoint.latitude).append(",").append(endPoint.longitude).append("&to=" + mAddressStr);
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()));
        context.startActivity(intent);
    }


    /**
     * BD-09 坐标转换成 GCJ-02 坐标
     */
    public static LatLng BD2GCJ(LatLng bd) {
        double x = bd.longitude - 0.0065, y = bd.latitude - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Math.PI);

        double lng = z * Math.cos(theta);//lng
        double lat = z * Math.sin(theta);//lat
        return new LatLng(lat, lng);
    }

    /**
     * GCJ-02 坐标转换成 BD-09 坐标
     */
    public static LatLng GCJ2BD(LatLng bd) {
        double x = bd.longitude, y = bd.latitude;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * Math.PI);
        double tempLon = z * Math.cos(theta) + 0.0065;
        double tempLat = z * Math.sin(theta) + 0.006;
        return new LatLng(tempLat, tempLon);
    }

}
