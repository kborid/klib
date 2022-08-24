package com.kborid.jsbridge;

import android.webkit.ValueCallback;

public interface IWVJBWebViewAction {
    <T> void evaluateJavascriptt(String script, ValueCallback<T> valueCallback);
    void postt(Runnable runnable);
    void loadUrll(String url);
    void addJavascriptInterfacee(Object obj, String interfaceName);
}
