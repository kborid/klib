package com.kborid.jsbridge;

import android.content.Context;
import android.text.TextUtils;

import com.kborid.jsbridge.wvjb.WVJBMessage;
import com.kborid.jsbridge.wvjb.WVJBResponseCallback;
import com.kborid.jsbridge.wvjb.handler.WVJBHandler;

/**
 * webview 与js交互桥梁创建，实现数据的对接交互
 *
 * @author kborid
 */
public class WVJBWebViewClient extends AbstractWebViewClient {


    public static WVJBWebViewClient builder() {
        return new WVJBWebViewClient();
    }

    public <T> WVJBWebViewClient setWebViewClient(T view, WVJBPageListener listener) {
        setWebViewClientInner(view, listener);
        return this;
    }

    public WVJBWebViewClient initHandler(RegisterHandler handler, Context context) throws Exception {
        handler.initHandle(this, context);
        return this;
    }

    /**
     * 是否打印log
     *
     * @param enable
     */
    @Override
    public void enableLogging(boolean enable) {
        logging = enable;
    }

    /**
     * 发送
     *
     * @param handlerName
     * @param data
     * @param responseCallback
     */
    private void doSend(String handlerName, Object data, WVJBResponseCallback responseCallback) {
        log("doSend", "sendData() name=" + handlerName);
        if (TextUtils.isEmpty(handlerName))
            return;
        WVJBMessage message = new WVJBMessage();
        message.setHandlerName(handlerName);
        message.setData(data);
        if (responseCallback != null) {
            String callbackId = kCallbackId + (++uniqueId);
            responseCallbacks.put(callbackId, responseCallback);
            message.setCallbackId(callbackId);
        }
        queueMessage(message);
    }

    /**
     * 调用js方法
     *
     * @param handlerName
     */
    @Override
    public void callHandler(String handlerName) {
        callHandler(handlerName, null);
    }

    /**
     * 调用js方法
     *
     * @param handlerName
     * @param data
     */
    @Override
    public void callHandler(String handlerName, Object data) {
        callHandler(handlerName, data, null);
    }

    /**
     * 调用js方法
     *
     * @param handlerName
     * @param data
     * @param responseCallback
     */
    @Override
    public void callHandler(String handlerName, Object data, WVJBResponseCallback responseCallback) {
        log("callHandler", "callHandler() name=" + handlerName);
        doSend(handlerName, data, responseCallback);
    }

    /**
     * 注册处理程序，使JavaScript可以调用
     *
     * @param handlerName
     * @param handler
     */
    @Override
    public void registerHandler(String handlerName, WVJBHandler handler) {
        log("registerHandler", "registerHandler() name=" + handlerName);
        if (TextUtils.isEmpty(handlerName)) {
            return;
        }
        messageHandlers.put(handlerName, handler);
    }
}
