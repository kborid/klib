package com.kborid.common;

import android.app.Application;
import android.content.Context;

import com.kborid.common.tool.CrashHandler;
import com.kborid.common.util.SystemParamUtil;

/**
 * ThunisoftCommon
 *
 * @description: common entrypoint
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 * @date: 2020/12/18
 */
public class ThunisoftCommon {

    private static Context mContext;

    /**
     * library初始化方法，invoke in application onCreate
     *
     * @param context application
     */
    @Deprecated
    public static void init(Context context) {
        if (!(context instanceof Application)) {
            throw new IllegalArgumentException("must be use application context init it");
        }
        mContext = context;

        SystemParamUtil.init(context);
        CrashHandler.getInstance().init();
    }


    static void setContext(Context context) {
        mContext = context;
        SystemParamUtil.init(context);
        CrashHandler.getInstance().init();
    }

    public static Context getContext() {
        if (null == mContext) {
            throw new NullPointerException("context is null, need manual invoke init() in your application before use it!");
        }
        return mContext;
    }
}
