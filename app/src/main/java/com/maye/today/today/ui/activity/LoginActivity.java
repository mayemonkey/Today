package com.maye.today.today.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.maye.today.today.R;
import com.maye.today.today.login.LoginPresenter;
import com.maye.today.today.login.LoginPresenterImpl;
import com.maye.today.today.login.LoginView;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * 登录Activity
 */
public class LoginActivity extends Activity implements LoginView {

    private LoginPresenter loginPresenter;
    private MaterialEditText met_username;
    private MaterialEditText met_password;

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
        met_username = (MaterialEditText) findViewById(R.id.met_username);
        met_password = (MaterialEditText) findViewById(R.id.met_password);

        TextView tv_login = (TextView) findViewById(R.id.tv_login);
        RxView.clicks(tv_login).
                throttleWithTimeout(300, TimeUnit.MILLISECONDS).
                subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        //获取用户输入
                        String username = met_username.getText().toString();
                        String password = met_password.getText().toString();

                        //执行登录
                        loginPresenter.loginCheck(username, password);


                    }
                });


        loginPresenter = new LoginPresenterImpl(this);
    }

    @Override
    public void usernameError(String error) {
        met_username.setError(error);
    }

    @Override
    public void passwordError(String error) {
        met_password.setError(error);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
