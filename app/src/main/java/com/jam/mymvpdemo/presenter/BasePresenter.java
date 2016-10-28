package com.jam.mymvpdemo.presenter;

/**
 * Created by qingning on 2016/10/19.
 */

public interface BasePresenter {
    void onStart();
    void onResume();
    void onPause();
    void onDestroy();
    void onCreate();
}
