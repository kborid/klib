package com.kborid.logger;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @Author: kborid
 * @Date: 2019/5/6
 * @Version: 1.0.0
 * @Description: LoggerUtils
 * @Copyright kborid@aliyun.com
 */
public class LoggerUtils {
    /**
     * 单个logger 文件最大值
     */
    public static final int LOGGER_FILE_MAX_SIZE = 500 * 1024;

    /**
     * logger 日志 tag 前缀
     */
    public static final String TAG_PREFIX = "THUNISOFT_LOGGER";

    /**
     * logger文件名前缀
     */
    public static final String FILE_NAME_PREFIX = "logs";

    /**
     * sdcard 存在  存储到sdcard上面
     * sdcard 不存在：存储在本地缓存目录上面
     *
     * @param
     * @return
     */
    public static String getBaseSaveDirPath(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "thunisoft"
                    + File.separator + context.getPackageName()
                    + File.separator + "logger";
        } else {
            return context.getFilesDir().getAbsolutePath()
                    + File.separator + "logger";
        }
    }
}
