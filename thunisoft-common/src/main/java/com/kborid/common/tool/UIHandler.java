package com.kborid.common.tool;

import android.os.Handler;
import android.os.Looper;

/**
 * @description: 主线程队列Handler
 * @date: 2019/6/27
 * @time: 10:41
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 */
public class UIHandler {
    private static final Handler sHandler;

    static {
        sHandler = new Handler(Looper.getMainLooper());
    }

    public static Handler getHandler() {
        return sHandler;
    }

    public static boolean post(Runnable r) {
        return sHandler.post(r);
    }

    public static boolean postDelayed(Runnable r, long delayMillis) {
        return sHandler.postDelayed(r, delayMillis);
    }

    public static void removeCallbacks(Runnable r) {
        sHandler.removeCallbacks(r);
    }
}
