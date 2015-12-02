package com.oubowu.exerciseprogram.kotlin

import android.app.Activity
import android.text.TextUtils
import android.view.View
import android.widget.*
import butterknife.Bind
import com.oubowu.exerciseprogram.BaseActivity
import com.oubowu.exerciseprogram.R
import com.socks.library.KLog
import kotlin.concurrent.thread

// 为指定类添加方法（拓展）
// 这是个非常实用的方法，像OC一样，kotlin也可以给某个类添加一些方法，比如代码中，我们给Activity类添加了一个toast方法，
// 这样所有的Activity子类都可以拥有了toast方法。相信所有做Java的朋友都遇到过Java不能多继承的问题，虽然这给Java开发带来了很大的好处，
// 但是在某些情况下不能多继承确实很麻烦，用kotlin的这个特性就能轻松解决这种问题了。
fun Activity.toast(msg: CharSequence) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : BaseActivity() {

    // 伴随对象
    private companion object {
        val TAG = LoginActivity::class.java.simpleName
    }

    var progressBar: ProgressBar? = null
    var email: AutoCompleteTextView? = null
    var password: EditText? = null
    var button: Button? = null

    override fun provideLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {

        KLog.e(LoginActivity.TAG)

        progressBar = getViewById(R.id.login_progress)
        email = getViewById(R.id.email)
        password = getViewById(R.id.password)
        button = getViewById(R.id.email_sign_in_button)

        button?.setOnClickListener { view: View ->

            progressBar?.visibility = View.VISIBLE

            val account: String? = email!!.getText().toString().trim()
            val pwd: String? = password!!.getText().toString().trim()

            thread {

                Thread.sleep(2000)

                runOnUiThread {
                    if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)) {
                        toast("email or password can't be null")
                    } else if (account.equals("ou") && pwd.equals("123")) {
                        toast("login success")
                    } else {
                        toast("email or passeord is incorrect, please try again")
                    }
                    progressBar?.visibility = View.GONE
                }
            }

        }

    }

    override fun initData() {
    }

}

