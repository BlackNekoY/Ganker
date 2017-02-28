package com.slim.me.ganker.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Slim on 2017/2/16.
 */
public class ApiManager {

    private Retrofit mRetrofit;
    private GankApi mGankApi;
    private OkHttpClient mClient;

    private Object lock = new Object();

    private ApiManager() {
        mClient = new OkHttpClient.Builder()
                .connectTimeout(8, TimeUnit.SECONDS) //8秒超时
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mClient)
                .build();
    }

    public static ApiManager getInstance() {
        return ApiManagerInstanceHolder.sInstance;
    }

    public OkHttpClient getOkHttpClient() {
        return mClient;
    }

    public GankApi getGankApi() {
        if(mGankApi == null) {
            mGankApi = mRetrofit.create(GankApi.class);
        }
        return mGankApi;
    }


    private static class ApiManagerInstanceHolder {
        public static ApiManager sInstance = new ApiManager();
    }
}
