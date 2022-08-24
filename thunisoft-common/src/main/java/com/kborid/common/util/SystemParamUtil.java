package com.kborid.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.orhanobut.logger.Logger;

/**
 * @description: MetaData获取工具类
 * @date: 2019/8/1
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 */
public final class SystemParamUtil {

    private static final String TAG = SystemParamUtil.class.getSimpleName();
    private static final String KEY_ENV = "env";

    private static String paramEnv;

    public static void init(Context context) {
        paramEnv = getMetaData(context, KEY_ENV);
        Logger.t(TAG).d("paramEnv: " + paramEnv);
    }

    public static String getParamEnv() {
        return paramEnv;
    }

    public static String getMetaData(Context context, String key) {
        String value = "";
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != appInfo && null != appInfo.metaData) {
                value = appInfo.metaData.getString(key);
            }
        } catch (Exception e) {
            Log.e(TAG, "获取MetaData失败", e);
        }
        return value;
    }
}
