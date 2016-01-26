package com.oubowu.exerciseprogram.jni;

/**
 * 类名：
 * 作者: oubowu
 * 时间： 2016/1/26 17:09
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class NdkJniUtils {

    static {
        System.loadLibrary("NdkTest");   //defaultConfig.ndk.moduleName
    }

    public native String getCLanguageString();

}
