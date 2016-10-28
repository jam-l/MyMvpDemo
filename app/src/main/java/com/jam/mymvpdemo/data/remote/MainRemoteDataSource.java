package com.jam.mymvpdemo.data.remote;

import com.jam.mymvpdemo.data.MainDataSource;
import com.jam.mymvpdemo.net.HttpManager;
import com.jam.mymvpdemo.net.service.DataService;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by qingning on 2016/10/19.
 */

public class MainRemoteDataSource implements MainDataSource {
    private static MainRemoteDataSource INSTANCE;

    private MainRemoteDataSource() {
    }

    public static MainRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public Observable<String> getDataInfo() {
        return HttpManager.getInstance()
                .getService(DataService.class)
                .getUserData("typecho-fans")
                .map(new Func1<ResponseBody, String>() {
                    @Override
                    public String call(ResponseBody responseBody) {
                        return responseBody.source().toString();
                    }
                });
    }
}
