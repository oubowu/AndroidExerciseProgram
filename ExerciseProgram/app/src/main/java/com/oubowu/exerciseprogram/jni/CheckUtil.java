package com.oubowu.exerciseprogram.jni;

import com.socks.library.KLog;

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
        final long timeMillis = System.currentTimeMillis();
        System.loadLibrary("NdkTest");   //defaultConfig.ndk.moduleName
        KLog.e("加载JNI库使用时间为：" + (System.currentTimeMillis() - timeMillis) + " 毫秒。");
    }

    public native String getEncodePsw(String psw);

}
