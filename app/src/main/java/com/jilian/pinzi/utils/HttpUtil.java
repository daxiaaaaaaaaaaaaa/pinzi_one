package com.jilian.pinzi.utils;


import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jilian.pinzi.base.BaseVo;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * HttpUtil 工具类，把vo转换成map
 *
 * @author weishixiong
 * @Time 2018-03-19
 */
public class HttpUtil {
    /**
     * 上传单个对象的文件
     * 对象中包含文件数组
     * files转换为MultipartBody
     *
     * @param files
     * @return
     */
    public static MultipartBody filesToMultipartBody(BaseVo vo, List<File> files, String mediaType) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (File file : files) {
            //  这里为了简单起见，没有判断file的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse(mediaType), file);
            builder.addFormDataPart("file", file.getName(), requestBody);

        }
        if (EmptyUtils.isNotEmpty(vo)) {
            Map<String, String> map = HttpUtil.convertVo2Params(vo);
            for (String key : map.keySet()) {
                builder.addFormDataPart(key, map.get(key));
            }
        }
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }
    /**
     * vo转换为Map
     *
     * @param vo
     * @return
     */
    public static RequestBody convertVo2Json(BaseVo vo) {
        Map maps = convertVo2Params(vo);
        JSONObject object = new JSONObject(maps);
        return RequestBody.create(MediaType.parse("Content-Type, application/json"),
                "{\"data\":" + object.toString() + "}");
    }


    /**
     * vo转换为Map
     *
     * @param vo
     * @return
     */
    public static Map<String, String> convertVo2Params(BaseVo vo) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> map = gson.fromJson(gson.toJson(vo), type);
        // 移除值为空的键值对
        removeNullValue(map);
        return map;
    }
    /*移除Map中值为空的键值对*/
    public static void removeNullEntry(Map map) {
        removeNullKey(map);
        removeNullValue(map);
    }
    /*移除键为空的键值对*/
    public static void removeNullKey(Map map) {
        Set set = map.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext(); ) {
            Object obj = (Object) iterator.next();
            remove(obj, iterator);
        }
    }
    /*移除值为空的键值对*/
    public static void removeNullValue(Map map) {
        Set set = map.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext(); ) {
            Object obj = (Object) iterator.next();
            Object value = (Object) map.get(obj);
            remove(value, iterator);
        }
    }

    private static void remove(Object obj, Iterator iterator) {
        if (obj instanceof String) {
            String str = (String) obj;
            if (str == null || str.trim().isEmpty()) {
                iterator.remove();
            }
        } else if (obj instanceof Collection) {
            Collection col = (Collection) obj;
            if (col == null || col.isEmpty()) {
                iterator.remove();
            }

        } else if (obj instanceof Map) {
            Map temp = (Map) obj;
            if (temp == null || temp.isEmpty()) {
                iterator.remove();
            }

        } else if (obj instanceof Object[]) {
            Object[] array = (Object[]) obj;
            if (array == null || array.length <= 0) {
                iterator.remove();
            }
        } else {
            if (obj == null) {
                iterator.remove();
            }
        }
    }

    /**
     * 多文件转换为 Map<String, RequestBody> bodyMap
     *
     * @param files
     * @param mediaType 文件类型
     * @return
     */
    public static Map<String, RequestBody> convertVo2BodyMap(List<File> files, String mediaType) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        for (int i = 0; i < files.size(); i++) {
            bodyMap.put("file" + i + "\"; filename=\"" + files.get(i).getName(), RequestBody.create(MediaType.parse(mediaType), files.get(i)));
        }
        return bodyMap;
    }

}
