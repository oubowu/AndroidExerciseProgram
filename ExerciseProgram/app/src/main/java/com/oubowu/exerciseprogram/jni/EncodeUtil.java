package com.oubowu.exerciseprogram.jni;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ClassName：EncodeUtil
 * Author：shenshaoqin
 * Fuction：加密工具类,初始提供32位md5加密
 * CreateDate：2015/7/31 10:44
 * UpdateAuthor：
 * UpdateDate：
 */
public class EncodeUtil {
    // java语言md5加密
    public static String getMD5(String info)
    {
        try
        {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuilder strBuf = new StringBuilder();
            for (byte anEncryption : encryption) {
                if (Integer.toHexString(0xff & anEncryption).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & anEncryption));
                } else {
                    strBuf.append(Integer.toHexString(0xff & anEncryption));
                }
            }

            return strBuf.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            return "";
        }
        catch (UnsupportedEncodingException e)
        {
            return "";
        }
    }

    /**后端所用加密方式
     * @return
     */
    public static String getEncoderPass(String pass){
        return get32MD5(getSha1(pass)+"oubowu");
    }
    /**
     * 32位小写md5加密
     *
     * @param str
     * @return
     */
    public static String get32MD5(String str) {
        StringBuilder builder = new StringBuilder();
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] result = md5.digest(str.getBytes());
            for (byte b : result) {
                int a = b & 0xff;
                String temp = Integer.toHexString(a);
                if (temp.length() == 1)
                    builder.append("0");
                builder.append(temp);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * sha-1加密
     *
     * @param str
     * @return
     */
    public static String getSha1(String str) {
        StringBuilder builder = new StringBuilder();
        try {
            MessageDigest shaDig = MessageDigest.getInstance("SHA-1");
            shaDig.update(str.getBytes());
            byte messageDigest[] = shaDig.digest();
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() == 1) {
                    builder.append(0);
                }
                builder.append(shaHex);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

}
