package com.maye.today.login;

import android.text.TextUtils;

import com.maye.today.domain.LoginResponse;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * LoginPresenter实现类
 */
public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;
    private LoginModel loginModel;
    private Subscription subscribe;


    public LoginPresenterImpl(LoginView loginView) {

        this.loginView = loginView;
        this.loginModel = new LoginModelImpl();
    }

    @Override
    public void loginCheck(String username, String password) {
        if (loginView == null) {
            return;
        }

        boolean isEmpty = false;

        //输入空判断
        if (TextUtils.isEmpty(username)) {
            showError("USERNAME");
            isEmpty = true;
        }

        if (TextUtils.isEmpty(password)) {
            showError("PASSWORD");
            isEmpty = true;
        }

        if (isEmpty) return;

        //通过非空判断，上传请求
        loginView.showProgress();

        subscribe = loginModel.login(username, password).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<LoginResponse>() {
                    @Override
                    public void onCompleted() {
                        // 完成
                        loginView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loginView.hideProgress();
                    }

                    @Override
                    public void onNext(LoginResponse responseBody) {
                        //根据Response内容作出动作

                    }
                });
    }

    @Override
    public void loginInAdvance(String sessionId) {
        subscribe = loginModel.login(sessionId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<LoginResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(LoginResponse responseBody) {
                        //根据Response内容作出动作

                    }
                });
    }

    @Override
    public void onViewDestroy() {
        loginView = null;
        subscribe.unsubscribe();
    }

    private void showError(String inputIndex){
        Observable.just(inputIndex).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Action1<String>() {
                    @Override
                    public void call(String inputIndex) {
                        loginView.inputError(inputIndex, "should not be empty");
                    }
                });
    }

}
