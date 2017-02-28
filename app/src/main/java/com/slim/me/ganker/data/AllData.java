package com.slim.me.ganker.data;

import com.google.gson.annotations.SerializedName;
import com.slim.me.ganker.data.entity.Gank;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Slim on 2017/2/24.
 */

public class AllData extends BaseData {


    @SerializedName("category")
    public List<String> category;

//    @SerializedName("results")
//    public Result results;

    @SerializedName("results")
    public LinkedHashMap<String, ArrayList<Gank>> results;


    public static class Result {
        @SerializedName("Android")
        public List<Gank> androidList;
        @SerializedName("iOS")
        public List<Gank> iosList;
        @SerializedName("休息视频")
        public List<Gank> relaxVideoList;
        @SerializedName("拓展资源")
        public List<Gank> expandList;
        @SerializedName("前端")
        public List<Gank> frontEndList;
        @SerializedName("福利")
        public List<Gank> mMeizhiList;

    }

    @Override
    public String toString() {
        return "AllData{" +
                "category=" + category +
                ", results=" + results +
                '}';
    }
}
