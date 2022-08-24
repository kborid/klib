package com.kborid.common.util;

import android.util.Base64;
import android.util.Log;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;


public class EncryptUtils {

    /**
     * MD5加密
     * @param data 待加密字符串
     * @return
     */
    public static String getMD5(String data) {
        return getMD5(data.getBytes());
    }

    /**
     * MD5加密
     * @param data 待加密的字节数组
     * @return MD5密文
     */
    public static String getMD5(byte[] data) {
        return bytes2Hex(encryptMD5(data));
    }

    public static byte[] encryptMD5(byte[] data) {
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data);
            return md.digest();
        }catch(NoSuchAlgorithmException e) {
            Log.e("EncryptUtils", "MD5加密失败", e);
        }
        return new byte[0];
    }

    /**
     * 一个byte转2个hex字符
     * @param src
     * @return
     */
    public static String bytes2Hex(byte[] src) {
        char[] res = new char[src.length * 2];
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        for(int i=0,j=0; i<src.length; i++) {
            res[j++] = hexDigits[src[i] >>> 4 & 0x0f];     //取高位 <= 15
            res[j++] = hexDigits[src[i] & 0x0f];           //取低位 <= 15
        }
        return new String(res);
    }


    /**
     * 根据模数、指数生成公钥
     * @param modulus
     * @param publicExponent
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKey(byte[] modulus, byte[] publicExponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        BigInteger bigIntModulus = new BigInteger(modulus);
        BigInteger bigIntPrivateExponent = new BigInteger(publicExponent);
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }


    /**
     * 用公钥加密 <br>
     * 每次加密的字节数，不能超过密钥的长度值减去11
     *
     * @param data 需加密数据的byte数据
     * @param modulus 公钥指数字节
     * @param publicExponent 公钥模数字节
     * @return 加密后的string数据
     */
    public static String encryptDataString(String data, String modulus, String publicExponent)
    {
        try
        {
            byte[] modulus_ = Base64.decode(modulus, Base64.NO_WRAP);
            byte[] publicExponent_ = Base64.decode(publicExponent, Base64.NO_WRAP);
            PublicKey publicKey = getPublicKey(modulus_, publicExponent_);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            // 编码前设定编码方式及密钥
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 传入编码数据并返回编码结果
            return base64Encode2String(cipher.doFinal(data.getBytes()));

        } catch (Exception e){
            Log.e("EncryptUtils", "RSA加密失败", e);
            return null;
        }
    }



    /**
     * Base64编码
     *
     * @param input 要编码的字节数组
     * @return Base64编码后的字符串
     */
    public static String base64Encode2String(byte[] input) {
        return Base64.encodeToString(input, Base64.NO_WRAP);
    }


}
