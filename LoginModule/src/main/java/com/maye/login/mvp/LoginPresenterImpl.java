package com.maye.login.mvp;

import android.text.TextUtils;

import com.maye.login.bean.LoginResponse;
import com.maye.login.ui.LoginActivity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * LoginPresenter实现类
 */
public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;
    private LoginModel loginModel;
    private Disposable subscribe;
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
                subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse loginResponse) throws Throwable {
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
                subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse loginResponse) throws Throwable {
                        //根据Response内容作出动作
                        boolean loginPass = isLoginPass(loginResponse);
                        if (loginPass) {
                            //TODO ARouter
//                            inAdvanceView.turnToActivity(HomeActivity.class);
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
                //TODO 保存用户信息
//                TodayApplication.setSessionId(sessionId);
//                SharedPreferencesUtil.saveData(TodayApplication.getContext(), "sessionId", sessionId);
//                TodayApplication.setUsername(loginResponse.getUsername());
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
        if (subscribe != null)
            subscribe.dispose();
    }

}
