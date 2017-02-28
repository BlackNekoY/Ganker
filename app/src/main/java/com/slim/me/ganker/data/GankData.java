package com.slim.me.ganker.data;

import com.google.gson.annotations.SerializedName;
import com.slim.me.ganker.data.entity.Gank;

import java.util.List;

/**
 * Created by Slim on 2017/2/24.
 */

public class GankData extends BaseData {

    @SerializedName("results")
    public List<Gank> results;
}
