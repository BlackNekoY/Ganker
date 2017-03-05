package com.slim.me.ganker.data.entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Slim on 2017/2/16.
 */
@Entity(nameInDb = "meizhi")
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

    @Id
    @SerializedName("_id")
    public String id;
    public Date createdAt;
    public String desc;
    public Date publishedAt;
    public String type;
    public String url;
    public boolean used;


    public Meizhi() {
    }


    @Generated(hash = 13220324)
    public Meizhi(String id, Date createdAt, String desc, Date publishedAt, String type, String url,
                  boolean used) {
        this.id = id;
        this.createdAt = createdAt;
        this.desc = desc;
        this.publishedAt = publishedAt;
        this.type = type;
        this.url = url;
        this.used = used;
    }


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

}
