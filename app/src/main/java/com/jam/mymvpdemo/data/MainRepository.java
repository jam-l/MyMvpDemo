package com.jam.mymvpdemo.data;

import com.jam.mymvpdemo.data.local.MainLocalDataSource;

import rx.Observable;
import rx.functions.Action1;

public class MainRepository implements MainDataSource {

    private MainLocalDataSource localDataSource;
    private MainDataSource      mRemoteDataSource;
    private String              dataCache;

    // --------------------------  单例  ----------------------------
    private static MainRepository INSTANCE;

    private MainRepository(MainDataSource mRemoteDataSource) {
        this.mRemoteDataSource = mRemoteDataSource;
        localDataSource = MainLocalDataSource.getInstance();
    }

    public static MainRepository getInstance(MainDataSource mRemoteDataSource) {
        if (INSTANCE == null) {
            synchronized (MainRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MainRepository(mRemoteDataSource);
                }
            }
        }
        return INSTANCE;
    }
    // ----------------------------------------------------------------

    @Override
    public Observable<String> getDataInfo() {
        if (dataCache != null) {
            return Observable.just(dataCache);
        }

        Observable<String> dataInfo = localDataSource.getDataInfo();
        Observable<String> stringObservable = mRemoteDataSource.getDataInfo().doOnNext(new Action1<String>() {
            @Override
            public void call(String string) {
                dataCache = string;
            }
        });
        Observable<String> first = Observable.concat(dataInfo, stringObservable).first();
        return first;
    }
}
