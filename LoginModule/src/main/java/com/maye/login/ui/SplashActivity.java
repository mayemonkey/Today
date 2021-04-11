package com.maye.login.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.maye.base.utils.SharedPreferencesUtil;
import com.maye.login.mvp.InAdvanceView;
import com.maye.login.mvp.LoginPresenter;
import com.maye.login.mvp.LoginPresenterImpl;


public class SplashActivity extends Activity implements InAdvanceView {

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.);

    }

    @Override
    protected void onStart() {
        super.onStart();
        loginPresenter = new LoginPresenterImpl(this);
        checkSessionId();
    }

    private void checkSessionId(){
        String sessionId = (String) SharedPreferencesUtil.getData(this, "sessionId", "");
        if (TextUtils.isEmpty(sessionId)) {
            turnToActivity(LoginActivity.class);
        } else {
            loginPresenter.loginInAdvance(sessionId);
        }
    }

    @Override
    public void turnToActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        this.finish();
    }

}
