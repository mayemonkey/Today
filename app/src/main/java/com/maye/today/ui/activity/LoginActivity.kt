package com.maye.today.ui.activity

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.textfield.TextInputLayout
import com.maye.today.expand.safeClick
import com.maye.today.login.LoginPresenter
import com.maye.today.login.LoginPresenterImpl
import com.maye.today.login.LoginView
import com.maye.today.today.R
import com.maye.today.util.InputUtil
import com.maye.today.util.Md5Util
import com.nineoldandroids.view.ViewHelper
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 登录Activity
 */
class LoginActivity : Activity(), LoginView, View.OnTouchListener, View.OnClickListener {
    private var loginPresenter: LoginPresenter? = null
    private var til_login_username: TextInputLayout? = null
    private var til_login_password: TextInputLayout? = null
    private var progressDialog: MaterialDialog? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initComponent()
    }

    override fun onResume() {
        super.onResume()
        til_login_username?.editText?.clearFocus()
        til_login_username?.error = null
        til_login_password?.editText?.clearFocus()
        til_login_password?.error = null
        setCustomerAnimation(iv_circle_inner, iv_circle_middle, iv_circle_outside)
    }

    /**
     * 初始化控件
     */
    private fun initComponent() {
        til_login_username = findViewById<View>(R.id.til_login_username) as TextInputLayout
        til_login_username?.editText?.setOnTouchListener(this)
        til_login_password = findViewById<View>(R.id.til_login_password) as TextInputLayout
        til_login_password?.editText?.setOnTouchListener(this)
        progressDialog = MaterialDialog.Builder(this).title("登录中").content("正在验证用户名及密码").progress(true, 0).build()
        val tv_login = findViewById<View>(R.id.tv_login) as TextView
        val tv_login_register = findViewById<View>(R.id.tv_login_register) as TextView
        tv_login_register.setOnClickListener(this)
        loginPresenter = LoginPresenterImpl(this)

        //点击登录——防止多触
        tv_login.safeClick {
            //获取用户输入
            if (InputUtil.checkTextInputLayout(til_login_username) && InputUtil.checkTextInputLayout(til_login_password)) {
                val username = til_login_username?.editText?.text.toString()
                val password = til_login_password?.editText?.text.toString()

                //执行登录
                loginPresenter?.loginCheck(username, Md5Util.textToMd5(password))
            }
        }
    }

    /**
     * 设置自定义动画，使Sign周边环圈做位移动画
     */
    private fun setCustomerAnimation(inner: ImageView, middle: ImageView, outside: ImageView) {
        val valueAnimator = ValueAnimator.ofFloat(0f, 1.5f).setDuration(3000)
        valueAnimator.repeatMode = ValueAnimator.RESTART
        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator.addUpdateListener { animation ->
            val value1 = animation.animatedValue as Float
            val value2 = value1 + 0.2f
            val value3 = value1 + 0.4f
            val alpha1 = 2f * (1 - value1) + 1
            val alpha2 = 2f * (1 - value2) + 1
            val alpha3 = 2f * (1 - value3) + 1
            ViewHelper.setScaleX(inner, value1)
            ViewHelper.setScaleY(inner, value1)
            ViewHelper.setAlpha(inner, alpha1)
            ViewHelper.setScaleX(middle, value2)
            ViewHelper.setScaleY(middle, value2)
            ViewHelper.setAlpha(middle, alpha2)
            ViewHelper.setScaleX(outside, value3)
            ViewHelper.setScaleY(outside, value3)
            ViewHelper.setAlpha(outside, alpha3)
        }
        valueAnimator.start()
    }

    override fun inputError(index: String, error: String) {
        when (index) {
            "USERNAME" -> til_login_username?.error = error
            "PASSWORD" -> til_login_password?.error = error
        }
    }

    override fun showProgress(visible: Boolean) {
        if (visible) {
            progressDialog?.show()
        } else {
            progressDialog?.dismiss()
        }
    }

    override fun startActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (view.id) {
            R.id.et_login_username -> til_login_username?.error = null
            R.id.et_login_password -> til_login_password?.error = null
        }
        return false
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.tv_login_register -> startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}