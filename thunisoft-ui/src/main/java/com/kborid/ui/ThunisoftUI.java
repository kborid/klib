package com.kborid.ui;

import android.app.Application;
import android.content.Context;

import com.kborid.ui.util.ScreenUtils;

/**
 * ThunisoftUI
 *
 * @description: ui entrypoint
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 * @date: 2020/12/18
 */
public class ThunisoftUI {

    private static Context mContext;

    /**
     * 初始化
     *
     * @param context application
     */
    @Deprecated
    public static void init(Context context) {
        if (!(context instanceof Application)) {
            throw new IllegalArgumentException("must be use application context init it");
        }
        mContext = context;
        ScreenUtils.init(context);
    }

    static void setContext(Context context) {
        mContext = context;
        ScreenUtils.init(context);
    }

    public static Context getContext() {
        if (null == mContext) {
            throw new NullPointerException("context is null, need manual invoke init() in your application before use it!");
        }
        return mContext;
    }
}
