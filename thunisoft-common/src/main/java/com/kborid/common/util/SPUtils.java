package com.kborid.common.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.kborid.common.ThunisoftCommon;

/**
 * @description: shared preference
 * @date: 2019/7/10
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 */
public class SPUtils {

    private final static String CONFIG_FILE_NAME = "thunisoft_preference_config";

    private static SharedPreferences.Editor getEditor() {
        return ThunisoftCommon.getContext().getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).edit();
    }

    private static SharedPreferences getSettings() {
        return ThunisoftCommon.getContext().getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static String getString(String key, String def) {
        return getSettings().getString(key, def);
    }

    public static boolean setString(String key, String value) {
        return getEditor().putString(key, value).commit();
    }

    public static int getInt(String key, int def) {
        return getSettings().getInt(key, def);
    }

    public static boolean setInt(String key, int def) {
        return getEditor().putInt(key, def).commit();
    }

    public static boolean setLong(String key, long def) {
        return getEditor().putLong(key, def).commit();
    }

    public static long getLong(String key, long def) {
        return getSettings().getLong(key, def);
    }

    public static boolean setFloat(String key, Float def) {
        return getEditor().putFloat(key, def).commit();
    }

    public static Float getFloat(String key, Float def) {
        return getSettings().getFloat(key, def);
    }

    public static boolean getBoolean(String key, boolean def) {
        return getSettings().getBoolean(key, def);
    }

    public static boolean setBoolean(String key, boolean value) {
        return getEditor().putBoolean(key, value).commit();
    }

    /**
     * 删除某个key-value
     *
     * @param key
     * @return
     */
    public static boolean removeByKey(String key) {
        return getEditor().remove(key).commit();
    }

    /**
     * 删除所有
     */
    public static void removeAll() {
        getEditor().clear().commit();
    }

}