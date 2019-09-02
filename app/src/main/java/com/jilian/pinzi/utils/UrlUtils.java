package com.jilian.pinzi.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UrlUtils {
    public static String getUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        } else {
            if (url.contains(",")) {
                String[] urlList = url.split(",");
                for (int i = 0; i < urlList.length; i++) {
                    if (!urlList[i].contains("mp4")) {
                        return urlList[i];
                    }
                }
            } else {
                return url;
            }
        }
        return url;
    }

    public static List<String> getUrlList(String url) {
        List<String> list = new ArrayList<>();
        if (TextUtils.isEmpty(url)) {
            return list;
        } else {
            if (url.contains(",")) {
                String[] array = url.split(",");
                list = new ArrayList<>(Arrays.asList(array));

            } else {
                list.add(url);

            }
            return list;
        }

    }

    /**
     * 拼接参数 没有编码
     * @param url
     * @param key
     * @param val
     * @return
     */
    public static String addUrlParamsNoEncode(String url, String key, String val) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        try {
            String and = "&";
            String dy = "=";
            if (!url.contains(key)) {
                if (url.contains("?")) {
                    url += and + key + dy + val;
                } else {
                    url += "?" + key + dy + val;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 拼接参数 编码
     * @param url
     * @param key
     * @param val
     * @return
     */
    public static String addUrlParamsEncode(String url, String key, String val) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        try {
            String and = URLEncoder.encode("&", "utf-8");
            String dy = URLEncoder.encode("=", "utf-8");
            if (!url.contains(key)) {
                if (url.contains("?")) {
                    url += and + key + dy + val;
                } else {
                    url += "?" + key + dy + val;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }
}
