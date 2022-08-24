package com.kborid.logger;

public class LoggerConfig {
    //默认TAG
    static final String TAG_DEFAULT = "TAG_KBORID_DEFAULT";

    private String mTag;
    private boolean mIsDebug;

    /**
     * 设置logger的打印Tag
     *
     * @return 主tag
     */
    public String getTag() {
        return mTag;
    }

    /**
     * 设置debug or release
     *
     * @return 是否debug模式
     */
    public boolean isDebug() {
        return mIsDebug;
    }

    private LoggerConfig(String tag, boolean isDebug) {
        this.mTag = tag;
        this.mIsDebug = isDebug;
    }

    public static LoggerConfig createLoggerConfig(String tag, boolean isDebug) {
        return new LoggerConfig(tag, isDebug);
    }

    public static LoggerConfig defaultLoggerConfig() {
        return new LoggerConfig(TAG_DEFAULT, false);
    }
}
