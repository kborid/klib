package com.kborid.jsbridge.wvjb.handler;

import com.kborid.jsbridge.wvjb.WVJBResponseCallback;

/**
 * js请求处理函数
 */
public interface WVJBHandler {
    /**
     * @param data     请求参数
     * @param callback 回调接口
     */
    void request(Object data, WVJBResponseCallback callback);
}
