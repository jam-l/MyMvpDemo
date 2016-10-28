package com.jam.mymvpdemo.data;

import rx.Observable;

/**
 * Created by qingning on 2016/10/19.
 */
public interface MainDataSource {
    Observable<String> getDataInfo();
}
