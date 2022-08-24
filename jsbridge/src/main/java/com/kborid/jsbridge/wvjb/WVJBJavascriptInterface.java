package com.kborid.jsbridge.wvjb;

import android.util.Log;
import android.webkit.JavascriptInterface;

import java.util.HashMap;
import java.util.Map;

public class WVJBJavascriptInterface {

    private static final String TAG = WVJBJavascriptInterface.class.getSimpleName();

    private Map<String, WVJBJavascriptCallback> map = new HashMap<String, WVJBJavascriptCallback>();

    public void addCallback(String key, WVJBJavascriptCallback callback) {
        map.put(key, callback);
    }

    @JavascriptInterface
    public void onResultForScript(String key, String value) {
        Log.i(TAG, "WVJBJavascriptInterface::onResultForScript: " + value);
        WVJBJavascriptCallback callback = map.remove(key);
        if (callback != null)
            callback.onReceiveValue(value);
    }
}
