package com.slim.me.ganker.ui.event;

import com.slim.me.ganker.data.entity.Meizhi;

/**
 * Created by Slim on 2017/3/7.
 */
public class MeizhiClickEvent {

    public final Meizhi meizhi;

    public MeizhiClickEvent(Meizhi meizhi) {
        this.meizhi = meizhi;
    }
}