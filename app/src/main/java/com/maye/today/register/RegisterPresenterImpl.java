package com.maye.today.register;

import android.text.TextUtils;

import com.maye.today.domain.User;


import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * RegisterPresenter实现
 */
public class RegisterPresenterImpl implements RegisterPresenter {

    private RegisterView registerView;
    private RegisterModel registerModel;
    private Subscription subscribe;

    public RegisterPresenterImpl(RegisterView registerView) {
        this.registerView = registerView;
        this.registerModel = new RegisterModelImpl();
    }

    @Override
    public void checkRegister(User user) {
        //not null
        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();

        //could be null
        String nickname = user.getNickname();
        String avatar = user.getAvatar();
        String phone = user.getPhone();

        //空输入判断
        boolean isEmpty = false;

        if (TextUtils.isEmpty(username)) {
            checkEmpty("USERNAME");
            isEmpty = true;
        }

        if (TextUtils.isEmpty(password)) {
            checkEmpty("PASSWORD");
            isEmpty = true;
        }

        if (TextUtils.isEmpty(email)) {
            checkEmpty("EMAIL");
            isEmpty = true;
        }

        if (isEmpty) return;

        //通过非空判断

        subscribe = registerModel.register(user).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {

                    }
                });
    }

    @Override
    public void onViewDestroy() {
        registerView = null;
        subscribe.unsubscribe();
    }

    private void checkEmpty(String inputIndex) {
        Observable.just(inputIndex).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Action1<String>() {
                    @Override
                    public void call(String inputIndex) {
                        registerView.inputError(inputIndex, "should not be empty");
                    }
                });
    }

}
