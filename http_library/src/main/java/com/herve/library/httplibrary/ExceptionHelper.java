package com.herve.library.httplibrary;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.herve.library.commonlibrary.utils.LogUtils;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

public class ExceptionHelper {

    private static final int BADREQUEST = 400;
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ApiException returnException(Throwable e) {
        ApiException ex;

        if (e instanceof HttpException) {                     // HTTP错误
            HttpException httpException = (HttpException) e;
            ex = new ApiException(e, httpException.code());
            switch (httpException.code()) {
                case BADREQUEST:
                case UNAUTHORIZED:
                case FORBIDDEN:
                    break;
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex = new ApiException(e, ApiException.NETWORD_ERROR);
                    ex.setDisplayMessage("网络错误，请重试");          // 均视为网络错误
                    break;
            }
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException
                || e instanceof SocketTimeoutException) {
            ex = new ApiException(e, ApiException.REQUEST_FILED);
            ex.setDisplayMessage("连接失败");                  // 服务器连接失败
        } else if (e instanceof ApiException) {                 // 服务器返回的错误
            ApiException resultException = (ApiException) e;
            ex = new ApiException(e, resultException.getCode());
            ex.setDisplayMessage(resultException.getMessage());
        } else if (e instanceof JsonParseException || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ApiException(e, ApiException.PARSE_ERROR);
            ex.setDisplayMessage("解析错误");                  // 均视为解析错误
        } else {
            ex = new ApiException(e, ApiException.UNKNOWN);
            ex.setDisplayMessage("未知错误");                  // 未知错误
        }

        return ex;
    }

    public static String handleException(Throwable e) {
        e.printStackTrace();
        String error;
        if (e instanceof SocketTimeoutException) {//网络超时
            LogUtils.e("网络连接异常: " + e.getMessage());
            error = "网络连接异常";
        } else if (e instanceof ConnectException) { //均视为网络错误
            LogUtils.e("网络连接异常: " + e.getMessage());
            error = "网络连接异常";
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //均视为解析错误
            LogUtils.e("数据出错: " + e.getMessage());
            error = "数据解析异常";
        } else if (e instanceof ApiException) {//服务器返回的错误信息
            error = e.getMessage();
        } else if (e instanceof UnknownHostException) {
            LogUtils.e("网络连接异常: " + e.getMessage());
            error = "网络连接异常";
        } else if (e instanceof IllegalArgumentException) {
            LogUtils.e("非法的参数异常: " + e.getMessage());
            error = "非法的参数异常";
        } else {//未知错误
            try {
                LogUtils.e("错误: " + e.getMessage());
            } catch (Exception e1) {
                LogUtils.e("未知错误Debug调试 ");
            }
            error = "错误";
        }
        return error;
    }

    public static String handleException(Throwable e, String className, String url) {
        LogUtils.e("className: " + className);
        LogUtils.e("url: " + url);
        return handleException(e);
    }
}