package com.slim.me.ganker.ui.view;

import com.slim.me.ganker.data.entity.Meizhi;

import java.util.List;

/**
 * Created by Slim on 2017/2/16.
 */
public interface IMeizhiView extends IBaseView {

    void setRefreshing(boolean refreshing);

    void updateMeizhi(List<Meizhi> meizhiList);
}
