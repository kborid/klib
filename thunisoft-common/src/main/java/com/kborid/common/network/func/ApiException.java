package com.kborid.common.network.func;

import com.orhanobut.logger.Logger;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

/**
 * ApiException
 *
 * @description: 接口请求Exception处理类
 * @date: 2019/10/25
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 2.0.0
 */
public class ApiException extends RuntimeException {
    private static final String TAG = ApiException.class.getSimpleName();

    public static final int OK = 0;
    public static final int SUCCESS = 200;

    //客户端错误
    public static final int ERROR_UNKNOWN = -1;
    public static final int ERROR_NETWORK = 1;
    public static final int ERROR_PARSE = 2;
    public static final int ERROR_UNAUTHORIZED = 401; //("Unauthorized")

    //服务器错误
    public static final int ERROR_SERVER_INTERNAL = 500; //("Internal Server Error")
    public static final int ERROR_SERVER_NOT_IMPLEMENTED = 501; //("Not Implemented")
    public static final int ERROR_SERVER_BAD_GATEWAY = 502; //("Bad Gateway");
    public static final int ERROR_SERVER_UNAVAILABLE = 503; //("Service Unavailable")
    public static final int ERROR_SERVER_GATEWAY = 504; //("Gateway Timeout")
    public static final int ERROR_SERVER_NOT_SUPPORTED = 505; //("HTTP Version Not Supported")

    public int code;
    public String msg;

    public ApiException(int code) {
        this(code, getExceptionMessage(code));
    }

    public ApiException(String msg) {
        this(ERROR_UNKNOWN, msg);
    }

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public static ApiException handleException(Throwable throwable) {
        Logger.t(TAG).e(throwable, "handleException()");
        if (throwable instanceof ConnectException
                || throwable instanceof SocketTimeoutException) {
            return new ApiException(ERROR_NETWORK);
        } else if (throwable instanceof JSONException
                || throwable instanceof com.alibaba.fastjson.JSONException) {
            return new ApiException(ERROR_PARSE);
        } else if (throwable instanceof HttpException) {
            return new ApiException(((HttpException) throwable).code());
        } else if (throwable instanceof ApiException) {
            return (ApiException) throwable;
        } else {
            return new ApiException(throwable.getMessage());
        }
    }

    /**
     * 获取接口错误码对应的消息说明
     *
     * @param code
     * @return
     */
    private static String getExceptionMessage(int code) {
        String msg = "";
        switch (code) {
            case ERROR_NETWORK:
                msg = "无法连接到服务器";
                break;
            case ERROR_SERVER_INTERNAL:
            case ERROR_SERVER_NOT_IMPLEMENTED:
            case ERROR_SERVER_BAD_GATEWAY:
            case ERROR_SERVER_UNAVAILABLE:
            case ERROR_SERVER_GATEWAY:
            case ERROR_SERVER_NOT_SUPPORTED:
                msg = "服务器错误";
                break;
            case ERROR_UNAUTHORIZED:
                msg = "Token过期或无效";
                break;
            case ERROR_PARSE:
                msg = "解析错误";
                break;
            default:
                msg = "请求失败，请重试";
                break;
        }
        Logger.t(TAG).e("getExceptionMessage() %s", msg);
        return msg;
    }
}
