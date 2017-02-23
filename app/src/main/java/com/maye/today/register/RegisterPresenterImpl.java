package com.maye.today.register;

import com.maye.today.domain.User;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
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

        registerView.showProgress(true);

        subscribe = registerModel.register(user).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        registerView.showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        //TODO　registerView.inputError
                    }
                });
    }

    @Override
    public void onViewDestroy() {
        registerView = null;
        if (subscribe != null)
            subscribe.unsubscribe();
    }

}
