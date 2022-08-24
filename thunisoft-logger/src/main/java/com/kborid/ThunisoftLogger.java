package com.kborid;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.kborid.logger.DiskFormatStrategy;
import com.kborid.logger.LoggerConfig;

/**
 * ThunisoftLogger
 *
 * @description: logger entrypoint
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 * @date: 2020/12/18
 */
public class ThunisoftLogger {

    private static Context mContext;

    public static Context getContext() {
        if (null == mContext) {
            throw new NullPointerException("context is null, need manual invoke init() in your application before use it!");
        }
        return mContext;
    }

    /**
     * 通过默认配置初始化
     *
     * @param context application
     */
    public static void initWithDefaultConfig(Context context) {
        initWithLoggerConfig(context, LoggerConfig.defaultLoggerConfig());
    }

    /**
     * 初始化logger配置
     *
     * @param config 配置
     */
    public static void initWithLoggerConfig(Context context, final LoggerConfig config) {
        checkContext(context);
        if (null == config) {
            throw new NullPointerException("LoggerConfig implementation must not be NULL");
        }
        // logger打印策略
        FormatStrategy mFormatStrategy = PrettyFormatStrategy.newBuilder()
                // (Optional) Whether to show thread info or not. Default true
                .showThreadInfo(true)
                // (Optional) How many method line to show. Default 2
                .methodCount(2)
                // (Optional) Hides internal method calls up to offset. Default 5
                .methodOffset(0)
                .tag(config.getTag())
                .build();

        // android log设置
        Logger.addLogAdapter(new AndroidLogAdapter(mFormatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return config.isDebug();
            }
        });

        // logger存储文件格式策略
        Logger.addLogAdapter(new DiskLogAdapter(DiskFormatStrategy.newBuilder().build(getContext())));
    }

    /**
     * 检查context是否是application
     *
     * @param context application
     */
    private static void checkContext(Context context) {
        if (!(context instanceof Application)) {
            throw new IllegalArgumentException("must be use application context init it");
        }
        mContext = context;
    }
}
