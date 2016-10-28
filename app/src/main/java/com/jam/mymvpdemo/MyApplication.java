package com.jam.mymvpdemo;

import android.app.Application;

/**
 * 作者：qingning on 2016/10/25 19:20
 */

public class MyApplication extends Application {
    private static MyApplication application;

    public static MyApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        LeakCanary.install(this);
        application = this;
    }
}
