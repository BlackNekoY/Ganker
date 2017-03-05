package com.slim.me.ganker.data.entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Slim on 2017/2/24.
 */
@Entity(nameInDb = "gank")
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

    @Id
    @SerializedName("_id")
    public String id;
    public Date createdAt;
    public String desc;
    public Date publishedAt;
    public String type;
    public String url;
    public boolean used;
    public String who;

    @Generated(hash = 496931389)
    public Gank(String id, Date createdAt, String desc, Date publishedAt,
            String type, String url, boolean used, String who) {
        this.id = id;
        this.createdAt = createdAt;
        this.desc = desc;
        this.publishedAt = publishedAt;
        this.type = type;
        this.url = url;
        this.used = used;
        this.who = who;
    }

    @Generated(hash = 116302247)
    public Gank() {
    }

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

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getPublishedAt() {
        return this.publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean getUsed() {
        return this.used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return this.who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
