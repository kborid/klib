package com.kborid.ui.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * @description: 屏幕参数工具类
 * @date: 2019/8/1
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.1.0
 */
public class ScreenUtils {

    public static int mScreenWidth;
    public static int mScreenHeight;
    public static float mDensity;
    public static float mScaleDensity;
    public static int mStateBarHeight;

    public static void init(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (null != wm) {
            wm.getDefaultDisplay().getMetrics(dm);
            mScreenWidth = dm.widthPixels;
            mScreenHeight = dm.heightPixels;
            mDensity = dm.density;
            mScaleDensity = dm.scaledDensity;
        }

        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            mStateBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            Log.e("ScreenUtils", "状态栏高度获取失败", e);
        }
    }

    /**
     * 根据手机分辨率从DP转成PX
     *
     * @param dpValue
     * @return int
     */
    public static int dp2px(float dpValue) {
        float scale = mDensity;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return int
     */
    public static int sp2px(float spValue) {
        final float fontScale = mScaleDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率PX(像素)转成DP
     *
     * @param pxValue
     * @return int
     */
    public static int px2dp(float pxValue) {
        float scale = mDensity;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return int
     */

    public static int px2sp(float pxValue) {
        final float fontScale = mScaleDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
