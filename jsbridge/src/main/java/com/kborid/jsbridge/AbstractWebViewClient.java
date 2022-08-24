package com.kborid.jsbridge;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import com.kborid.jsbridge.wvjb.WVJBJavascriptCallback;
import com.kborid.jsbridge.wvjb.WVJBJavascriptInterface;
import com.kborid.jsbridge.wvjb.WVJBMessage;
import com.kborid.jsbridge.wvjb.WVJBResponseCallback;
import com.kborid.jsbridge.wvjb.handler.WVJBDefaultHandler;
import com.kborid.jsbridge.wvjb.handler.WVJBHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWebViewClient implements IWVJBClient {

    boolean logging = LOG;

    static long uniqueId = 0;
    private ArrayList<WVJBMessage> startupMessageQueue = null;
    Map<String, WVJBResponseCallback> responseCallbacks = null;
    Map<String, WVJBHandler> messageHandlers = null;
    private WVJBHandler defaultHandler;
    private WVJBJavascriptInterface kJavascriptInterface = new WVJBJavascriptInterface();
    private String mNativeJS = null;

    private IWVJBWebViewAction webView;
    private WVJBPageListener listener;

    private WebViewClient webViewClient = new WebViewClient() {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (null != listener) {
                listener.onPageStart(url);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            log("onPageFinished", "url=" + url);
            loadJBJS(view.getContext());
            super.onPageFinished(view, url);
            if (null != listener) {
                listener.onPageFinish(url);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            log("shouldOverrideUrlLoading", "url=" + url);
            if (overrideUrlLoading(url)) {
                return true;
            }
            if (null != listener && listener.shouldOverrideUrlLoading(url)) {
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    };

    public AbstractWebViewClient() {
        responseCallbacks = new HashMap<String, WVJBResponseCallback>();
        messageHandlers = new HashMap<String, WVJBHandler>();
        startupMessageQueue = new ArrayList<WVJBMessage>();
        defaultHandler = new WVJBDefaultHandler();
    }

    protected <T> void setWebViewClientInner(T view, WVJBPageListener listener) {
        this.listener = listener;
        this.webView = (IWVJBWebViewAction) view;
        this.webView.addJavascriptInterfacee(kJavascriptInterface, kInterface);
        WebView webView = (WebView) view;
        webView.setWebViewClient(webViewClient);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setGeolocationEnabled(true); // 启用地理定位
        webView.getSettings().setAllowFileAccess(false);// 设置访问文件
//            webView.getSettings().setNeedInitialFocus(true); // 当webview调用requestFocus时为webview设置节点
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true); // 支持通过JS打开新窗口
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH); // 设置渲染优先级
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS); //支持内容重新布局
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); //webview缓存模式
        webView.getSettings().setUseWideViewPort(true); // 将图片调整到适合webview的大小
        webView.getSettings().setLoadWithOverviewMode(true); // 充满全屏
        webView.getSettings().setAppCacheEnabled(true); // 开启缓存功能
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDatabasePath("/data/data");
        webView.getSettings().setDomStorageEnabled(true); // 开启Dom存储Api(启用地图、定位之类的都需要)
    }

    private void loadJBJS(Context context) {
        InputStream is = null;
        try {
            if (mNativeJS == null) {
                is = context.getAssets().open(JS_FILE_NAME);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                mNativeJS = new String(buffer);
            }
            executeJavascript(mNativeJS);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(is);
        }

        if (startupMessageQueue != null) {
            for (WVJBMessage message : startupMessageQueue) {
                dispatchMessage(message);
            }
            startupMessageQueue = null;
        }
    }

    private boolean overrideUrlLoading(String url) {
        if (url.startsWith(kCustomProtocolScheme)) {
            if (url.indexOf(kQueueHasMessage) > 0) {
                flushMessageQueue();
            }
            return true;
        } else {
            return !url.startsWith("http:") && !url.startsWith("https:");
        }
    }

    void log(String action, Object json) {
        if (!logging)
            return;
        Log.i(TAG, String.format("[%s]:%s", action, json));
    }

    @Override
    public void executeJavascript(String script) {
        executeJavascript(script, null);
    }

    @Override
    public void executeJavascript(final String script, final WVJBJavascriptCallback callback) {
        log("executeJavascript", "script=" + script);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                webView.evaluateJavascriptt(script, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        if (callback != null) {
                            if (value != null && value.startsWith("\"") && value.endsWith("\"")) {
                                value = value.substring(1, value.length() - 1).replace("\\\"", "\"").replace("\\\\", "\\");
                            }
                            callback.onReceiveValue(value);
                        }
                    }
                });
            }
        } else {
            if (callback != null) {
                kJavascriptInterface.addCallback(String.valueOf(++uniqueId), callback);
                webView.postt(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrll(kJavaScript + String.format(JS_RESULT_FOR_SCRIPT, kInterface, uniqueId, script));
                    }
                });
            } else {
                webView.postt(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrll(kJavaScript + script);
                    }
                });
            }
        }
    }

    void queueMessage(WVJBMessage message) {
        log("queueMessage", "queueMessage() message=" + message);
        if (startupMessageQueue != null) {
            startupMessageQueue.add(message);
        } else {
            dispatchMessage(message);
        }
    }

    private void dispatchMessage(WVJBMessage message) {
        log("dispatchMessage", message.getResponseData());
        String messageJSON = WVJBMessage.message2JsonString(message).replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\"").replaceAll("\'", "\\\\\'").replaceAll("\n", "\\\\\n")
                .replaceAll("\r", "\\\\\r").replaceAll("\f", "\\\\\f");
        log("dispatchMessage", messageJSON);
        executeJavascript(String.format(JS_HANDLE_MSG_FROM_JAVA, messageJSON));
    }

    private void flushMessageQueue() {
        log("flushMessageQueue", "flushMessageQueue()");
        executeJavascript(JS_FETCH_QUEUE_FROM_JAVA, new WVJBJavascriptCallback() {
            public void onReceiveValue(String messageQueueString) {
                log("onReceiveValue", "messageQueueString = " + messageQueueString);
                if (TextUtils.isEmpty(messageQueueString)) {
                    return;
                }
                processQueueMessage(messageQueueString);
            }
        });
    }

    private void processQueueMessage(String messageQueueString) {
        log("processQueueMessage", "processQueueMessage() messageQueueString=" + messageQueueString);
        try {
            JSONArray messages = JSON.parseArray(messageQueueString);
            for (Object object : messages) {
                JSONObject jo = (JSONObject) object;
                log("processQueueMessage", jo);

                WVJBMessage message = WVJBMessage.JsonString2Message(jo.toString());
                if (message.getResponseId() != null) {
                    WVJBResponseCallback responseCallback = responseCallbacks.remove(message.getResponseId());
                    if (responseCallback != null) {
                        responseCallback.callback(message.getResponseData());
                    }
                } else {
                    WVJBResponseCallback responseCallback = null;
                    if (message.getCallbackId() != null) {
                        final String callbackId = message.getCallbackId();
                        responseCallback = new WVJBResponseCallback() {
                            @Override
                            public void callback(Object data) {
                                WVJBMessage msg = new WVJBMessage();
                                msg.setResponseId(callbackId);
                                msg.setResponseData(data);
                                queueMessage(msg);
                            }
                        };
                    }

                    WVJBHandler handler;
                    log("handler", "handlerName=" + message.getHandlerName());
                    if (!TextUtils.isEmpty(message.getHandlerName())) {
                        handler = messageHandlers.get(message.getHandlerName());
                    } else {
                        handler = defaultHandler;
                    }
                    if (handler != null) {
                        log("handler", "handler request()");
                        handler.request(message.getData(), responseCallback);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
