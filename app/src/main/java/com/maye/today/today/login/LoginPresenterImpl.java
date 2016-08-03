package com.maye.today.today.login;

import android.text.TextUtils;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * LoginPresenter实现类
 */
public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;
    private LoginModel loginModel;


    public LoginPresenterImpl(LoginView loginView) {

        this.loginView = loginView;
        this.loginModel = new LoginModelImpl();
    }

    @Override
    public void loginCheck(String username, String password) {
        if (loginView == null) {
            return;
        }

        if(TextUtils.isEmpty(username)){
            loginView.usernameError("should not be empty");
        }

        if(TextUtils.isEmpty(password)){
            loginView.passwordError("should not be empty");
        }

        loginView.showProgress();

        loginModel.login(username, password).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        //完成
                        loginView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loginView.hideProgress();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        //根据Response内容作出动作


                    }
                });
    }

}
