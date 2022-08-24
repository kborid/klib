package com.kborid.jsbridge.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import com.kborid.jsbridge.IWVJBWebViewAction;

public class NmWebView extends WebView implements IWVJBWebViewAction {

    public NmWebView(Context context) {
        super(context);
    }

    public NmWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NmWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public <T> void evaluateJavascriptt(String script, ValueCallback<T> valueCallback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            super.evaluateJavascript(script, (android.webkit.ValueCallback<String>) valueCallback);
        }
    }

    @Override
    public void postt(Runnable runnable) {
        super.post(runnable);
    }

    @Override
    public void loadUrll(String url) {
        super.loadUrl(url);
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void addJavascriptInterfacee(Object obj, String interfaceName) {
        super.addJavascriptInterface(obj, interfaceName);
    }
}
