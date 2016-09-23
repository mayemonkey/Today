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
    private Subscription add;

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
    public void addGroup(final Group group) {
        add = groupModel.addGroup(group).
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
                            groupView.addItem(group);
                            groupView.showToast("item added");
                        } else {
                            groupView.showToast("failed to add");
                        }
                    }
                });
    }

    @Override
    public void removeGroup(int id, final int position) {
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
                            groupView.removeItem(position);
                            groupView.showToast("remove");
                        } else {
                            groupView.showToast("failed to remove");
                        }
                    }
                });
    }

    @Override
    public void updateGroup(List<Group> groups) {
        update = groupModel.updateGroup(groups).
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
                            groupView.showToast("");
                            groupView.finishActivity();
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
