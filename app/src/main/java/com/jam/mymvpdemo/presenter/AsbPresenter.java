package com.jam.mymvpdemo.presenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 作者：qingning on 2016/10/26 16:22
 */

public abstract class AsbPresenter implements BasePresenter {
    private CompositeSubscription mSubscriptions;

    protected AsbPresenter() {
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        mSubscriptions.clear();
    }

    protected void addSubscription(Subscription subscription){
        mSubscriptions.add(subscription);
    }
}
