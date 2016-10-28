package com.maye.today.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.maye.today.login.InAdvanceView;
import com.maye.today.login.LoginPresenter;
import com.maye.today.login.LoginPresenterImpl;
import com.maye.today.util.SharedPreferencesUtil;

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
