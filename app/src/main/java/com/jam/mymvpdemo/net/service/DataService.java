package com.jam.mymvpdemo.net.service;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 作者：qingning on 2016/10/25 19:20
 */

public interface DataService {
    @GET("/repos/{uid}/plugins/readme")
    Observable<ResponseBody> getUserData(@Path("uid") String uid);
}
