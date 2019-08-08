package com.jilian.pinzi.utils;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;


/**
 * 经纬度坐标转换
 *
 * @author weishixiong
 * @Time 2018-03-19
 */
public class ChanLatLonUtils {
    /**
     * // 将google地图、soso地图、aliyun地图、mapabc地图和amap地图// 所用坐标转换成百度坐标
     *
     * @param sourceLatLng
     * @return
     */
    public static LatLng aliyunToBaiDu(LatLng sourceLatLng) {
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.COMMON);
        // sourceLatLng待转换坐标
        converter.coord(sourceLatLng);
        LatLng desLatLng = converter.convert();
        return desLatLng;
    }

    /**
     * /将GPS设备采集的原始GPS坐标转换成百度坐标
     *
     * @param sourceLatLng
     * @return
     */
    public static LatLng gpsToBaiDu(LatLng sourceLatLng) {
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.COMMON);
        // sourceLatLng待转换坐标
        converter.coord(sourceLatLng);
        LatLng desLatLng = converter.convert();
        return desLatLng;
    }

    /**
     * 百度地图坐标，转换成阿里地坐标
     *
     * @param sourceLatLng
     * @return
     */
    public static LatLng baiduToAliyun(LatLng sourceLatLng) {
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.BD09LL).coord(sourceLatLng);
        LatLng desLatLng = converter.convert();
        return desLatLng;
    }


}
