package com.maye.today.register;

import com.maye.today.domain.User;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * RegisterPresenter实现
 */
public class RegisterPresenterImpl implements RegisterPresenter {

    private RegisterView registerView;
    private RegisterModel registerModel;
    private Disposable subscribe;

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
                subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Throwable {
                        registerView.showProgress(false);
                    }
                });
    }

    @Override
    public void onViewDestroy() {
        registerView = null;
        if (subscribe != null)
            subscribe.dispose();
    }

}
