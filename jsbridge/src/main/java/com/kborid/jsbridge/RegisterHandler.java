package com.kborid.jsbridge;

import android.content.Context;

public abstract class RegisterHandler {

    /**
     * 初始化，注册处理程序
     */
    public abstract void initHandle(IWVJBClient wvjbClient, Context context) throws Exception;
}
