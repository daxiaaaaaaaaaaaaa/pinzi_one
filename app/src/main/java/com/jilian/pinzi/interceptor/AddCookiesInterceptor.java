package com.jilian.pinzi.interceptor;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.jilian.pinzi.Constant;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.common.dto.LoginDto;
import com.jilian.pinzi.utils.EmptyUtils;
import com.jilian.pinzi.utils.SPUtil;


import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 非首次请求的处理
 * @author : weishixiong
 * @create : 18/05/03
 */
public class AddCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        String cookieStr = SPUtil.getData(Constant.SP_VALUE.SP, Constant.SP_VALUE.SESSION_ID, String.class, null);
        List<String> cookies = JSONObject.parseArray(cookieStr, String.class);
        if (cookies != null) {
            for (String cookie : cookies) {
                builder.addHeader("Cookie", cookie);
                Log.v("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
            }
        }
       LoginDto loginDto =  PinziApplication.getInstance().getLoginDto();
        if(EmptyUtils.isNotEmpty(loginDto)
                &&EmptyUtils.isNotEmpty(loginDto.getSessionId())
                &EmptyUtils.isNotEmpty(loginDto.getPhone()))
        {
            builder.addHeader("phone", loginDto.getPhone());
            builder.addHeader("sessionId",loginDto.getSessionId());
        }

        return chain.proceed(builder.build());
    }
}

