package com.kborid.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.orhanobut.logger.Logger;

import org.apache.commons.lang3.StringUtils;

/**
 * @description: 系统信息工具类
 * @date: 2019/8/1
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 */
public class SystemInfoUtils {

    /**
     * 获取设备唯一ID
     *
     * @param context
     * @return String
     */
    @SuppressLint({"MissingPermission", "HardwareIds", "NewApi"})
    public static String getDeviceId(Context context) {
        String deviceId = null;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
            if (StringUtils.isEmpty(deviceId)) {
                deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        } catch (SecurityException e) {
            Logger.e(e, "异常: no permission to get deviceId");
        }

        return deviceId;
    }

    /**
     * 获取imei号码
     *
     * @param context
     * @return String
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getIMEI(Context context) {
        String imei = null;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                imei = tm.getImei();
            } else {
                imei = tm.getDeviceId();
            }
            String imsi = tm.getSubscriberId();
            System.out.println("imsi:" + imsi);
        } catch (SecurityException e) {
            Logger.e(e, "异常: no permission to get imei");
        }
        return imei;
    }
}
