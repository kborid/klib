package com.kborid.jsbridge.wvjb;

/**
 * Js回调处理
 */
public interface WVJBJavascriptCallback {

    /**
     * 接收获取接收信息
     *
     * @param value 接收到得js的值
     */
    void onReceiveValue(String value);
}
