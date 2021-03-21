package com.maye.today.time;

import java.io.IOException;
import java.util.Calendar;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class TimePresenterImpl implements TimePresenter {

    private TimeModel timeModel;

    private TimeView timeView;
    private Disposable subscribe;

    public TimePresenterImpl(TimeView timeView) {
        this.timeModel = new TimeModelImpl();
        this.timeView = timeView;
    }

    @Override
    public void getTime() {
        subscribe = timeModel.getDatetime("44322da67edca997da987d5f9c0fdacb").
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe((Consumer<ResponseBody>) responseBody -> {
                    try {
                        long time = Long.parseLong(responseBody.string().replace("{", "").replace("}", "").split(":")[1].replace("\n", "")) * 1000;
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(time);
                        timeView.showDate(calendar);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, (Consumer<Throwable>) throwable -> timeView.showDate(Calendar.getInstance()));
    }

    @Override
    public void onDestroyView() {
        timeView = null;
        if (subscribe != null)
            subscribe.dispose();
    }
}
