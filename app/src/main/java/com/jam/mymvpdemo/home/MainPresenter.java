package com.jam.mymvpdemo.home;

import com.jam.mymvpdemo.data.MainRepository;
import com.jam.mymvpdemo.util.L;
import com.jam.mymvpdemo.util.schedulers.BaseSchedulerProvider;

import rx.Observer;

/**
 * Created by qingning on 2016/10/19.
 */

public class MainPresenter implements MainContract.MainContractPresenter {
    private final MainRepository                instance;
    private       MainContract.MainContractView mainView;
    private       BaseSchedulerProvider         baseSchedulerProvider;

    public MainPresenter(MainContract.MainContractView mainView, MainRepository instance, BaseSchedulerProvider baseSchedulerProvider) {
        this.mainView = mainView;
        this.instance = instance;
        this.baseSchedulerProvider = baseSchedulerProvider;
        mainView.setPresenter(this);
    }

    @Override
    public void getInfo() {
        instance.getDataInfo()
                .subscribeOn(baseSchedulerProvider.io())
                .observeOn(baseSchedulerProvider.ui())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        // Log.d("MainPresenter", "onError: ");
                        System.out.println("MainPresenter  onError 39 : e = "+ e.getMessage());
                        e.printStackTrace();
                        mainView.showError();
                    }

                    @Override
                    public void onNext(String s) {
                        // Log.d("MainPresenter", "onNext: ");
                        System.out.println("MainPresenter  onNext 47 : s = "+ s);
                        mainView.showSuccess(s);
                    }
                });
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

    }

    @Override
    public void onCreate() {

    }
}
