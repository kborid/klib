package com.kborid.common.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import com.orhanobut.logger.Logger;
import com.kborid.common.ThunisoftCommon;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: crash信息收集工具类
 * @date: 2019/8/1
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 */
public class CrashInfoUtils {

    private static final String TAG = CrashInfoUtils.class.getSimpleName();

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @param infos
     */
    private static void saveCrashInfo2File(Throwable ex, Map<String, String> infos) {
        Date date = new Date();
        String time4Log = DateUtils.formatDate("======yyyy-MM-dd HH:mm:ss======", date);
        StringBuilder sb = new StringBuilder();
        String lineSeparator = System.getProperty("line.separator", "\n");
        sb.append(lineSeparator).append(lineSeparator).append(time4Log).append(lineSeparator).append(lineSeparator);
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();

        String result = writer.toString();
        sb.append(result);
        FileOutputStream outStream = null;
        try {
            String filePath = "";
            String fileTime = DateUtils.formatDate("yyyy-MM-dd", date);
            String fileName = "crash-" + fileTime + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                filePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + "thunisoft"
                        + File.separator + ThunisoftCommon.getContext().getPackageName()
                        + File.separator + "crash";
            } else {
                filePath = ThunisoftCommon.getContext().getFilesDir().getAbsolutePath()
                        + File.separator + "crash";
            }

            File dir = new File(filePath);
            if (!dir.exists()) {
                boolean ret = dir.mkdirs();
                if (ret) {
                    Logger.t(TAG).i("文件目录创建成功");
                }
            }
            Logger.t(TAG).i("Crash文件存放目录：" + dir.getAbsolutePath());
            outStream = new FileOutputStream(dir.getAbsolutePath() + File.separator + fileName, true);
            outStream.write(sb.toString().getBytes());
        } catch (Exception e) {
            Logger.t(TAG).e(e, "保存Crash信息到文件出错");
        } finally {
            CloseUtil.closeQuietly(outStream);
        }

    }

    /**
     * 收集设备参数信息
     */
    public static void collectDeviceInfo(Throwable throwable) {
        // 用来存储设备信息和异常信息
        Map<String, String> infos = new HashMap<String, String>();
        try {
            PackageManager pm = ThunisoftCommon.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ThunisoftCommon.getContext().getPackageName(), PackageManager.GET_ACTIVITIES);

            if (pi != null) {
                String versionName = StringUtils.defaultString(pi.versionName, "null");
                String versionCode = String.valueOf(pi.versionCode);
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Logger.t(TAG).e(e, "收集package信息发生错误");
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                Logger.t(TAG).e(e, "收集系统build信息发生错误");
            }
        }
        saveCrashInfo2File(throwable, infos);
    }
}
