package com.kborid.jsbridge.wvjb.handler;

import com.kborid.jsbridge.wvjb.WVJBResponseCallback;

/**
 * js默认请求处理
 */
public class WVJBDefaultHandler implements WVJBHandler {

    private static final String TAG = WVJBDefaultHandler.class.getSimpleName();

    @Override
    public void request(Object data, WVJBResponseCallback callback) {
        if (callback != null) {
            callback.callback("DefaultHandler response data");
        }
    }
}
