package com.jam.mymvpdemo.presenter;

/**
 * Created by qingning on 2016/10/19.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
    void showToast(String msg);
    // 跳转页面
    void toPage(String keyFindPage);
}
