package com.kborid.common.network.interceptor;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @description: okhttp拦截类，添加header信息
 * @date: 2019/7/2
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 */
public class HeaderInterceptor implements Interceptor {

    private static final String DEFAULT_AGENT = "Mozilla/5.0 (Linux;Android "
            + android.os.Build.VERSION.RELEASE + "; " + android.os.Build.MODEL
            + ") AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/45.0.2454.95 Mobile Safari/537.36";

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Content-Type", "application/json")
                .addHeader("User-Agent", DEFAULT_AGENT);
        Request request = builder.build();
        return chain.proceed(request);
    }
}
