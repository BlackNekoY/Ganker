package com.slim.me.ganker.data;

import com.google.gson.annotations.SerializedName;
import com.slim.me.ganker.data.entity.Meizhi;

import java.util.List;

/**
 * Created by Slim on 2017/2/24.
 */
public class MeizhiData extends BaseData {
    @SerializedName("results")
    public List<Meizhi> results;
}
