package com.kborid.common.tool;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Process;

import java.util.LinkedList;
import java.util.List;


/**
 * @description: Activity栈管理类：包括退出管理
 * @date: 2019/7/16
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 */
public class ActivityTack {

    // Activity集合
    private List<Activity> mList = new LinkedList<Activity>();
    private static ActivityTack instance = new ActivityTack();

    private ActivityTack() {
    }

    /**
     * 单例获取Activity管理实例
     *
     * @return
     */
    public static ActivityTack getInstance() {
        return instance;
    }

    /**
     * 添加组件
     *
     * @param activity
     */
    public final void addActivity(Activity activity) {
        mList.add(activity);
    }

    /**
     * 去除组件
     *
     * @param activity
     */
    public final void removeActivity(Activity activity) {
        mList.remove(activity);
    }

    /*
     * 获取当前Activity*/
    public final Activity getCurrentActivity() {
        return mList.get(mList.size() - 1);
    }


    /**
     * 应用退出
     */
    public final void exitApp(Context context) {
        clearNotification(context);
        for (Activity activity : mList) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0);
        Process.killProcess(Process.myPid());
    }

    public final void clearNotification(Context context) {
        NotificationManager manager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
    }
}
