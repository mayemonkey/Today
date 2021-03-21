package com.maye.today.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import com.maye.today.domain.User
import com.maye.today.expand.safeClick
import com.maye.today.register.RegisterPresenter
import com.maye.today.register.RegisterPresenterImpl
import com.maye.today.register.RegisterView
import com.maye.today.today.R
import com.maye.today.util.InputUtil
import com.maye.today.util.Md5Util
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File

/**
 * RegisterActivity
 */
class RegisterActivity : Activity(), RegisterView, View.OnClickListener {
    private var registerPresenter: RegisterPresenter? = null
    private var til_reg_username: TextInputLayout? = null
    private var til_reg_password: TextInputLayout? = null
    private var til_reg_email: TextInputLayout? = null
    private var progressDialog: MaterialDialog? = null
    private var civ_reg_avatar: CircleImageView? = null
    private var til_reg_nickname: TextInputLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initComponent()
    }

    override fun onResume() {
        super.onResume()
        til_reg_username!!.editText!!.clearFocus()
        til_reg_username!!.error = null
        til_reg_password!!.editText!!.clearFocus()
        til_reg_password!!.error = null
        til_reg_email!!.editText!!.clearFocus()
        til_reg_email!!.error = null
        til_reg_nickname!!.editText!!.clearFocus()
        til_reg_nickname!!.error = null
    }

    /**
     * 初始化控件
     */
    private fun initComponent() {
        val iv_cancel = findViewById<View>(R.id.iv_reg_cancel) as ImageView
        iv_cancel.setOnClickListener(this)
        civ_reg_avatar = findViewById<View>(R.id.civ_reg_avatar) as CircleImageView
        civ_reg_avatar!!.setOnClickListener(this)
        til_reg_username = findViewById<View>(R.id.til_reg_username) as TextInputLayout
        til_reg_password = findViewById<View>(R.id.til_reg_password) as TextInputLayout
        til_reg_nickname = findViewById<View>(R.id.til_reg_nickname) as TextInputLayout
        til_reg_email = findViewById<View>(R.id.til_reg_email) as TextInputLayout
        val bt_reg_commit = findViewById<View>(R.id.bt_reg_commit) as Button

        //点击注册——防止多触
        bt_reg_commit.safeClick {
            if (InputUtil.checkTextInputLayout(til_reg_username) && InputUtil.checkTextInputLayout(til_reg_password) &&
                    InputUtil.checkTextInputLayout(til_reg_nickname) && InputUtil.checkTextInputLayout(til_reg_email)) {
                val username = til_reg_username!!.editText!!.text.toString()
                val password = til_reg_password!!.editText!!.text.toString()
                val nickname = til_reg_nickname!!.editText!!.text.toString()
                val email = til_reg_email!!.editText!!.text.toString()
                val avatar = civ_reg_avatar!!.tag as String
                val user = User(username, Md5Util.textToMd5(password), nickname, email, avatar)
                registerPresenter!!.checkRegister(user)
            }
        }

        progressDialog = MaterialDialog.Builder(this).title("注册中").content("正在提交用户信息...").progress(true, 0).build()
        registerPresenter = RegisterPresenterImpl(this)
    }

    override fun inputError(index: String, error: String) {
        when (index) {
            "USERNAME" -> til_reg_username!!.error = error
            "PASSWORD" -> til_reg_password!!.error = error
            "EMAIL" -> til_reg_email!!.error = error
        }
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            progressDialog!!.show()
        } else {
            progressDialog!!.dismiss()
        }
    }

    override fun startActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_reg_cancel -> finish()
            R.id.civ_reg_avatar -> startActivityForResult(Intent(this, AlbumActivity::class.java), AVATAR_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == AVATAR_REQUEST) {
            if (resultCode == 0) {
                val path = data.getStringExtra("path")
                if (!TextUtils.isEmpty(path)) {
                    civ_reg_avatar!!.tag = path
                    Glide.with(this).load(File(path)).centerCrop().crossFade().into(civ_reg_avatar)
                }
            }
        }
    }

    companion object {
        private const val AVATAR_REQUEST = 0
    }
}