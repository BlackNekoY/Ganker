package com.slim.me.ganker.ui.view;

import com.slim.me.ganker.data.AllData;

/**
 * Created by Slim on 2017/2/25.
 */

public interface IDailyView extends IBaseView {

    void updateDaily(AllData data);

    void setLoadingState(boolean isLoading);
}
