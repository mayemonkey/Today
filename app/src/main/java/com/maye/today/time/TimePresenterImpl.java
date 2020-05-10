package com.maye.today.time;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.ResponseBody;
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
        subscribe = timeModel.getDatetime("44322da67edca997da987d5f9c0fdacb").
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        timeView.showDate(Calendar.getInstance());
                    }

                    @Override
                    public void onNext(ResponseBody body) {
                        try {
                            long time = Long.parseLong(body.string().replace("{", "").replace("}", "").split(":")[1].replace("\n", "")) * 1000;
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(time);
                            timeView.showDate(calendar);
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        timeView = null;
        if (subscribe != null)
            subscribe.unsubscribe();
    }
}
