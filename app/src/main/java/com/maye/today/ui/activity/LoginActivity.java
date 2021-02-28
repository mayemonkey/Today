package com.maye.today.ui.activity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.jakewharton.rxbinding.view.RxView;
import com.maye.today.login.LoginPresenter;
import com.maye.today.login.LoginPresenterImpl;
import com.maye.today.login.LoginView;
import com.maye.today.today.R;
import com.maye.today.util.InputUtil;
import com.maye.today.util.Md5Util;
import com.nineoldandroids.view.ViewHelper;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 登录Activity
 */
public class LoginActivity extends Activity implements LoginView, View.OnTouchListener, View.OnClickListener {

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

    @Override
    protected void onResume() {
        super.onResume();
        til_login_username.getEditText().clearFocus();
        til_login_username.setError(null);
        til_login_password.getEditText().clearFocus();
        til_login_password.setError(null);

        ImageView iv_circle_inner = (ImageView) findViewById(R.id.iv_circle_inner);
        ImageView iv_circle_middle = (ImageView) findViewById(R.id.iv_circle_middle);
        ImageView iv_circle_outside = (ImageView) findViewById(R.id.iv_circle_outside);
        setCustomerAnimation(iv_circle_inner, iv_circle_middle, iv_circle_outside);
    }

    /**
     * 初始化控件
     */
    private void initComponent() {
        til_login_username = (TextInputLayout) findViewById(R.id.til_login_username);
        til_login_username.getEditText().setOnTouchListener(this);
        til_login_password = (TextInputLayout) findViewById(R.id.til_login_password);
        til_login_password.getEditText().setOnTouchListener(this);

        progressDialog = new MaterialDialog.Builder(this).title("登录中").content("正在验证用户名及密码").progress(true, 0).build();

        TextView tv_login = (TextView) findViewById(R.id.tv_login);

        TextView tv_login_register = (TextView) findViewById(R.id.tv_login_register);
        tv_login_register.setOnClickListener(this);

        loginPresenter = new LoginPresenterImpl(this);

        //点击登录——防止多触
        RxView.clicks(tv_login).throttleWithTimeout(300, TimeUnit.MILLISECONDS).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Action1<Void>() {
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
    }

    /**
     * 设置自定义动画，使Sign周边环圈做位移动画
     */
    private void setCustomerAnimation(final ImageView inner, final ImageView middle, final ImageView outside) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1.5f).setDuration(3000);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value1 = (float) animation.getAnimatedValue();
                float value2 = value1 + 0.2f;
                float value3 = value1 + 0.4f;
                float alpha1 = 2f * (1 - value1) + 1;
                float alpha2 = 2f * (1 - value2) + 1;
                float alpha3 = 2f * (1 - value3) + 1;

                ViewHelper.setScaleX(inner, value1);
                ViewHelper.setScaleY(inner, value1);
                ViewHelper.setAlpha(inner, alpha1);
                ViewHelper.setScaleX(middle, value2);
                ViewHelper.setScaleY(middle, value2);
                ViewHelper.setAlpha(middle, alpha2);
                ViewHelper.setScaleX(outside, value3);
                ViewHelper.setScaleY(outside, value3);
                ViewHelper.setAlpha(outside, alpha3);
            }
        });
        valueAnimator.start();
    }

    @Override
    public void inputError(String index, String error) {
        switch (index) {
            case "USERNAME":
                til_login_username.setError(error);
                break;

            case "PASSWORD":
                til_login_password.setError(error);
                break;
        }
    }

    @Override
    public void showProgress(boolean visible) {
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


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.et_login_username:
                til_login_username.setError(null);
                break;

            case R.id.et_login_password:
                til_login_password.setError(null);
                break;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}
