package com.kborid.jsbridge;

public interface WVJBPageListener {
    void onPageStart(String url);
    void onPageFinish(String url);
    boolean shouldOverrideUrlLoading(String url);
}
