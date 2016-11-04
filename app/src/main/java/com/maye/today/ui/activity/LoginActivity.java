package com.maye.today.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jakewharton.rxbinding.view.RxView;
import com.maye.today.today.R;
import com.maye.today.login.LoginPresenter;
import com.maye.today.login.LoginPresenterImpl;
import com.maye.today.login.LoginView;
import com.maye.today.util.InputUtil;
import com.maye.today.util.Md5Util;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * 登录Activity
 */
public class LoginActivity extends Activity implements LoginView {

    private LoginPresenter loginPresenter;
    private TextInputLayout til_login_username;
    private TextInputLayout til_login_password;
    private MaterialDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponent();
    }

    /**
     * 初始化控件
     */
    private void initComponent() {
        til_login_username = (TextInputLayout) findViewById(R.id.til_login_username);
        til_login_password = (TextInputLayout) findViewById(R.id.til_login_password);

        TextView tv_login = (TextView) findViewById(R.id.tv_login);

        loginPresenter = new LoginPresenterImpl(this);

        //点击登录——防止多触
        RxView.clicks(tv_login).throttleWithTimeout(300, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                //获取用户输入
                if (InputUtil.checkTextInputLayout(til_login_username) && InputUtil.checkTextInputLayout(til_login_password)) {
                    String username = til_login_username.getEditText().getText().toString();
                    String password = til_login_password.getEditText().getText().toString();

                    //执行登录
                    loginPresenter.loginCheck(username, Md5Util.textToMd5(password));
                }
            }
        });

        progressDialog = new MaterialDialog.Builder(this).title("登录中").content("正在验证用户名及密码").build();
    }

    @Override
    public void setProgressVisible(boolean visible) {
        if (visible) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void startActivity() {
        startActivity(new Intent(this, HomeActivity.class));
    }
}
