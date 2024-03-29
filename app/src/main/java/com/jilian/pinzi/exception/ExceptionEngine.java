package com.jilian.pinzi.exception;

import android.net.ParseException;
import android.util.MalformedJsonException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

public class ExceptionEngine {
    //客户端报错
    public static final int UN_KNOWN_ERROR = 9000;//未知错误
    public static final int ANALYTIC_SERVER_DATA_ERROR = 9001;//解析(服务器)数据错误
    public static final int ANALYTIC_CLIENT_DATA_ERROR = 9002;//解析(客户端)数据错误
    public static final int CONNECT_ERROR = 9003;//网络连接错误
    public static final int TIME_OUT_ERROR = 9004;//网络连接超时
    public static final int UNKNOWNHOSTEXCEPTION = 9005;//网络连接超时

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof HttpException) {             //HTTP错误
            HttpException httpExc = (HttpException) e;
            ex = new ApiException(e, httpExc.code());
            ex.setMsg("网络连接错误，请稍后再试");  //均视为网络错误
            return ex;
        } else if (e instanceof ServerException) {    //服务器返回的错误
           ServerException serverExc = (ServerException) e;
            ex = new ApiException(serverExc, serverExc.getCode());
            ex.setMsg(serverExc.getMsg());
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException || e instanceof MalformedJsonException) {  //解析数据错误
            ex = new ApiException(e, ANALYTIC_SERVER_DATA_ERROR);
            ex.setMsg("客户端异常，请稍后再试");
            return ex;
        } else if (e instanceof ConnectException) {//连接网络错误
            ex = new ApiException(e, CONNECT_ERROR);
            ex.setMsg("网络连接错误，请稍后再试");
            return ex;
        } else if (e instanceof SocketTimeoutException) {//网络超时
            ex = new ApiException(e, TIME_OUT_ERROR);
            ex.setMsg("服务器连接超时，请稍后再试");
            return ex;
        }
        else if (e instanceof UnknownHostException) {//网络异常
            ex = new ApiException(e, UNKNOWNHOSTEXCEPTION);
            ex.setMsg("网络连接错误，请稍后再试");
            return ex;
        }
        else {  //未知错误
            ex = new ApiException(e, UN_KNOWN_ERROR);
            ex.setMsg("系统异常，请稍后再试");
            return ex;
        }
    }

}