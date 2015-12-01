package com.oubowu.exerciseprogram.kotlin

import android.view.View
import android.widget.ProgressBar
import butterknife.Bind
import com.oubowu.exerciseprogram.BaseActivity
import com.oubowu.exerciseprogram.R
import com.socks.library.KLog

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : BaseActivity() {

    var progressBar: ProgressBar? = null

    override fun provideLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        progressBar = getViewById(R.id.login_progress)
        progressBar?.visibility = View.VISIBLE
    }

    override fun initData() {
    }

}

