package com.kborid.common.network;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.orhanobut.logger.Logger;
import com.kborid.common.BuildConfig;
import com.kborid.common.ThunisoftCommon;
import com.kborid.common.network.interceptor.HeaderInterceptor;
import com.kborid.common.network.interceptor.LoggerInterceptor;

import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @description: okHttpClient工厂类
 * @date: 2019/7/2
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 */
public class OkHttpClientFactory {

    // HTTPS服务器端证书的公钥（用于验证服务器的合法性）
    private static final String HTTPS_SERVER_CERT = "server.bks";
    private static final String HTTPS_SERVER_CERT_PWD = "cc4client";

    //请求超时时间
    private static final int TIMEOUT = 10;

    // HTTPS证书
    private static KeyStore mHttpsKeyStore = null;
    private static TrustManager mTrustManager = null;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static OkHttpClient newOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
        // 失败重连
        builder.retryOnConnectionFailure(true);

        //设置Https证书
//        builder.sslSocketFactory(newSslSocketFactory(getX509TrustManager()), getX509TrustManager());
//        builder.hostnameVerifier((hostname, session) -> StringUtils.equals("https", "https"));

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new LoggerInterceptor());
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            builder.addInterceptor(httpLoggingInterceptor);
            // 网络请求监测
//            builder.addNetworkInterceptor(new StethoInterceptor());
        }

        builder.addInterceptor(new HeaderInterceptor());
        return builder.build();
    }

    /**
     * 从assets目录读取https证书到KeyStore中
     *
     * @return KeyStore
     */
    private static KeyStore getHttpsKeyStore() {
        if (mHttpsKeyStore == null) {
            synchronized (OkHttpClientFactory.class) {
                if (mHttpsKeyStore == null) {
                    try {
                        char[] password = HTTPS_SERVER_CERT_PWD.toCharArray();
                        InputStream isCert = ThunisoftCommon.getContext().getAssets().open(HTTPS_SERVER_CERT);
                        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                        keyStore.load(isCert, password);
                        mHttpsKeyStore = keyStore;
                    } catch (Exception e) {
                        Logger.e(e, "读取HTTPS证书出错");
                    }
                }
            }
        }
        return mHttpsKeyStore;
    }

    /**
     * 构建X509TrustManager
     *
     * @return X509TrustManager
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static X509TrustManager getX509TrustManager() {
        if (null == mTrustManager) {
            try {
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                        TrustManagerFactory.getDefaultAlgorithm());
                // 默认用空证书初始化
//            trustManagerFactory.init((KeyStore) null);
                trustManagerFactory.init(getHttpsKeyStore());
                TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
                if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                    throw new IllegalStateException("Unexpected default trust managers:"
                            + Arrays.toString(trustManagers));
                }
                mTrustManager = trustManagers[0];
            } catch (GeneralSecurityException e) {
                throw new AssertionError("No System TLS", e); // The system has no TLS. Just give up.
            }
        }
        return (X509TrustManager) mTrustManager;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static SSLSocketFactory newSslSocketFactory(X509TrustManager trustManager) {
        try {
            SSLContext sslContext = Platform.get().getSSLContext();
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            return sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new AssertionError("No System TLS", e); // The system has no TLS. Just give up.
        }
    }
}
