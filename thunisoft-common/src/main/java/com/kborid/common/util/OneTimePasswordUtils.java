package com.kborid.common.util;

import android.text.TextUtils;

import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * OneTimePasswordUtils
 *
 * @author zhengjiayou
 * @version 1.0.0
 * @description 一次性密码工具类
 * @date 2018年8月23日 下午3:56:41
 */
public final class OneTimePasswordUtils {

    /**
     * 共享密钥
     */
    private static final String SECRET_KEY = "ga35sdia89dhqj6k3e0la";

    /**
     * 时间步长 单位:毫秒 作为口令变化的时间周期
     */
    private static final long STEP = 900000;

    /**
     * 转码位数 [1-8]
     */
    private static final int CODE_DIGITS = 8;

    /**
     * 16
     */
    private static final int INT_16 = 16;

    /**
     * 初始化时间
     */
    private static final long INITIAL_TIME = 0;

    /**
     * 柔性时间回溯
     */
    private static final long FLEXIBILIT_TIME = 5000;

    /**
     * 数子量级
     */
    private static final int[] DIGITS_POWER = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000};

    public static final String CRYPTO_512 = "HmacSHA512";

    public static final String CRYPTO_256 = "HmacSHA256";

    public static final String CRYPTO_1 = "HmacSHA1";

    private static final String CRYPTO_DEFAULT = CRYPTO_1;

    /**
     * OneTimePasswordUtils
     *
     * @description 构造函数
     * @author zhengjiayou
     * @date 2018年8月23日 下午3:57:25
     * @version 1.0.0
     */
    private OneTimePasswordUtils() {
    }

    /**
     * 生成一次性密码
     *
     * @param code 账户
     * @param pass 密码
     * @return String
     */
    public static String generatePassword(String code, String pass) {
        if (TextUtils.isEmpty(code) || TextUtils.isEmpty(pass)) {
            throw new RuntimeException("账户密码不许为空");
        }
        long now = System.currentTimeMillis();
        String time = Long.toHexString(timeFactor(now)).toUpperCase();
        return generatePassword(code + pass + SECRET_KEY, time, CRYPTO_DEFAULT);
    }

    /**
     * 刚性口令验证
     *
     * @param code 账户
     * @param pass 密码
     * @param totp 待验证的口令
     * @return boolean
     */
    public static boolean verifyPasswordRigidity(String code, String pass, String totp) {
        return generatePassword(code, pass).equals(totp);
    }

    /**
     * 柔性口令验证
     *
     * @param code 账户
     * @param pass 密码
     * @param totp 待验证的口令
     * @return boolean
     */
    public static boolean verifyPasswordFlexibility(String code, String pass, String totp) {
        long now = System.currentTimeMillis();
        String time = Long.toHexString(timeFactor(now)).toUpperCase();
        String tempTotp = generatePassword(code + pass + SECRET_KEY, time, CRYPTO_DEFAULT);
        if (tempTotp.equals(totp)) {
            return true;
        }
        String time2 = Long.toHexString(timeFactor(now - FLEXIBILIT_TIME)).toUpperCase();
        String tempTotp2 = generatePassword(code + pass + SECRET_KEY, time2, CRYPTO_DEFAULT);
        return tempTotp2.equals(totp);
    }

    /**
     * 获取动态因子
     *
     * @param targetTime 指定时间
     * @return long
     */
    private static long timeFactor(long targetTime) {
        return (targetTime - INITIAL_TIME) / STEP;
    }

    /**
     * 哈希加密
     *
     * @param crypto   加密算法
     * @param keyBytes 密钥数组
     * @param text     加密内容
     * @return byte[]
     */
    private static byte[] hmacSha(String crypto, byte[] keyBytes, byte[] text) {
        try {
            Mac hmac = Mac.getInstance(crypto);
            SecretKeySpec macKey = new SecretKeySpec(keyBytes, "AES");
            hmac.init(macKey);
            return hmac.doFinal(text);
        } catch (GeneralSecurityException gse) {
            throw new UndeclaredThrowableException(gse);
        }
    }

    /**
     * OneTimePasswordUtils
     *
     * @param hex hex
     * @return byte[]
     * @description hexStr2Bytes
     * @author zhengjiayou
     * @date 2018年8月23日 下午4:00:37
     * @version 1.0.0
     */
    private static byte[] hexStr2Bytes(String hex) {
        byte[] bArray = new BigInteger("10" + hex, 16).toByteArray();
        byte[] ret = new byte[bArray.length - 1];
        System.arraycopy(bArray, 1, ret, 0, ret.length);
        return ret;
    }

    /**
     * OneTimePasswordUtils
     *
     * @param key    key
     * @param time   time
     * @param crypto crypto
     * @return password
     * @description generatePassword
     * @author zhengjiayou
     * @date 2018年8月23日 下午4:03:51
     * @version 1.0.0
     */
    private static String generatePassword(String key, String time, String crypto) {
        StringBuilder timeBuilder = new StringBuilder(time);
        while (timeBuilder.length() < INT_16) {
            timeBuilder.insert(0, "0");
        }
        time = timeBuilder.toString();

        byte[] msg = hexStr2Bytes(time);
        byte[] k = key.getBytes(StandardCharsets.UTF_8);
        byte[] hash = hmacSha(crypto, k, msg);
        return truncate(hash);
    }

    /**
     * 截断函数
     *
     * @param target 20字节的字符串
     * @return String
     */
    private static String truncate(byte[] target) {
        StringBuilder result;
        int offset = target[target.length - 1] & 0xf;
        int binary = ((target[offset] & 0x7f) << 24) | ((target[offset + 1] & 0xff) << 16) | ((target[offset + 2] & 0xff) << 8)
                | (target[offset + 3] & 0xff);

        int otp = binary % DIGITS_POWER[CODE_DIGITS];
        result = new StringBuilder(Integer.toString(otp));
        while (result.length() < CODE_DIGITS) {
            result.insert(0, "0");
        }
        return result.toString();
    }
}
