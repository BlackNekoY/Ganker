package com.slim.me.ganker.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.slim.me.ganker.api.GankApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Slim on 2017/2/16.
 */
public class ApiManager implements IManager {

    private Retrofit mRetrofit;
    private GankApi mGankApi;
    private OkHttpClient mClient;

    private Object lock = new Object();

    public OkHttpClient getOkHttpClient() {
        return mClient;
    }

    // fix: 2016/12/30日的妹纸数据，publishAt为12.31号，加了这个gson至构造函数就正常了，why??
    final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();

    public GankApi getGankApi() {
        if(mGankApi == null) {
            mGankApi = mRetrofit.create(GankApi.class);
        }
        return mGankApi;
    }

    @Override
    public void onInit() {
        mClient = new OkHttpClient.Builder()
                .connectTimeout(8, TimeUnit.SECONDS) //8秒超时
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mClient)
                .build();
    }

}
