package com.jam.mymvpdemo.ui.home;

import com.jam.mymvpdemo.data.MainRepository;
import com.jam.mymvpdemo.interfaces.PageNames;
import com.jam.mymvpdemo.presenter.AsbPresenter;
import com.jam.mymvpdemo.util.schedulers.BaseSchedulerProvider;

import rx.Observer;

public class MainPresenter extends AsbPresenter implements MainContract.MainContractPresenter {
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
        addSubscription(instance
                .getDataInfo()
                .subscribeOn(baseSchedulerProvider.io())
                .observeOn(baseSchedulerProvider.ui())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("MainPresenter  onError 39 : e = " + e.getMessage());
                        e.printStackTrace();
                        mainView.showError();
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("MainPresenter  onNext 47 : s = " + s);
                        mainView.showSuccess(s);
                    }
                }));
    }

    @Override
    public void toFindPage() {
        mainView.toPage(PageNames.KEY_FIND_PAGE);
    }
}
