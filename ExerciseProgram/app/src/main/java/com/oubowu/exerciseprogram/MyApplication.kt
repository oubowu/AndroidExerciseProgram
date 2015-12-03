package com.oubowu.exerciseprogram

import android.app.Application
import android.content.Context
import com.socks.library.KLog
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import java.util.concurrent.TimeUnit

/**
 * 类名： MyApplication
 * 作者: oubowu
 * 时间： 2015/12/2 10:52
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
class MyApplication : Application() {

    fun getRefWatcher(context: Context): RefWatcher {
        val application = context.applicationContext as MyApplication
        return application.getRefWatcher()
    }

    fun getRefWatcher(): RefWatcher {
        return refWatcher!!
    }

    private val refWatcher: RefWatcher? = null

    override fun onCreate() {
        super.onCreate()
        LeakCanary.install(this)
        KLog.init(true)
        KLog.e("LeakCanary.install(this)")

    }

}