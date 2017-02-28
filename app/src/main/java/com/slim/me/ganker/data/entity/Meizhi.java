package com.slim.me.ganker.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Slim on 2017/2/16.
 */
public class Meizhi {


    /**
     * _id : 58ae32e1421aa957f9dd6dbe
     * createdAt : 2017-02-23T08:54:57.835Z
     * desc : 2-23
     * publishedAt : 2017-02-23T11:50:23.116Z
     * source : chrome
     * type : 福利
     * url : http://7xi8d6.com1.z0.glb.clouddn.com/2017-02-23-16906361_1846962082218545_7116720067412230144_n.jpg
     * used : true
     * who : daimajia
     */

    @SerializedName("_id")
    public String id;
    public Date createdAt;
    public String desc;
    public Date publishedAt;
    public String type;
    public String url;
    public boolean used;

    @Override
    public String toString() {
        return "Meizhi{" +
                "id='" + id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", desc='" + desc + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", used=" + used +
                '}';
    }
}
