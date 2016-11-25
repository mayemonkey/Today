package com.maye.today.login;

import android.text.TextUtils;

import com.maye.today.domain.LoginResponse;
import com.maye.today.global.TodayApplication;
import com.maye.today.ui.activity.HomeActivity;
import com.maye.today.ui.activity.LoginActivity;
import com.maye.today.util.SharedPreferencesUtil;

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
    private InAdvanceView inAdvanceView;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginModel = new LoginModelImpl();
    }

    public LoginPresenterImpl(InAdvanceView inAdvanceView) {
        this.inAdvanceView = inAdvanceView;
        this.loginModel = new LoginModelImpl();
    }

    @Override
    public void loginCheck(String username, String password) {
        if (loginView == null) {
            return;
        }

        //通过非空判断，上传请求
//        Observable.just("").observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//            }
//        });
                loginView.showProgress(true);


        subscribe = loginModel.login(username, password).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<LoginResponse>() {
                    @Override
                    public void onCompleted() {
                        loginView.showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        //根据Response内容作出动作，登录成功跳转Home，失败等待重新登录
                        boolean loginPass = isLoginPass(loginResponse);
                        if (loginPass) {
                            loginView.startActivity();
                        } else {
                            //TODO loginView.inputError
                        }
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
                    public void onNext(LoginResponse loginResponse) {
                        //根据Response内容作出动作
                        boolean loginPass = isLoginPass(loginResponse);
                        if (loginPass) {
                            inAdvanceView.turnToActivity(HomeActivity.class);
                        } else {
                            inAdvanceView.turnToActivity(LoginActivity.class);
                        }
                    }
                });
    }


    /**
     * 根据返回数据判断登录检验是否通过
     *
     * @param loginResponse 登录返回数据
     * @return 是否通过
     */
    private boolean isLoginPass(LoginResponse loginResponse) {
        String result = loginResponse.getResult();
        if (result.equals("success")) {
            String sessionId = loginResponse.getSessionId();
            if (!TextUtils.isEmpty(sessionId)) {
                //将返回的sessionId保存在内存及本地
                TodayApplication.setSessionId(sessionId);
                SharedPreferencesUtil.saveData(TodayApplication.getContext(), "sessionId", sessionId);
                TodayApplication.setUsername(loginResponse.getUsername());
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void onViewDestroy() {
        loginView = null;
        subscribe.unsubscribe();
    }

}
