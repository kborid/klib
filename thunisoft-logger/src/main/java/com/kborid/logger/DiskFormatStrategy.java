package com.kborid.logger;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogStrategy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.orhanobut.logger.Logger.*;

/**
 * @Author: kborid
 * @Date: 2019/5/6
 * @Version: 1.0.0
 * @Description: 日志写入磁盘策略
 * @Copyright kborid@aliyun.com
 */
public class DiskFormatStrategy implements FormatStrategy {

    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String NEW_LINE_REPLACEMENT = " <br> ";
    private static final String SEPARATOR = ",";

    private final Date mDate;
    private final SimpleDateFormat mDateFormat;
    private final LogStrategy mLogStrategy;
    private final String mTag;

    private DiskFormatStrategy(Builder builder) {
        mDate = builder.nDate;
        mDateFormat = builder.nDateFormat;
        mLogStrategy = builder.nLogStrategy;
        mTag = builder.nTag;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public void log(int priority, String onceOnlyTag, String message) {
        String tag = formatTag(onceOnlyTag);

        mDate.setTime(System.currentTimeMillis());

        StringBuilder builder = new StringBuilder();

        // machine-readable date/time
        builder.append(Long.toString(mDate.getTime()));

        // human-readable date/time
        builder.append(SEPARATOR);
        builder.append(mDateFormat.format(mDate));

        // level
        builder.append(SEPARATOR);
        builder.append(logLevel(priority));

        // tag
        builder.append(SEPARATOR);
        builder.append(tag);

        // message
        if (message.contains(NEW_LINE)) {
            // a new line would break the CSV format, so we replace it here
            message = message.replaceAll(NEW_LINE, NEW_LINE_REPLACEMENT);
        }
        builder.append(SEPARATOR);
        builder.append(message);

        // new line
        builder.append(NEW_LINE);

        mLogStrategy.log(priority, tag, builder.toString());
    }

    private String logLevel(int value) {
        switch (value) {
            case VERBOSE: {
                return "VERBOSE";
            }
            case DEBUG: {
                return "DEBUG";
            }
            case INFO: {
                return "INFO";
            }
            case WARN: {
                return "WARN";
            }
            case ERROR: {
                return "ERROR";
            }
            case ASSERT: {
                return "ASSERT";
            }
            default: {
                return "UNKNOWN";
            }
        }
    }

    /**
     * 格式化tag
     *
     * @param tag
     * @return
     */
    private String formatTag(String tag) {
        if (!TextUtils.isEmpty(tag) && !TextUtils.equals(this.mTag, tag)) {
            return this.mTag + "-" + tag;
        }
        return this.mTag;
    }

    public static final class Builder {
        private static final int MAX_BYTES = LoggerUtils.LOGGER_FILE_MAX_SIZE; // 500K averages to a 4000 lines per file

        Date nDate;
        SimpleDateFormat nDateFormat;
        LogStrategy nLogStrategy;
        String nTag = LoggerUtils.TAG_PREFIX;

        private Builder() {
        }

        public Builder date(Date val) {
            nDate = val;
            return this;
        }

        public Builder dateFormat(SimpleDateFormat val) {
            nDateFormat = val;
            return this;
        }

        public Builder logStrategy(LogStrategy val) {
            nLogStrategy = val;
            return this;
        }

        public Builder tag(String tag) {
            this.nTag = tag;
            return this;
        }

        public DiskFormatStrategy build(Context context) {
            if (nDate == null) {
                nDate = new Date();
            }
            if (nDateFormat == null) {
                nDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.CHINA);
            }
            if (nLogStrategy == null) {
                String path = LoggerUtils.getBaseSaveDirPath(context);
                HandlerThread handlerThread = new HandlerThread("ThunisoftAndroidLogger." + path);
                handlerThread.start();
                Handler handler = new DiskLogStrategy.WriteHandler(handlerThread.getLooper(), path, MAX_BYTES);
                nLogStrategy = new DiskLogStrategy(handler);
            }
            return new DiskFormatStrategy(this);
        }
    }
}
