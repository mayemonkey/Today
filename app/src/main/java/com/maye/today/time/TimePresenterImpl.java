package com.maye.today.time;

import android.content.Context;
import android.widget.Toast;
import com.maye.today.ui.activity.HomeActivity;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class TimePresenterImpl implements TimePresenter {

    private TimeModel timeModel;

    private TimeView timeView;
    private Subscription subscribe;

    public TimePresenterImpl(TimeView timeView) {
        this.timeModel = new TimeModelImpl();
        this.timeView = timeView;
    }

    @Override
    public void getTime() {
        subscribe = timeModel.getDatetime().
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String dateTime) {
                        Toast.makeText((Context) timeView, dateTime, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onDestroyView() {
        timeView = null;
        subscribe.unsubscribe();
    }
}
