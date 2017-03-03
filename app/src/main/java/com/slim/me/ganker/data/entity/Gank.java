package com.slim.me.ganker.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Slim on 2017/2/24.
 */

public class Gank {

    /**
     * _id : 58a6e902421aa966366d05ef
     * createdAt : 2017-02-17T20:13:54.599Z
     * desc : java 8 lambda表达式详解，有这篇就够了
     * publishedAt : 2017-02-23T11:50:23.116Z
     * source : web
     * type : Android
     * url : http://blog.csdn.net/xiaochuanding/article/details/55516726
     * used : true
     * who : null
     */

    @SerializedName("_id")
    public String id;
    public Date createdAt;
    public String desc;
    public Date publishedAt;
    public String type;
    public String url;
    public boolean used;
    public String who;

    @Override
    public String toString() {
        return "Gank{" +
                "id='" + id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", desc='" + desc + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", used=" + used +
                ", who='" + who + '\'' +
                '}';
    }
}
