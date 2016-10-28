package com.jam.mymvpdemo.data.local;

import android.util.Log;

import com.jam.mymvpdemo.data.MainDataSource;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by qingning on 2016/10/19.
 */

public class MainLocalDataSource implements MainDataSource {
    private static final String TAG = "MainLocalDataSource";
    private static MainLocalDataSource INSTANCE;

    private MainLocalDataSource() {
    }

    public static MainLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainLocalDataSource();
        }
        return INSTANCE;
    }

    @Override
    public Observable<String> getDataInfo() {

        Observable<String> just1 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("");
                subscriber.onCompleted();
            }
        }).flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                if(s == null){
                    return Observable.empty();
                }else{
                    return Observable.just(s);
                }
            }
        });
        Observable<String> just2 = Observable.just("2","4","6");

        Observable.concat(just1, just2).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: e = "+ e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Log.d("MainLocalDataSource", "onNext: s = "+ s);
            }
        });
        return Observable.empty();
    }
}
