package com.kborid.jsbridge;

import com.kborid.jsbridge.wvjb.WVJBJavascriptCallback;
import com.kborid.jsbridge.wvjb.WVJBResponseCallback;
import com.kborid.jsbridge.wvjb.handler.WVJBHandler;

public interface IWVJBClient {
    String TAG = IWVJBClient.class.getSimpleName();
    //默认log打印开关
    boolean LOG = true;

    String JS_FILE_NAME = "WebViewJavascriptBridge.js";

    String kInterface = "WVJBInterface";
    String kCustomProtocolScheme = "wvjbscheme";
    String kQueueHasMessage = "__WVJB_QUEUE_MESSAGE__";
    String kCallbackId = "objc_cb_";
    String kJavaScript = "javascript:";

    String JS_HANDLE_MSG_FROM_JAVA = "WebViewJavascriptBridge._handleMessageFromObjC('%s');";
    String JS_FETCH_QUEUE_FROM_JAVA = "WebViewJavascriptBridge._fetchQueue();";
    String JS_RESULT_FOR_SCRIPT = "window.%s.onResultForScript(%s,%s)";

    void enableLogging(boolean enable);

    void executeJavascript(String script);

    void executeJavascript(final String script, final WVJBJavascriptCallback callback);

    void callHandler(String handlerName);

    void callHandler(String handlerName, Object data);

    void callHandler(String handlerName, Object data, WVJBResponseCallback responseCallback);

    void registerHandler(String handlerName, WVJBHandler handler);
}
