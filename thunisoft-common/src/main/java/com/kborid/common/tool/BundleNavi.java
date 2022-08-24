package com.kborid.common.tool;


import android.app.Activity;
import android.os.Bundle;

import java.io.Serializable;

/**
 * @description: 单例，原生Bundle进化版，数据短暂持久化，传递多个activity，直到值被取过后，删除该值
 * @date: 2019/7/17
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 */
public class BundleNavi {
    private BundleUnit curUnit;
    private BundleUnit preUnit;

    private static final BundleNavi instance = new BundleNavi();

    private BundleNavi() {
        curUnit = BundleUnit.defaultBundleUnit;
        preUnit = BundleUnit.defaultBundleUnit;
    }

    public static BundleNavi getInstance() {
        return instance;
    }

    /*package*/ void updateBundle(Activity activity) {
        if (curUnit.hashcode == activity.hashCode()) {
            return;
        }
        preUnit = curUnit;
        curUnit = BundleUnit.create(activity.getClass(), activity.hashCode());
    }

    public boolean containKey(String key) {
        return preUnit.bundle.containsKey(key);
    }

    public String getString(String key) {
        String temp = preUnit.bundle.getString(key);
        preUnit.bundle.remove(key);
        return temp;
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        int temp = preUnit.bundle.getInt(key, defaultValue);
        preUnit.bundle.remove(key);
        return temp;
    }

    public float getFloat(String key) {
        float temp = preUnit.bundle.getFloat(key);
        preUnit.bundle.remove(key);
        return temp;
    }

    public boolean getBoolean(String key) {
        boolean temp = preUnit.bundle.getBoolean(key);
        preUnit.bundle.remove(key);
        return temp;
    }

    public Object get(String key) {
        Object temp = preUnit.bundle.get(key);
        preUnit.bundle.remove(key);
        return temp;
    }

    public void putString(String key, String value) {
        curUnit.bundle.putString(key, value);
    }

    public void putInt(String key, int value) {
        curUnit.bundle.putInt(key, value);
    }

    public void putFloat(String key, float value) {
        curUnit.bundle.putFloat(key, value);
    }

    public void putBoolean(String key, boolean value) {
        curUnit.bundle.putBoolean(key, value);
    }

    public void put(String key, Serializable value) {
        curUnit.bundle.putSerializable(key, value);
    }

    public Class<? extends Activity> getPreviousActivityClass() {
        return preUnit.activityClass;
    }

    public Class<? extends Activity> getCurrentActivityClass() {
        return curUnit.activityClass;
    }

    private static class BundleUnit {
        private static final BundleUnit defaultBundleUnit = new BundleUnit(Activity.class, 0);

        private final Bundle bundle;
        private final Class<? extends Activity> activityClass;
        private int hashcode;

        private BundleUnit(Class<? extends Activity> activityClass, int hashcode) {
            this.activityClass = activityClass;
            this.hashcode = hashcode;
            bundle = new Bundle();
        }

        private static BundleUnit create(Class<? extends Activity> activityClass, int hashcode) {
            return new BundleUnit(activityClass, hashcode);
        }
    }
}
