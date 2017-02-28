package com.slim.me.ganker.api;

import com.slim.me.ganker.data.AllData;
import com.slim.me.ganker.data.MeizhiData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Slim on 2017/2/16.
 */
public interface GankApi {

    @GET("data/福利/{number}/{page}")
    Observable<MeizhiData> getMeizhi(@Path("number") int number, @Path("page") int page);

    @GET("day/{year}/{month}/{day}")
    Observable<AllData> getDailyData(@Path("year") String year, @Path("month") String month, @Path("day") String day);

}
