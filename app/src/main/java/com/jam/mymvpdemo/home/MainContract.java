package com.jam.mymvpdemo.home;

import com.jam.mymvpdemo.presenter.BasePresenter;
import com.jam.mymvpdemo.presenter.BaseView;

/**
 * Created by qingning on 2016/10/19.
 */

public interface MainContract {
    interface MainContractView extends BaseView<MainContractPresenter> {
        void showSuccess(String info);
        void showError();
    }

    interface MainContractPresenter extends BasePresenter {
        void getInfo();
    }
}
