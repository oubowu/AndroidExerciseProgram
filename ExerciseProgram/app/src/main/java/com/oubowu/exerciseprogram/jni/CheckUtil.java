package com.oubowu.exerciseprogram.jni;

/**
 * 类名： CheckUtil
 * 作者: oubowu
 * 时间： 2016/1/27 16:23
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class CheckUtil {

    static {
        System.loadLibrary("NdkTest");   //defaultConfig.ndk.moduleName
    }

    public native String getEncodePsw(String psw);

}
