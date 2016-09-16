package com.maye.today.group;

import com.maye.today.domain.Group;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GroupPresenterImpl implements GroupPresenter {

    private GroupModel groupModel;
    private GroupView groupView;
    private Subscription update;
    private Subscription remove;
    private Subscription load;

    public GroupPresenterImpl(GroupView groupView) {
        this.groupModel = new GroupModelImpl();
        this.groupView = groupView;
    }

    @Override
    public void loadGroup(String date, String type) {
        load = groupModel.getGroup(date, type).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<List<Group>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Group> list) {
                        groupView.showGroup(list);
                    }
                });
    }

    @Override
    public void removeGroup(int id) {
        remove = groupModel.removeGroup(id).
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
                    public void onNext(ResponseBody body) {
                        if (true) {
                            groupView.removeItem(0);
                            groupView.showToast("");
                        } else {
                            groupView.showToast("");
                        }
                    }
                });
    }

    @Override
    public void updateGroup(final Group group) {
        update = groupModel.updateGroup(group).
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
                    public void onNext(ResponseBody body) {
                        if (true) {
                            groupView.notifyItem(0, group);
                            groupView.showToast("");
                        } else {
                            groupView.showToast("");
                        }
                    }
                });
    }

    @Override
    public void viewDestroy() {
        groupView = null;
        if (remove != null)
            remove.unsubscribe();

        if (update != null)
            update.unsubscribe();

        if (load != null)
            load.unsubscribe();
    }
}
