package com.jam.mymvpdemo.net;

import com.jam.mymvpdemo.util.L;
import com.jam.mymvpdemo.util.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by qingning on 2016/10/25.
 */

public class HttpManager {
    private static final String BASE_URL = "https://api.github.com/" ;
    private static final int DEFAULT_TIMEOUT = 30;
    private HashMap<String, Object> mServiceMap;

    // --------------------------  单例  ----------------------------
    private static HttpManager INSTANCE;

    private HttpManager() {
        //Map used to store RetrofitService
        mServiceMap = new HashMap<>();
    }

    public static HttpManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpManager();
                }
            }
        }
        return INSTANCE;
    }
    // ----------------------------------------------------------------


    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> serviceClass) {
        if (mServiceMap.containsKey(serviceClass.getName())) {
            return (T) mServiceMap.get(serviceClass.getName());
        } else {
            Object obj = createService(serviceClass);
            mServiceMap.put(serviceClass.getName(), obj);
            return (T) obj;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> serviceClass, OkHttpClient client) {
        if (mServiceMap.containsKey(serviceClass.getName())) {
            return (T) mServiceMap.get(serviceClass.getName());
        } else {
            Object obj = createService(serviceClass, client);
            mServiceMap.put(serviceClass.getName(), obj);
            return (T) obj;
        }
    }

    private <T> T createService(Class<T> serviceClass) {
        //custom OkHttp
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        //time our
        httpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        //cache
        // File httpCacheDirectory = new File(MyApplication.getInstance().getCacheDir(), "OkHttpCache");
        // httpClient.cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024));
        // httpClient.addInterceptor(new CacheControlInterceptor());

        //Interceptor
        httpClient.addNetworkInterceptor(getInterceptor());

        return createService(serviceClass, httpClient.build());
    }

    private <T> T createService(Class<T> serviceClass, OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit.create(serviceClass);
    }

    private Interceptor getInterceptor(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    private class LogInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            L.d("HttpHelper" + String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            L.d("HttpHelper" + String.format("Received response for %s in %.1fms%n%s",response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;

            // log Response Body
//            if(BuildConfig.DEBUG) {
//                String responseBody = response.body().string();
//                Log.v("HttpHelper", String.format("Received response for %s in %.1fms%n%s%n%s",
//                        response.request().url(), (t2 - t1) / 1e6d, response.headers(), responseBody));
//                return response.newBuilder()
//                        .body(ResponseBody.create(response.body().contentType(), responseBody))
//                        .build();
//            } else {
//                Log.v("HttpHelper", String.format("Received response for %s in %.1fms%n%s",
//                        response.request().url(), (t2 - t1) / 1e6d, response.headers()));
//                return response;
//            }
        }
    }

    private class CacheControlInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!Utils.checkConnectivity()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response response = chain.proceed(request);

            if (Utils.checkConnectivity()) {
                int maxAge = 60 * 60; // read from cache for 1 minute
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }
}
