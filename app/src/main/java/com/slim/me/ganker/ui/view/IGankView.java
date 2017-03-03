package com.slim.me.ganker.ui.view;

import com.slim.me.ganker.data.entity.Gank;

import java.util.List;

/**
 * Created by Slim on 2017/3/3.
 */

public interface IGankView extends IBaseView {

    void setRefreshing(boolean refreshing);

    void updateGanks(List<Gank> gankList);

}
